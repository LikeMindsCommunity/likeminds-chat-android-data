package com.likeminds.internalsdk.user.api

import com.likeminds.internalsdk.user.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface UserApi {

    // api to logout user
    suspend fun logout(
        request: _LogoutRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    //api to register user
    suspend fun registerDevice(request: _RegisterDeviceRequest_): NetworkResponse<APIResponse<Nothing>>

    //api to get user meta
    suspend fun getUserMeta(): NetworkResponse<APIResponse<_UserMetaResponse_>>
}