package com.likeminds.internalsdk.user.model

import com.google.gson.annotations.SerializedName

class _RegisterDeviceRequest_ private constructor(
    @SerializedName("token")
    val token: String,
    @SerializedName("device_id")
    val deviceId: String?
) {
    class Builder {
        private var token: String = ""
        private var deviceId: String? = null

        fun token(token: String) = apply { this.token = token }
        fun deviceId(deviceId: String?) = apply { this.deviceId = deviceId }

        fun build() = _RegisterDeviceRequest_(token, deviceId)
    }

    fun toBuilder(): Builder {
        return Builder().deviceId(deviceId)
            .token(token)
    }
}