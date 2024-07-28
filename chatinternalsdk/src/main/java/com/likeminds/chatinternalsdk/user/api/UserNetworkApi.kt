package com.likeminds.chatinternalsdk.user.api

import com.likeminds.chatinternalsdk.user.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface UserNetworkApi {

    @POST("user/logout")
    suspend fun logout(
        @Header("x-device-id") deviceId: String,
        @Body request: _LogoutRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @POST("user/device/push")
    suspend fun registerDevice(
        @Header("x-device-id") deviceId: String,
        @Body request: _RegisterDeviceRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @GET("community/member/state")
    suspend fun getMemberState(): NetworkResponse<APIResponse<_MemberStateResponse_>>
}