package com.likeminds.internalsdk.sdk.model

import com.google.gson.annotations.SerializedName

class _ValidateUserRequest_ private constructor(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
) {

    class Builder {
        private var accessToken: String = ""
        private var refreshToken: String = ""

        fun accessToken(accessToken: String) = apply {
            this.accessToken = accessToken
        }

        fun refreshToken(refreshToken: String) = apply {
            this.refreshToken = refreshToken
        }

        fun build() = _ValidateUserRequest_(
            accessToken,
            refreshToken
        )
    }

    fun toBuilder(): Builder {
        return Builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
    }
}