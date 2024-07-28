package com.likeminds.chatinternalsdk.user.api

import com.likeminds.chatinternalsdk.user.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface UserApi {

    // api to logout user
    suspend fun logout(
        request: _LogoutRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    //api to register user's device for notification
    suspend fun registerDevice(request: _RegisterDeviceRequest_): NetworkResponse<APIResponse<Nothing>>

    //api to get the member state
    suspend fun getMemberState(): NetworkResponse<APIResponse<_MemberStateResponse_>>
}