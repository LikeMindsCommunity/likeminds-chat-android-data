package com.likeminds.chatinternalsdk.refreshtoken

import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenRequest_
import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface RefreshTokenNetworkApi {

    @POST("user/refresh")
    suspend fun refreshAccessToken(
        @Header("Authorization") refreshToken: String,
        @Body request: _RefreshTokenRequest_
    ): NetworkResponse<APIResponse<_RefreshTokenResponse_>>
}