package com.likeminds.chatinternalsdk.user.api

import com.likeminds.chatinternalsdk.user.UserReceiver
import com.likeminds.chatinternalsdk.user.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
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

    override suspend fun getMemberState(): NetworkResponse<APIResponse<_MemberStateResponse_>> {
        return userReceiver.getMemberState()
    }

    override suspend fun editUserProfile(request: _EditUserProfileRequest_): NetworkResponse<APIResponse<Nothing>> {
        return userReceiver.editUserProfile(request)
    }
}