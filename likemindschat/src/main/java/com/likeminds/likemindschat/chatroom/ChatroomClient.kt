package com.likeminds.likemindschat.chatroom

import com.likeminds.internalsdk.chatroom.model._FollowChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._GetChatroomRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.FollowChatroomRequest
import com.likeminds.likemindschat.chatroom.model.GetChatroomRequest
import com.likeminds.likemindschat.chatroom.model.GetChatroomResponse
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

    private fun validateFollowChatroomRequest(followChatroomRequest: FollowChatroomRequest) {
        if (followChatroomRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (followChatroomRequest.memberId.isEmpty()) {
            RequestUtils.throwException("memberId")
        }
    }
}