package com.likeminds.likemindschat.initiateUser.model

class RegisterDeviceRequest private constructor(
    var token: String,
    var deviceId: String
) {
    class Builder {
        private var token: String = ""
        private var deviceId: String = ""

        fun token(token: String) = apply { this.token = token }
        fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }

        fun build() = RegisterDeviceRequest(token, deviceId)
    }
}