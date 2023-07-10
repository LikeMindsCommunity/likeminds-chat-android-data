package com.likeminds.likemindschat.conversation

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkInfo
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.ConversationRO
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.conversation.util.FirebaseUtil.childEventListener
import com.likeminds.likemindschat.conversation.util.GetConversationCountType
import com.likeminds.likemindschat.conversation.util.GetConversationType
import com.likeminds.likemindschat.conversation.util.LoadConversationType
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class ConversationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().conversationComponent()?.inject(this)
    }

    private val conversationApi by lazy {
        groupChatSDK.getConversationApi()
    }

    private val conversationDB by lazy {
        groupChatSDK.getConversationDB()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param postConversationRequest - client request model to post a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<PostConversationResponse> - Base LM response[PostConversationResponse]
     */
    suspend fun postConversation(postConversationRequest: PostConversationRequest): LMResponse<PostConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validatePostConversationRequest(postConversationRequest)

        val request = _PostConversationRequest_.Builder()
            .chatroomId(postConversationRequest.chatroomId)
            .text(postConversationRequest.text)
            .shareLink(postConversationRequest.shareLink)
            .ogTags(ModelConverter.createLinkOGTags(postConversationRequest.ogTags))
            .repliedConversationId(postConversationRequest.repliedConversationId)
            .attachmentCount(postConversationRequest.attachmentCount)
            .temporaryId(postConversationRequest.temporaryId)
            .repliedChatroomId(postConversationRequest.repliedChatroomId)
            .build()

        return when (val response = conversationApi.postConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                val conversation = body.data?.conversation

                conversation?.let {
                    conversationDB.savePostedConversation(
                        it,
                        postConversationRequest.isFromNotification
                    )
                }

                ModelConverter.convertPostConversationAPIResponse(body)
            }
        }
    }

    /**
     * validates [postConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePostConversationRequest(postConversationRequest: PostConversationRequest) {
        if (postConversationRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (postConversationRequest.text.isEmpty()
            && (postConversationRequest.attachmentCount ?: 0) <= 0
        ) {
            RequestUtils.throwException("text")
        }
    }

    /**
     * runs the query for observing new conversations and returns the data in listener
     * @param observeConversationsRequest: [ObserveConversationsRequest] request for observing new conversation
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     */
    suspend fun observeConversations(observeConversationsRequest: ObserveConversationsRequest) {
        //validates the client request
        RequestUtils.validate()
        validateObserveConversationRequest(observeConversationsRequest)

        val realm = Realm.getDefaultInstance()
        val chatroomId = observeConversationsRequest.chatroomId
        val listener = observeConversationsRequest.listener

        val flowOfConversations = conversationDB.observeConversations(realm, chatroomId)

        flowOfConversations.collect { collectionChange ->
            val insertions = getConversationFromChanges(
                collectionChange.collection,
                collectionChange.changeset?.insertions
            )

            val changes = getConversationFromChanges(
                collectionChange.collection,
                collectionChange.changeset?.changes
            )

            val postedConversation = insertions?.filter { conversation ->
                !conversation.temporaryId.isNullOrEmpty()
            }?.mapNotNull { conversation ->
                ModelConverter.convertConversationRO(conversation)
            }

            val newConversations = insertions?.filter { conversation ->
                conversation.temporaryId.isNullOrEmpty()
            }?.mapNotNull { conversation ->
                ModelConverter.convertConversationRO(conversation)
            }

            val changedConversations = changes?.mapNotNull { conversation ->
                ModelConverter.convertConversationRO(conversation)
            }

            listener.getPostedConversations(postedConversation)
            listener.getNewConversations(newConversations)
            listener.getChangedConversations(changedConversations)
        }

        realm.close()
    }

    /**
     * validates [observeConversationsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateObserveConversationRequest(observeConversationsRequest: ObserveConversationsRequest) {
        if (observeConversationsRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * returns list of [ConversationRO] as per indexes received in
     * @param indexes
     */
    private fun getConversationFromChanges(
        list: RealmResults<ConversationRO>,
        indexes: IntArray?,
    ): List<ConversationRO>? {
        if (list.isEmpty()) {
            return null
        }
        return indexes?.map { index ->
            list[index]!!
        }
    }

    /**
     * runs the worker as per [LoadConversationType] and save data in local db
     */
    fun loadConversations(
        context: Context,
        type: LoadConversationType,
        chatroomId: String
    ): MediatorLiveData<WorkInfo.State> {
        //validates the client request
        RequestUtils.validate()
        return when (type) {
            LoadConversationType.FIRST_TIME -> {
                SyncSDK.startFirstTimeSyncForChatroom(context, chatroomId)
            }

            LoadConversationType.FIRST_TIME_BACKGROUND -> {
                SyncSDK.startFirstTimeBackgroundSync(context, chatroomId)
            }

            LoadConversationType.REOPEN -> {
                SyncSDK.startReopenSyncForChatroom(context, chatroomId)
            }
        }
    }

    /**
     * Observe live conversations
     */
    suspend fun observeLiveConversations(
        context: Context,
        chatroomId: String
    ) {
        val app = FirebaseApp.getInstance("secondary")
        val dataBaseReference = FirebaseDatabase.getInstance(app).reference
            .child("collabcards")
            .child(chatroomId)
        dataBaseReference.keepSynced(true)

        dataBaseReference.childEventListener().collect { result ->
            when (result) {
                is LiveConversationResponse.ChildAdded -> {
                    val latestConversation = result.response?.answerId
                    if (!latestConversation.isNullOrEmpty()) {
                        SyncSDK.startReopenSyncForChatroom(
                            context,
                            chatroomId,
                            latestConversation
                        )
                    }
                }

                is LiveConversationResponse.ChildChanged -> {
                    val latestConversation = result.response?.answerId
                    if (!latestConversation.isNullOrEmpty()) {
                        SyncSDK.startReopenSyncForChatroom(
                            context,
                            chatroomId,
                            latestConversation
                        )
                    }
                }

                is LiveConversationResponse.ChildMoved -> {
                    val latestConversation = result.response?.answerId
                    if (!latestConversation.isNullOrEmpty()) {
                        SyncSDK.startReopenSyncForChatroom(
                            context,
                            chatroomId,
                            latestConversation
                        )
                    }
                }

                is LiveConversationResponse.ChildRemoved -> {
                    val latestConversation = result.response?.answerId
                    if (!latestConversation.isNullOrEmpty()) {
                        SyncSDK.startReopenSyncForChatroom(
                            context,
                            chatroomId,
                            latestConversation
                        )
                    }
                }

                is LiveConversationResponse.OnCancelled -> {
                    Log.e(
                        GroupChatSDK.LOG_TAG,
                        "live conversation failed: ${result.errorMessage}"
                    )
                }
            }
        }
    }

    /**
     * runs the query and returns the conversations as per situations
     * @param getConversationsRequest - client request model to get conversations
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetConversationsResponse> - Base LM response[GetConversationsResponse]
     */
    fun getConversations(getConversationsRequest: GetConversationsRequest): LMResponse<GetConversationsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetConversationsRequest(getConversationsRequest)

        val type = getConversationsRequest.type
        val chatroomId = getConversationsRequest.chatroomId
        val limit = getConversationsRequest.limit
        val conversation = getConversationsRequest.conversation

        return when (type) {
            GetConversationType.NONE -> {
                LMResponse(
                    success = false,
                    errorMessage = "queryType not specified."
                )
            }

            GetConversationType.BELOW -> {
                getBelowConversations(chatroomId, limit, conversation)
            }

            GetConversationType.ABOVE -> {
                getAboveConversation(chatroomId, limit, conversation)
            }

            GetConversationType.TOP -> {
                getTopConversations(chatroomId, limit)
            }

            GetConversationType.BOTTOM -> {
                getBottomConversations(chatroomId, limit)
            }
        }
    }

    /**
     * validates [getConversationsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetConversationsRequest(getConversationsRequest: GetConversationsRequest) {
        if (getConversationsRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (getConversationsRequest.type == GetConversationType.NONE) {
            RequestUtils.throwException("queryType")
        }
    }

    //get conversations below a particular conversation
    private fun getBelowConversations(
        chatroomId: String,
        limit: Int,
        belowConversation: Conversation?
    ): LMResponse<GetConversationsResponse> {
        val realm = Realm.getDefaultInstance()
        val conversationsRO = conversationDB.getConversationsBelow(
            realm,
            chatroomId,
            limit,
            belowConversation?.id,
            belowConversation?.createdEpoch
        )
        val conversations = ModelConverter.convertGetConversationsResponse(conversationsRO)
        realm.close()
        return LMResponse(
            success = true,
            errorMessage = null,
            conversations
        )
    }

    //get conversations above a particular conversation
    private fun getAboveConversation(
        chatroomId: String,
        limit: Int,
        conversation: Conversation?
    ): LMResponse<GetConversationsResponse> {
        val realm = Realm.getDefaultInstance()
        val conversationsRO = conversationDB.getConversationsAbove(
            realm,
            chatroomId,
            limit,
            conversation?.id,
            conversation?.createdEpoch
        )
        val conversations = ModelConverter.convertGetConversationsResponse(conversationsRO)
        realm.close()
        return LMResponse(
            success = true,
            errorMessage = null,
            conversations
        )
    }

    //get conversations from start of a chatroom
    private fun getTopConversations(
        chatroomId: String,
        limit: Int
    ): LMResponse<GetConversationsResponse> {
        val realm = Realm.getDefaultInstance()
        val conversationsRO = conversationDB.getTopConversations(
            realm,
            chatroomId,
            limit
        )
        val conversations = ModelConverter.convertGetConversationsResponse(conversationsRO)
        realm.close()
        return LMResponse(
            success = true,
            errorMessage = null,
            conversations
        )
    }

    //get conversations from end of a chatroom
    private fun getBottomConversations(
        chatroomId: String,
        limit: Int
    ): LMResponse<GetConversationsResponse> {
        val realm = Realm.getDefaultInstance()
        val conversationsRO = conversationDB.getBottomConversations(realm, chatroomId, limit)
        val conversations = ModelConverter.convertGetConversationsResponse(conversationsRO)
        realm.close()
        return LMResponse(
            success = true,
            errorMessage = null,
            conversations
        )
    }

    /**
     * runs the query and returns the conversations above count
     * @param getConversationsCountRequest - client request model to get conversations count
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetConversationsResponse> - Base LM response[GetConversationsResponse]
     */
    fun getConversationsCount(getConversationsCountRequest: GetConversationsCountRequest): LMResponse<GetConversationsCountResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetConversationsCountRequest(getConversationsCountRequest)

        val chatroomId = getConversationsCountRequest.chatroomId
        val conversationId = getConversationsCountRequest.conversation.id ?: ""
        val createdEpoch = getConversationsCountRequest.conversation.createdEpoch ?: 0

        return when (getConversationsCountRequest.type) {
            GetConversationCountType.NONE -> {
                LMResponse(
                    success = false,
                    errorMessage = "queryType not specified."
                )
            }
            GetConversationCountType.BELOW -> {
                getConversationsBelowCount(
                    chatroomId,
                    conversationId,
                    createdEpoch
                )
            }
            GetConversationCountType.ABOVE -> {
                getConversationsAboveCount(
                    chatroomId,
                    conversationId,
                    createdEpoch,
                )
            }
        }
    }

    /**
     * validates [getConversationsCountRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetConversationsCountRequest(getConversationsCountRequest: GetConversationsCountRequest) {
        if (getConversationsCountRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (getConversationsCountRequest.type == GetConversationCountType.NONE) {
            RequestUtils.throwException("queryType")
        }
    }

    // gets count of conversations above the provided conversation
    private fun getConversationsAboveCount(
        chatroomId: String,
        conversationId: String,
        createdEpoch: Long
    ): LMResponse<GetConversationsCountResponse> {
        val realm = Realm.getDefaultInstance()
        val count = conversationDB.getConversationsAboveCount(
            realm,
            chatroomId,
            conversationId,
            createdEpoch
        )
        realm.close()
        val aboveConversationsCount = ModelConverter.convertGetConversationsCountResponse(count)
        return LMResponse(
            success = true,
            errorMessage = null,
            aboveConversationsCount
        )
    }

    // gets count of conversations below the provided conversation
    private fun getConversationsBelowCount(
        chatroomId: String,
        conversationId: String,
        createdEpoch: Long
    ): LMResponse<GetConversationsCountResponse> {
        val realm = Realm.getDefaultInstance()
        val count = conversationDB.getConversationsBelowCount(
            realm,
            chatroomId,
            conversationId,
            createdEpoch
        )
        realm.close()
        val belowConversationsCount = ModelConverter.convertGetConversationsCountResponse(count)
        return LMResponse(
            success = true,
            errorMessage = null,
            belowConversationsCount
        )
    }

    /**
     * save conversation in local db
     * @param saveConversationRequest - client request model to save a temporary conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * */
    fun saveTemporaryConversation(saveConversationRequest: SaveConversationRequest) {
        // validates the client request
        RequestUtils.validate()
        validateSaveConversationRequest(saveConversationRequest)

        val conversation =
            ModelConverter.createConversation(saveConversationRequest.conversation)

        conversationDB.saveTemporaryConversation(conversation)
    }

    private fun validateSaveConversationRequest(saveConversationRequest: SaveConversationRequest) {
        if (saveConversationRequest.conversation.id.isNullOrEmpty()) {
            RequestUtils.throwException("conversation")
        }
    }

    /**
     * updates temporary conversation in local db
     * @param updateTemporaryConversationRequest - client request model to update temporary conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * */
    fun updateTemporaryConversation(updateTemporaryConversationRequest: UpdateTemporaryConversationRequest) {
        // validates the client request
        RequestUtils.validate()
        validateUpdateTemporaryConversationRequest(updateTemporaryConversationRequest)

        conversationDB.updateTemporaryConversation(
            updateTemporaryConversationRequest.conversationId,
            updateTemporaryConversationRequest.localSavedEpoch
        )
    }

    private fun validateUpdateTemporaryConversationRequest(updateTemporaryConversationRequest: UpdateTemporaryConversationRequest) {
        if (updateTemporaryConversationRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (updateTemporaryConversationRequest.localSavedEpoch == -1L) {
            RequestUtils.throwException("localSavedEpoch")
        }
    }

    /**
     * update conversation uuid in local db
     * @param updateConversationUploadWorkerUUIDRequest - client request model to update conversation upload worker
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * */
    fun updateConversationUploadWorkerUUID(updateConversationUploadWorkerUUIDRequest: UpdateConversationUploadWorkerUUIDRequest) {
        // validates the client request
        RequestUtils.validate()
        validateUpdateConversationUploadWorkerUUIDRequest(updateConversationUploadWorkerUUIDRequest)

        conversationDB.updateConversationUploadWorkerUUID(
            updateConversationUploadWorkerUUIDRequest.conversationId,
            updateConversationUploadWorkerUUIDRequest.uuid
        )
    }

    private fun validateUpdateConversationUploadWorkerUUIDRequest(
        updateConversationUploadWorkerUUIDRequest: UpdateConversationUploadWorkerUUIDRequest
    ) {
        if (updateConversationUploadWorkerUUIDRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (updateConversationUploadWorkerUUIDRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }

    /**
     * return a single conversation from local db
     * @param getConversationRequest: client request model to get a conversation
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetConversationResponse> - Base LM response[GetConversationResponse]
     */
    fun getConversation(getConversationRequest: GetConversationRequest): LMResponse<GetConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetConversationRequest(getConversationRequest)

        val realm = Realm.getDefaultInstance()
        val conversationRO =
            conversationDB.getConversation(realm, getConversationRequest.conversationId)
        return if (conversationRO == null) {
            LMResponse(
                success = false,
                errorMessage = "Conversation w.r.t conversation id not found"
            )
        } else {
            LMResponse(
                success = true,
                errorMessage = null,
                ModelConverter.convertGetConversationResponse(conversationRO)
            )
        }
    }

    /**
     * validates [getConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetConversationRequest(getConversationRequest: GetConversationRequest) {
        if (getConversationRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param editConversationRequest - client request model to edit a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<EditConversationResponse> - Base LM response[EditConversationResponse]
     */
    suspend fun editConversation(editConversationRequest: EditConversationRequest): LMResponse<EditConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateEditConversationRequest(editConversationRequest)

        val request = _EditConversationRequest_.Builder()
            .conversationId(editConversationRequest.conversationId)
            .text(editConversationRequest.text)
            .shareLink(editConversationRequest.shareLink)
            .build()

        return when (val response = conversationApi.editConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                val conversation = body.data?.conversation

                //if success -> make db query
                conversation?.let {
                    conversationDB.updateEditedConversation(it.id ?: "", it.answer, it.ogTags)
                }

                ModelConverter.convertEditConversationAPIResponse(body)
            }
        }
    }

    /**
     * validates [editConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateEditConversationRequest(editConversationRequest: EditConversationRequest) {
        if (editConversationRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (editConversationRequest.text.isEmpty()) {
            RequestUtils.throwException("text")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param deleteConversationsRequest - client request model to delete conversations
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<DeleteConversationRequest> - Base LM response[DeleteConversationsRequest]
     */
    suspend fun deleteConversations(deleteConversationsRequest: DeleteConversationsRequest): LMResponse<DeleteConversationsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateDeleteConversationRequest(deleteConversationsRequest)

        val request = _DeleteConversationsRequest_.Builder()
            .conversationIds(deleteConversationsRequest.conversationIds)
            .build()

        return when (val response = conversationApi.deleteConversations(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    response.body.success,
                    response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                //if success -> make db query
                conversationDB.updateDeletedConversations(deleteConversationsRequest.conversationIds)

                ModelConverter.convertDeleteConversationsAPIResponse(body)
            }
        }
    }

    /**
     * validates [deleteConversationsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateDeleteConversationRequest(deleteConversationsRequest: DeleteConversationsRequest) {
        if (deleteConversationsRequest.conversationIds.isEmpty()) {
            RequestUtils.throwException("conversationIds")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param putReactionRequest - client request model to put a reaction on a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun putReaction(putReactionRequest: PutReactionRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validatePutReactionRequest(putReactionRequest)

        // builds internal request model
        val request =
            _PutReactionRequest_.Builder()
                .chatroomId(putReactionRequest.chatroomId)
                .conversationId(putReactionRequest.conversationId)
                .reaction(putReactionRequest.reaction)
                .build()

        // calls api and processes the response accordingly
        return when (val response = conversationApi.putReaction(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {

                putReactionRequest.conversationId?.let { conversationId ->
                    conversationDB.updateConversationReaction(
                        putReactionRequest.reaction,
                        conversationId
                    )
                }

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [putReactionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePutReactionRequest(putReactionRequest: PutReactionRequest) {
        if (putReactionRequest.chatroomId.isNullOrEmpty() && putReactionRequest.conversationId.isNullOrEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (putReactionRequest.reaction.isEmpty()) {
            RequestUtils.throwException("reaction")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param deleteReactionRequest - client request model to delete a reaction on a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun deleteReaction(deleteReactionRequest: DeleteReactionRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateDeleteReactionRequest(deleteReactionRequest)

        // builds internal request model
        val request =
            _DeleteReactionRequest_.Builder()
                .chatroomId(deleteReactionRequest.chatroomId)
                .conversationId(deleteReactionRequest.conversationId)
                .build()

        // calls api and processes the response accordingly
        return when (val response = conversationApi.deleteReaction(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {

                deleteReactionRequest.conversationId?.let {
                    conversationDB.removeConversationReaction(it)
                }

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [deleteReactionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateDeleteReactionRequest(deleteReactionRequest: DeleteReactionRequest) {
        if (deleteReactionRequest.chatroomId.isNullOrEmpty() && deleteReactionRequest.conversationId.isNullOrEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param putMultimediaRequest - client request model to upload a conversation attachment
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<PutMultimediaResponse> - Base LM response[PutMultimediaResponse]
     */
    suspend fun putMultimedia(
        putMultimediaRequest: PutMultimediaRequest
    ): LMResponse<PutMultimediaResponse> {
        // validates the client request
        RequestUtils.validate()
        validatePutMultimediaRequest(putMultimediaRequest)

        val request = _PutMultimediaRequest_.Builder()
            .conversationId(putMultimediaRequest.conversationId)
            .name(putMultimediaRequest.name)
            .url(putMultimediaRequest.url)
            .thumbnailUrl(putMultimediaRequest.thumbnailUrl)
            .type(putMultimediaRequest.type)
            .filesCount(putMultimediaRequest.filesCount)
            .index(putMultimediaRequest.index)
            .width(putMultimediaRequest.width)
            .height(putMultimediaRequest.height)
            .meta(ModelConverter.createAttachmentMeta(putMultimediaRequest.meta))
            .build()

        return when (val response = conversationApi.putMultimedia(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertPutMultimediaAPIResponse(body)
            }
        }
    }

    /**
     * validates [putMultimediaRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePutMultimediaRequest(putMultimediaRequest: PutMultimediaRequest) {
        if (putMultimediaRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (putMultimediaRequest.url.isEmpty()) {
            RequestUtils.throwException("url")
        }
    }
}