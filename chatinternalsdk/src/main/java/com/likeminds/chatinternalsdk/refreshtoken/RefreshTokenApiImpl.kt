package com.likeminds.chatinternalsdk.refreshtoken

import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class RefreshTokenApiImpl @Inject constructor(private val refreshTokenReceiver: RefreshTokenReceiver) :
    RefreshTokenApi {

    override suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>> {
        return refreshTokenReceiver.refreshAccessToken(refreshToken)
    }
}