package com.likeminds.likemindschat.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.dm.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class DMClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().dmSubComponent()?.inject(this)
    }

    private val dmApi by lazy {
        groupChatSDK.getDMApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return CheckDMTabResponse - CheckDMTabResponse model
     */
    suspend fun checkDMTab(): LMResponse<CheckDMTabResponse> {
        // validates the client request
        RequestUtils.validate()

        return when (val response = dmApi.checkDMTab()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMTabResponse(body)
            }
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param sendDMRequest - client request model to send a dm request
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return SendDMResponse - SendDMResponse model for sendDMRequest
     */
    suspend fun sendDMRequest(sendDMRequest: SendDMRequest): LMResponse<SendDMResponse> {
        // validates the client request
        RequestUtils.validate()
        validateSendDMRequest(sendDMRequest)

        // builds internal request model
        val request = _SendDMRequest_.Builder()
            .chatroomId(sendDMRequest.chatroomId)
            .chatRequestState(sendDMRequest.chatRequestState)
            .text(sendDMRequest.text)
            .build()

        // calls api and processes the response accordingly
        return when (val response = dmApi.sendDMRequest(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertSendDMRequestResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSendDMRequest(sendDMRequest: SendDMRequest) {
        if (sendDMRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param checkDMStatusRequest - client request model to check whether dm is enabled or not
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CheckDMStatusResponse - CheckDMStatusResponse model for checkDMStatusRequest
     */
    suspend fun checkDMStatus(checkDMStatusRequest: CheckDMStatusRequest): LMResponse<CheckDMStatusResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCheckDMStatusRequest(checkDMStatusRequest)

        // builds internal request model
        val request = _CheckDMStatusRequest_.Builder()
            .requestFrom(checkDMStatusRequest.requestFrom)
            .chatroomId(checkDMStatusRequest.chatroomId)
            .uuid(checkDMStatusRequest.uuid)
            .build()

        // calls api and processes the response accordingly
        return when (val response = dmApi.checkDMStatus(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMStatusResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCheckDMStatusRequest(checkDMStatusRequest: CheckDMStatusRequest) {
        if (checkDMStatusRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }

        if (checkDMStatusRequest.requestFrom.isEmpty()) {
            RequestUtils.throwException("requestFrom")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param blockMemberRequest - client request model to block a member
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return BlockMemberResponse - BlockMemberResponse model for blockMemberRequest
     */
    suspend fun blockMember(blockMemberRequest: BlockMemberRequest): LMResponse<BlockMemberResponse> {
        // validates the client request
        RequestUtils.validate()
        validateBlockMemberRequest(blockMemberRequest)

        // builds internal request model
        val request = _BlockMemberRequest_.Builder()
            .chatroomId(blockMemberRequest.chatroomId)
            .status(blockMemberRequest.status)
            .build()

        return when (val response = dmApi.blockMember(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertBlockMemberResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateBlockMemberRequest(blockMemberRequest: BlockMemberRequest) {
        if (blockMemberRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param checkDMLimitRequest - client request model to check dm limit
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CheckDMLimitResponse - CheckDMLimitResponse model for checkDMLimitRequest
     */
    suspend fun checkDMLimit(checkDMLimitRequest: CheckDMLimitRequest): LMResponse<CheckDMLimitResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCheckDMLimitRequest(checkDMLimitRequest)

        // builds internal request model
        val request = _CheckDMLimitRequest_.Builder()
            .uuid(checkDMLimitRequest.uuid)
            .build()

        return when (val response = dmApi.checkDMLimit(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMLimitResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCheckDMLimitRequest(checkDMLimitRequest: CheckDMLimitRequest) {
        if (checkDMLimitRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param createDMChatroomRequest - client request model to create a dm chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CreateDMChatroomResponse - CreateDMChatroomResponse model for createDMChatroomRequest
     */
    suspend fun createDMChatroom(createDMChatroomRequest: CreateDMChatroomRequest): LMResponse<CreateDMChatroomResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCreateDMChatroomRequest(createDMChatroomRequest)

        val request = _CreateDMChatroomRequest_.Builder()
            .uuid(createDMChatroomRequest.uuid)
            .build()

        return when (val response = dmApi.createDMChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCreateDMChatroomResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCreateDMChatroomRequest(createDMChatroomRequest: CreateDMChatroomRequest) {
        if (createDMChatroomRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }
}