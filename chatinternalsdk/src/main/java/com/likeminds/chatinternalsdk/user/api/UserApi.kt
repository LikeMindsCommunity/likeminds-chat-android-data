package com.likeminds.chatinternalsdk.user.api

import com.likeminds.chatinternalsdk.user.model._EditProfileRequest_
import com.likeminds.chatinternalsdk.user.model._LogoutRequest_
import com.likeminds.chatinternalsdk.user.model._MemberStateResponse_
import com.likeminds.chatinternalsdk.user.model._RegisterDeviceRequest_
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

    //api to edit member profile
    suspend fun editProfile(request: _EditProfileRequest_): NetworkResponse<APIResponse<Nothing>>
}