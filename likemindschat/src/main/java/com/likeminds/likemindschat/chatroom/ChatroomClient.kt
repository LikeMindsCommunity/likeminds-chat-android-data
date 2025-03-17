package com.likeminds.likemindschat.chatroom

import android.content.Context
import android.util.Log
import com.likeminds.chatinternalsdk.chatroom.model.*
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.sync.SyncSDK
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import kotlinx.coroutines.*
import javax.inject.Inject

class ChatroomClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        Log.d("PUI","ChatroomClient attach dagger is called")
        LikeMindsChatApplication.getInstance().chatroomComponent()?.inject(this)
    }

    private val chatroomApi by lazy {
        chatSDK.getChatroomApi()
    }

    private val chatroomDB by lazy {
        chatSDK.getChatroomDb()
    }

    private val sdkPreferences by lazy {
        chatSDK.getSDKPreferences()
    }

    private val syncPreferences by lazy {
        chatSDK.getSyncPreference()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getChatroomRequest - client request model to fetch chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetChatroomResponse - GetChatroomResponse model for getChatroomRequest
     */
    fun getChatroom(getChatroomRequest: GetChatroomRequest): LMResponse<GetChatroomResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetChatroomRequest(getChatroomRequest)

        val realm = Realm.getDefaultInstance()
        realm.refresh()
        val chatroomRO = chatroomDB.getChatroom(realm, getChatroomRequest.chatroomId)

        val getChatroomResponse = ModelConverter.convertGetChatroomResponse(chatroomRO)
        val chatroom = getChatroomResponse.chatroom

        realm.close()
        return if (chatroom == null) {
            LMResponse(
                success = false,
                errorMessage = "Chatroom with respect to chatroomId not found."
            )
        } else {
            LMResponse(
                success = true,
                errorMessage = null,
                getChatroomResponse
            )
        }
    }

    /**
     * validates [getChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetChatroomRequest(getChatroomRequest: GetChatroomRequest) {
        if (getChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getChatroomActionRequest - client request model to fetch chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return [GetChatroomActionsResponse] - GetChatroomActionsResponse model for [getChatroomActions]
     */
    suspend fun getChatroomActions(getChatroomActionRequest: GetChatroomActionsRequest): LMResponse<GetChatroomActionsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetChatroomActionsRequest(getChatroomActionRequest)

        // builds internal request model
        val request =
            _GetChatroomActionsRequest_.Builder()
                .chatroomId(getChatroomActionRequest.chatroomId)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.getChatroomActions(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetChatroomActionsAPIResponse(body)
            }
        }
    }

    /**
     * validates [getChatroomActionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetChatroomActionsRequest(getChatroomActionRequest: GetChatroomActionsRequest) {
        if (getChatroomActionRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param followChatroomRequest - client request model to follow a chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun followChatroom(followChatroomRequest: FollowChatroomRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateFollowChatroomRequest(followChatroomRequest)

        // builds internal request model
        val request =
            _FollowChatroomRequest_.Builder()
                .chatroomId(followChatroomRequest.chatroomId)
                .uuid(followChatroomRequest.uuid)
                .value(followChatroomRequest.value)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.followChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {

                //if success -> make the db call
                chatroomDB.updateChatroomFollowStatus(
                    followChatroomRequest.chatroomId,
                    followChatroomRequest.value
                )

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [followChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateFollowChatroomRequest(followChatroomRequest: FollowChatroomRequest) {
        if (followChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (followChatroomRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param leaveSecretChatroomRequest - client request model to leave a secret chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun leaveSecretChatroom(leaveSecretChatroomRequest: LeaveSecretChatroomRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateLeaveSecretChatroomRequest(leaveSecretChatroomRequest)

        // builds internal request model
        val request =
            _LeaveSecretChatroomRequest_.Builder()
                .chatroomId(leaveSecretChatroomRequest.chatroomId)
                .isSecret(leaveSecretChatroomRequest.isSecret)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.leaveSecretChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                //if success -> make the db call
                chatroomDB.updateSecretChatroomLeaveStatus(leaveSecretChatroomRequest.chatroomId)

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [leaveSecretChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateLeaveSecretChatroomRequest(leaveSecretChatroomRequest: LeaveSecretChatroomRequest) {
        if (leaveSecretChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param muteChatroomRequest - client request model to mute secret chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun muteChatroom(muteChatroomRequest: MuteChatroomRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateMuteChatroomRequest(muteChatroomRequest)

        // builds internal request model
        val request =
            _MuteChatroomRequest_.Builder()
                .chatroomId(muteChatroomRequest.chatroomId)
                .value(muteChatroomRequest.value)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.muteChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                //if success -> make the db call
                chatroomDB.updateChatroomMuteStatus(
                    muteChatroomRequest.chatroomId,
                    muteChatroomRequest.value
                )
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [muteChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateMuteChatroomRequest(muteChatroomRequest: MuteChatroomRequest) {
        if (muteChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param markReadChatroomRequest - client request model to mark chatroom as read
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun markReadChatroom(markReadChatroomRequest: MarkReadChatroomRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateMarkReadChatroomRequest(markReadChatroomRequest)

        // builds internal request model
        val request =
            _MarkReadChatroomRequest_.Builder()
                .chatroomId(markReadChatroomRequest.chatroomId)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.markReadChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [markReadChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateMarkReadChatroomRequest(markReadChatroomRequest: MarkReadChatroomRequest) {
        if (markReadChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param setChatroomTopicRequest - client request model to set a conversation as topic for chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun setChatroomTopic(setChatroomTopicRequest: SetChatroomTopicRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateSetChatroomTopicRequest(setChatroomTopicRequest)

        // builds internal request model
        val request =
            _SetChatroomTopicRequest_.Builder()
                .chatroomId(setChatroomTopicRequest.chatroomId)
                .conversationId(setChatroomTopicRequest.conversationId)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.setChatroomTopic(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {

                //if success -> make the db call
                chatroomDB.updateChatroomTopic(
                    setChatroomTopicRequest.chatroomId,
                    setChatroomTopicRequest.conversationId
                )

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [setChatroomTopicRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSetChatroomTopicRequest(setChatroomTopicRequest: SetChatroomTopicRequest) {
        if (setChatroomTopicRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (setChatroomTopicRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getParticipantsRequest - client request model to get list of participants in chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetParticipantsResponse - GetParticipantsResponse model for getParticipantsRequest
     */
    suspend fun getParticipants(getParticipantsRequest: GetParticipantsRequest): LMResponse<GetParticipantsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetParticipantsRequest(getParticipantsRequest)

        // builds internal request model
        val request =
            _GetParticipantsRequest_.Builder()
                .isChatroomSecret(getParticipantsRequest.isChatroomSecret)
                .chatroomId(getParticipantsRequest.chatroomId)
                .participantName(getParticipantsRequest.participantName)
                .page(getParticipantsRequest.page)
                .pageSize(getParticipantsRequest.pageSize)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.getParticipants(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetParticipantsAPIResponse(body)
            }
        }
    }

    /**
     * validates [getParticipantsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetParticipantsRequest(getParticipantsRequest: GetParticipantsRequest) {
        if (getParticipantsRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * sets last seen to true and saves draft response
     * @param updateLastSeenAndDraftRequest - client request model to get list of participants in chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     */
    fun updateLastSeenAndDraft(updateLastSeenAndDraftRequest: UpdateLastSeenAndDraftRequest) {
        // validates the client request
        RequestUtils.validate()
        validateUpdateLastSeenAndDraftRequest(updateLastSeenAndDraftRequest)

        val chatroomId = updateLastSeenAndDraftRequest.chatroomId
        val draft = updateLastSeenAndDraftRequest.draft

        chatroomDB.updateLastSeenAndDraft(chatroomId, draft)
    }

    /**
     * validates [updateLastSeenAndDraftRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateUpdateLastSeenAndDraftRequest(updateLastSeenAndDraftRequest: UpdateLastSeenAndDraftRequest) {
        if (updateLastSeenAndDraftRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param editChatroomTitleRequest - client request model to edit a chatroom title
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun editChatroomTitle(editChatroomTitleRequest: EditChatroomTitleRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateEditChatroomTitleRequest(editChatroomTitleRequest)

        // builds internal request model
        val request =
            _EditChatroomTitleRequest_.Builder()
                .chatroomId(editChatroomTitleRequest.chatroomId)
                .text(editChatroomTitleRequest.text)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.editChatroomTitle(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {

                //if success -> make the db call
                chatroomDB.updateChatroomTitle(
                    editChatroomTitleRequest.chatroomId,
                    editChatroomTitleRequest.text
                )

                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [editChatroomTitleRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateEditChatroomTitleRequest(editChatroomTitleRequest: EditChatroomTitleRequest) {
        if (editChatroomTitleRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (editChatroomTitleRequest.text.isEmpty()) {
            RequestUtils.throwException("text")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param updateChannelInviteRequest - client request model to update the status of channel invite
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun updateChannelInvite(updateChannelInviteRequest: UpdateChannelInviteRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateUpdateChannelInviteRequest(updateChannelInviteRequest)

        // builds internal request model
        val request = _UpdateChannelInviteRequest_.Builder()
            .channelId(updateChannelInviteRequest.channelId)
            .inviteStatus(updateChannelInviteRequest.inviteStatus.value)
            .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.updateChannelInvite(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [updateChannelInviteRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateUpdateChannelInviteRequest(updateChannelInviteRequest: UpdateChannelInviteRequest) {
        if (updateChannelInviteRequest.channelId.isEmpty()) {
            RequestUtils.throwException("channelId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getChannelInviteRequest - client request model to get the channel invites
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetChannelInviteResponse> - GetChannelInviteResponse model for getChannelInviteRequest
     */
    suspend fun getChannelInvites(getChannelInviteRequest: GetChannelInviteRequest): LMResponse<GetChannelInviteResponse> {
        // validates the client request
        RequestUtils.validate()

        // builds internal request model
        val request = _GetChannelInviteRequest_.Builder()
            .channelType(getChannelInviteRequest.channelType)
            .page(getChannelInviteRequest.page)
            .pageSize(getChannelInviteRequest.pageSize)
            .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.getChannelInvites(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                ModelConverter.convertGetChannelInvitesResponse(response.body)
            }
        }
    }

    /**
     * Returns the count of chatrooms user has joined from local DB
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetJoinedChatroomCountResponse> - GetJoinedChatroomCountResponse model
     */
    fun getJoinedChatroomsCount(): LMResponse<GetJoinedChatroomCountResponse> {
        // validates the client request
        RequestUtils.validate()

        // get the count from local DB
        val realm = Realm.getDefaultInstance()
        val joinedChatroomCount = chatroomDB.getJoinedChatroomsCount(realm)

        val getJoinedChatroomCountResponse = GetJoinedChatroomCountResponse(
            joinedChatroomCount.first,
            joinedChatroomCount.second
        )

        realm.close()

        return LMResponse(
            true,
            null,
            getJoinedChatroomCountResponse
        )
    }

    /**
     * Returns the unread conversations count in local DB
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<GetUnreadConversationsCountResponse> - GetUnreadConversationsCountResponse model
     */
    suspend fun getUnreadConversationsCount(context: Context): LMResponse<GetUnreadConversationsCountResponse> {
        // validates the client request
        RequestUtils.validate()

        if (sdkPreferences.getAccessToken().isNullOrEmpty()) {
            return LMResponse(
                true,
                null,
                GetUnreadConversationsCountResponse(0, 0)
            )
        }

        return withContext(Dispatchers.IO) {
            // start the group chatroom sync to get updated group chatroom unread conversations count
            //check whether db is empty or not
            val isFirstTime = ChatDBUtil.isEmpty()
            /**
             * if empty start first time chatroom worker else reopen
             */
            if (isFirstTime) {
                SyncSDK.startFirstHomeFeedSync(context)
            } else {
                SyncSDK.startReopenSyncForHomeFeed(context)
            }

            // start the DM sync to get updated dm unread conversations count
            val doesDMChatroomExists = ChatDBUtil.doesDMChatroomExists()
            val syncTimestamp = syncPreferences.getTimestampForSyncDM()
            if (!doesDMChatroomExists && syncTimestamp == 0L) {
                SyncSDK.startFirstTimeDMFeedSync(context)
            } else {
                SyncSDK.startReopenSyncForDMFeed(context)
            }

            // adding delay to provide time to sync APIs to update the DB
            delay(500)

            // get the count from local DB
            val realm = Realm.getDefaultInstance()
            val unreadConversationCount = chatroomDB.getUnreadConversationsCount(realm)

            val getUnreadConversationsCountResponse = GetUnreadConversationsCountResponse(
                unreadConversationCount.first,
                unreadConversationCount.second
            )

            realm.close()

            LMResponse(
                true,
                null,
                getUnreadConversationsCountResponse
            )
        }
    }
}