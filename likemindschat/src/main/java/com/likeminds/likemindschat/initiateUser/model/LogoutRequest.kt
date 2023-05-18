package com.likeminds.likemindschat.initiateUser.model

class LogoutRequest private constructor(
    var refreshToken: String,
    var deviceId: String
) {

    class Builder {
        private var refreshToken: String = ""
        private var deviceId: String = ""

        fun refreshToken(refreshToken: String) = apply { this.refreshToken = refreshToken }
        fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

        fun build() = LogoutRequest(refreshToken, deviceId)
    }

    fun toBuilder(): Builder {
        return Builder().refreshToken(refreshToken)
            .deviceId(deviceId)
    }
}