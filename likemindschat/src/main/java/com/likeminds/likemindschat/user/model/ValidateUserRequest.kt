package com.likeminds.likemindsfeed.user.model

class ValidateUserRequest private constructor(
    val accessToken: String,
    val refreshToken: String,
    val deviceId: String?
) {

    class Builder {
        private var accessToken: String = ""
        private var refreshToken: String = ""
        private var deviceId: String? = null

        fun accessToken(accessToken: String) = apply {
            this.accessToken = accessToken
        }

        fun refreshToken(refreshToken: String) = apply {
            this.refreshToken = refreshToken
        }

        fun deviceId(deviceId: String?) = apply {
            this.deviceId = deviceId
        }

        fun build() = ValidateUserRequest(
            accessToken,
            refreshToken,
            deviceId
        )
    }

    fun toBuilder(): Builder {
        return Builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .deviceId(deviceId)
    }
}