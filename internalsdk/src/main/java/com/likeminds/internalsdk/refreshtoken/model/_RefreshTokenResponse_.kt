package com.likeminds.internalsdk.refreshtoken.model

import com.google.gson.annotations.SerializedName

data class _RefreshTokenResponse_(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("refresh_token")
    var refreshToken: String,
)
