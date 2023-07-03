package com.likeminds.likemindschat.conversation

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkInfo
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.ConversationRO
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.conversation.util.*
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
                    //todo change true as per request
                    conversationDB.savePostedConversation(it, true)
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
     * write the observer query and returns the data in listener
     * @param chatroomId: id of the chatroom
     * @param listener: [ConversationChangeListener] listener to observe conversation
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     */
    suspend fun observeConversations(
        chatroomId: String,
        listener: ConversationChangeListener
    ) {
        //validates the client request
        RequestUtils.validate()
        validateObserveConversationRequest(chatroomId)

        val realm = Realm.getDefaultInstance()

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
     * validates [chatroomId]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateObserveConversationRequest(chatroomId: String) {
        if (chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

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

    fun loadConversations(
        context: Context,
        type: LoadConversationType,
        chatroomId: String
    ): MediatorLiveData<WorkInfo.State>? {
        return when (type) {
            LoadConversationType.FIRST_TIME -> {
                SyncSDK.startFirstTimeSyncForChatroom(context, chatroomId)
            }

            LoadConversationType.FIRST_TIME_BACKGROUND -> {
                SyncSDK.startFirstTimeSyncForChatroom(context, chatroomId)
            }

            LoadConversationType.REOPEN -> {
                SyncSDK.startFirstTimeSyncForChatroom(context, chatroomId)
            }

            else -> {
                null
            }
        }
    }

    /**
     * write the query and returns the conversations as per situations
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
                val conversations = conversationDB.getConversationsBelow(
                    chatroomId,
                    limit,
                    conversation?.id,
                    conversation?.createdEpoch
                )
                LMResponse(
                    success = true,
                    errorMessage = null,
                    ModelConverter.convertGetConversationsResponse(conversations)
                )
            }

            GetConversationType.ABOVE -> {
                val conversations = conversationDB.getConversationsAbove(
                    chatroomId,
                    limit,
                    conversation?.id,
                    conversation?.createdEpoch
                )
                LMResponse(
                    success = true,
                    errorMessage = null,
                    ModelConverter.convertGetConversationsResponse(conversations)
                )
            }

            GetConversationType.TOP -> {
                val conversations = conversationDB.getTopConversations(chatroomId, limit)
                LMResponse(
                    success = true,
                    errorMessage = null,
                    ModelConverter.convertGetConversationsResponse(conversations)
                )
            }

            GetConversationType.BOTTOM -> {
                val conversations = conversationDB.getBottomConversations(chatroomId, limit)
                LMResponse(
                    success = true,
                    errorMessage = null,
                    ModelConverter.convertGetConversationsResponse(conversations)
                )
            }

            GetConversationType.INTERMEDIATE -> {
                val medianConversation = conversationDB.getConversation(conversation?.id ?: "")

                if (medianConversation == null) {
                    LMResponse(
                        success = false,
                        errorMessage = "Conversation w.r.t conversation not found."
                    )
                } else {
                    val aboveConversations = conversationDB.getConversationsAbove(
                        chatroomId,
                        limit,
                        conversation?.id,
                        conversation?.createdEpoch
                    )
                    val belowConversations = conversationDB.getConversationsBelow(
                        chatroomId,
                        limit,
                        conversation?.id,
                        conversation?.createdEpoch
                    )

                    val conversations = aboveConversations + medianConversation + belowConversations

                    LMResponse(
                        success = true,
                        errorMessage = null,
                        ModelConverter.convertGetConversationsResponse(conversations)
                    )
                }
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

    /**
     * Converts client request model to internal model and calls the api
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

        val conversationRO = conversationDB.getConversation(getConversationRequest.conversationId)
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
     * @param deleteConversationRequest - client request model to post a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<DeleteConversationRequest> - Base LM response[DeleteConversationRequest]
     */
    suspend fun deleteConversations(deleteConversationRequest: DeleteConversationRequest): LMResponse<DeleteConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateDeleteConversationRequest(deleteConversationRequest)

        val request = _DeleteConversationRequest_.Builder()
            .conversationIds(deleteConversationRequest.conversationIds)
            .build()

        return when (val response = conversationApi.deleteConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    response.body.success,
                    response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                //if success -> make db query
                conversationDB.updateDeletedConversations(deleteConversationRequest.conversationIds)

                ModelConverter.convertDeleteConversationAPIResponse(body)
            }
        }
    }

    /**
     * validates [deleteConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateDeleteConversationRequest(deleteConversationRequest: DeleteConversationRequest) {
        if (deleteConversationRequest.conversationIds.isEmpty()) {
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
     * @param putMultimediaRequest - client request model to post a conversation
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