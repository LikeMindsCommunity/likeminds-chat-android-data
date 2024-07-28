package com.likeminds.chatinternalsdk.sdk

import com.likeminds.chatinternalsdk.sdk.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class SDKApiImpl @Inject constructor(private val sdkReceiver: SDKReceiver) : SDKApi {

    override suspend fun initiateUser(
        apiKey: String,
        request: _InitiateUserRequest_
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>> {
        return sdkReceiver.initiateUser(apiKey, request)
    }

    override suspend fun validateUser(): NetworkResponse<APIResponse<_ValidateUserResponse_>> {
        return sdkReceiver.validateUser()
    }
}