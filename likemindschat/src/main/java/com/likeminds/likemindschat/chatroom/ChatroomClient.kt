package com.likeminds.likemindschat.chatroom

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class ChatroomClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().chatroomComponent()?.inject(this)
    }

    private val chatroomApi by lazy {
        groupChatSDK.getChatroomApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getChatroomRequest - client request model to fetch chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetChatroomResponse - GetChatroomResponse model for getChatroomRequest
     */
    suspend fun getChatroom(getChatroomRequest: GetChatroomRequest): LMResponse<GetChatroomResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetPostRequest(getChatroomRequest)

        // builds internal request model
        val request =
            _GetChatroomRequest_.Builder().chatroomId(getChatroomRequest.chatroomId).build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.getChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                return ModelConverter.convertGetChatroomResponse(body)
            }
        }
    }

    /**
     * validates [getChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetPostRequest(getChatroomRequest: GetChatroomRequest) {
        if (getChatroomRequest.chatroomId.isEmpty()) {
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
            _FollowChatroomRequest_.Builder().chatroomId(followChatroomRequest.chatroomId)
                .memberId(followChatroomRequest.memberId)
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
        if (followChatroomRequest.memberId.isEmpty()) {
            RequestUtils.throwException("memberId")
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
            _LeaveSecretChatroomRequest_.Builder().chatroomId(leaveSecretChatroomRequest.chatroomId)
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
        if (leaveSecretChatroomRequest.chatroomId == -1) {
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
            _MuteChatroomRequest_.Builder().chatroomId(muteChatroomRequest.chatroomId)
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
        if (muteChatroomRequest.chatroomId == -1) {
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
            _MarkReadChatroomRequest_.Builder().chatroomId(markReadChatroomRequest.chatroomId)
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
        if (markReadChatroomRequest.chatroomId == -1) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param shareChatroomUrlRequest - client request model to get chatroom's share url
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return ShareChatroomUrlResponse - ShareChatroomUrlResponse model for shareChatroomUrlRequest
     */
    suspend fun shareChatroomUrl(shareChatroomUrlRequest: ShareChatroomUrlRequest): LMResponse<ShareChatroomUrlResponse> {
        // validates the client request
        RequestUtils.validate()
        validateShareChatroomUrlRequest(shareChatroomUrlRequest)

        // builds internal request model
        val request =
            _ShareChatroomUrlRequest_.Builder().chatroomId(shareChatroomUrlRequest.chatroomId)
                .domain(shareChatroomUrlRequest.domain)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.shareChatroomUrl(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                return ModelConverter.convertShareChatroomUrlResponse(body)
            }
        }
    }

    /**
     * validates [shareChatroomUrlRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateShareChatroomUrlRequest(shareChatroomUrlRequest: ShareChatroomUrlRequest) {
        if (shareChatroomUrlRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (shareChatroomUrlRequest.domain.isEmpty()) {
            RequestUtils.throwException("domain")
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
            _SetChatroomTopicRequest_.Builder().chatroomId(setChatroomTopicRequest.chatroomId)
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
        if (setChatroomTopicRequest.chatroomId == -1) {
            RequestUtils.throwException("chatroomId")
        }
        if (setChatroomTopicRequest.conversationId == -1) {
            RequestUtils.throwException("conversationId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getChatroomParticipantsRequest - client request model to get list of participants in chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetChatroomParticipantsResponse - GetChatroomParticipantsResponse model for getChatroomParticipantsRequest
     */
    suspend fun getChatroomParticipants(getChatroomParticipantsRequest: GetChatroomParticipantsRequest): LMResponse<GetChatroomParticipantsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetChatroomParticipantsRequest(getChatroomParticipantsRequest)

        // builds internal request model
        val request =
            _GetChatroomParticipantsRequest_.Builder()
                .isChatroomSecret(getChatroomParticipantsRequest.isChatroomSecret)
                .chatroomId(getChatroomParticipantsRequest.chatroomId)
                .participantName(getChatroomParticipantsRequest.participantName)
                .page(getChatroomParticipantsRequest.page)
                .pageSize(getChatroomParticipantsRequest.pageSize)
                .build()

        // calls api and processes the response accordingly
        return when (val response = chatroomApi.getChatroomParticipants(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                return ModelConverter.convertGetChatroomParticipantsResponse(body)
            }
        }
    }

    /**
     * validates [getChatroomParticipantsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetChatroomParticipantsRequest(getChatroomParticipantsRequest: GetChatroomParticipantsRequest) {
        if (getChatroomParticipantsRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }
}