package com.likeminds.chatinternalsdk.refreshtoken

import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenRequest_
import com.likeminds.chatinternalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshTokenNetworkApi {

    @POST("user/refresh")
    suspend fun refreshAccessToken(
        @Header("Authorization") refreshToken: String,
        @Body request_: _RefreshTokenRequest_? = null
    ): NetworkResponse<APIResponse<_RefreshTokenResponse_>>
}