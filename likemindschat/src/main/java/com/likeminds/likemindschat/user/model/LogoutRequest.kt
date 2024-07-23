package com.likeminds.likemindschat.user.model

class LogoutRequest private constructor(
    val deviceId: String
) {

    class Builder {
        private var deviceId: String = ""

        fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

        fun build() = LogoutRequest(deviceId)
    }

    fun toBuilder(): Builder {
        return Builder().deviceId(deviceId)
    }
}