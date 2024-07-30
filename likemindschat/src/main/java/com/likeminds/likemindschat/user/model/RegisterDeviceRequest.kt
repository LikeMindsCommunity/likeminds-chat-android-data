package com.likeminds.likemindschat.user.model

class RegisterDeviceRequest private constructor(
    val token: String,
    val deviceId: String
) {
    class Builder {
        private var token: String = ""
        private var deviceId: String = ""

        fun token(token: String) = apply { this.token = token }
        fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

        fun build() = RegisterDeviceRequest(token, deviceId)
    }
}