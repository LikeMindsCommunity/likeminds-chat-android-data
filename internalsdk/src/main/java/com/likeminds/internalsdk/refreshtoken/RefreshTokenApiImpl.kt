package com.likeminds.internalsdk.refreshtoken

import com.likeminds.internalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class RefreshTokenApiImpl @Inject constructor(private val refreshTokenReceiver: RefreshTokenReceiver) :
    RefreshTokenApi {

    override suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>> {
        return refreshTokenReceiver.refreshAccessToken(refreshToken)
    }
}