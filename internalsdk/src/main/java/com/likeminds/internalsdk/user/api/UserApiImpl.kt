package com.likeminds.internalsdk.user.api

import com.likeminds.internalsdk.user.UserReceiver
import com.likeminds.internalsdk.user.model._LogoutRequest_
import com.likeminds.internalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class UserApiImpl @Inject constructor(private val userReceiver: UserReceiver) : UserApi {

    override suspend fun logout(request: _LogoutRequest_): NetworkResponse<APIResponse<Nothing>> {
        return userReceiver.logout(request)
    }

    override suspend fun registerDevice(
        request: _RegisterDeviceRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return userReceiver.registerDevice(request)
    }

}