package com.likeminds.internalsdk.sdk

import com.likeminds.internalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SDKNetworkApi {

    @POST("sdk/initiate")
    suspend fun initiateUser(
        @Header("x-api-key") apiKey: String,
        @Body request: _InitiateUserRequest_,
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>>
}