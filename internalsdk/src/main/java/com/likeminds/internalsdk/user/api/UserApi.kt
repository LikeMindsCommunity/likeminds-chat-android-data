package com.likeminds.internalsdk.user.api

import com.likeminds.internalsdk.user.model._LogoutRequest_
import com.likeminds.internalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface UserApi {

    // api to logout user
    suspend fun logout(
        request: _LogoutRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    //api to register user's device for notification
    suspend fun registerDevice(request: _RegisterDeviceRequest_): NetworkResponse<APIResponse<Nothing>>
}