package com.likeminds.internalsdk.user.api

import com.likeminds.internalsdk.user.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
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

    @GET("user/meta")
    suspend fun getUserMeta(): NetworkResponse<APIResponse<_UserMetaResponse_>>
}