package com.likeminds.internalsdk.refreshtoken.model

import com.google.gson.annotations.SerializedName

data class _RefreshTokenResponse_(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
)
