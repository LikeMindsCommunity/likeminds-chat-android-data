package com.likeminds.chatinternalsdk.sdk

import com.likeminds.chatinternalsdk.sdk.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface SDKApi {

    // api to initiate user
    suspend fun initiateUser(
        apiKey: String,
        request: _InitiateUserRequest_,
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>>

    // api to validate user
    suspend fun validateUser(): NetworkResponse<APIResponse<_ValidateUserResponse_>>
}