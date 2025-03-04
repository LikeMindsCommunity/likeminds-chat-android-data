package com.likeminds.chatinternalsdk.refreshtoken

import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenRequest_
import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class RefreshTokenReceiver @Inject constructor(private val refreshTokenNetworkApi: RefreshTokenNetworkApi) {

    suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>> {
        return refreshTokenNetworkApi.refreshAccessToken(refreshToken)
    }
}