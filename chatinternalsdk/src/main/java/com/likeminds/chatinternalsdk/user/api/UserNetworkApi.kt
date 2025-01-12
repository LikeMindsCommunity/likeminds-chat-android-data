package com.likeminds.chatinternalsdk.user.api

import com.likeminds.chatinternalsdk.user.model._EditProfileRequest_
import com.likeminds.chatinternalsdk.user.model._LogoutRequest_
import com.likeminds.chatinternalsdk.user.model._MemberStateResponse_
import com.likeminds.chatinternalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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

    @PUT("community/member/profile")
    suspend fun editProfile(@Body request: _EditProfileRequest_): NetworkResponse<APIResponse<Nothing>>
}