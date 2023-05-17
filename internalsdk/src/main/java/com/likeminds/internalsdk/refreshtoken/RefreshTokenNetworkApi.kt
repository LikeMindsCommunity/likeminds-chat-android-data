package com.likeminds.internalsdk.refreshtoken

import com.likeminds.internalsdk.refreshtoken.model._RefreshTokenResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshTokenNetworkApi {

    @POST("user/refresh")
    suspend fun refreshAccessToken(
        @Header("Authorization") refreshToken: String,
    ): NetworkResponse<APIResponse<_RefreshTokenResponse_>>
}