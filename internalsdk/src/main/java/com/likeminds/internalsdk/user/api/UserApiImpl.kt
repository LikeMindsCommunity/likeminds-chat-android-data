package com.likeminds.internalsdk.user.api

import com.likeminds.internalsdk.user.UserReceiver
import com.likeminds.internalsdk.user.model.*
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

    override suspend fun getUserMeta(): NetworkResponse<APIResponse<_UserMetaResponse_>> {
        return userReceiver.getUserMeta()
    }

}