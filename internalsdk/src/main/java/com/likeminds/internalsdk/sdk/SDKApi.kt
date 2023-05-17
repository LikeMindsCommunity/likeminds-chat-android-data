package com.likeminds.internalsdk.sdk

import com.likeminds.internalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface SDKApi {

    // api to initiate user
    suspend fun initiateUser(
        apiKey: String,
        request: _InitiateUserRequest_,
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>>
}