package com.likeminds.internalsdk.refreshtoken

import com.likeminds.internalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class RefreshTokenReceiver @Inject constructor(private val refreshTokenNetworkApi: RefreshTokenNetworkApi) {

    suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>> {
        return refreshTokenNetworkApi.refreshAccessToken(refreshToken)
    }
}