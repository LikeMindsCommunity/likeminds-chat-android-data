package com.likeminds.internalsdk.sdk

import com.likeminds.internalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.sdk.model._ValidateUserResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface SDKNetworkApi {

    @POST("sdk/initiate")
    @Headers("x-api-version: 1")
    suspend fun initiateUser(
        @Header("x-api-key") apiKey: String,
        @Body request: _InitiateUserRequest_,
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>>

    @GET("sdk/initiate")
    suspend fun validateUser():NetworkResponse<APIResponse<_ValidateUserResponse_>>
}