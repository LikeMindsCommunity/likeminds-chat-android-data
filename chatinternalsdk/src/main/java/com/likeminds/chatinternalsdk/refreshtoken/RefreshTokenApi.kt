package com.likeminds.chatinternalsdk.refreshtoken

import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface RefreshTokenApi {
    suspend fun refreshAccessToken(refreshToken: String): NetworkResponse<APIResponse<_RefreshTokenResponse_>>
}