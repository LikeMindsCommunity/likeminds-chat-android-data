package com.likeminds.internalsdk.refreshtoken

import com.likeminds.internalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface RefreshTokenApi {
    suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>>
}