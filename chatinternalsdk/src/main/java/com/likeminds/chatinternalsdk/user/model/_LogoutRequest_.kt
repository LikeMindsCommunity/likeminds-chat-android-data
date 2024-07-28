package com.likeminds.chatinternalsdk.user.model

import com.google.gson.annotations.SerializedName

class _LogoutRequest_ private constructor(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("device_id")
    val deviceId: String?
) {

    class Builder {

        private var refreshToken: String = ""
        private var deviceId: String? = null

        fun refreshToken(refreshToken: String) = apply { this.refreshToken = refreshToken }
        fun deviceId(deviceId: String?) = apply { this.deviceId = deviceId }

        fun build() = _LogoutRequest_(refreshToken, deviceId)
    }

    fun toBuilder(): Builder {
        return Builder().refreshToken(refreshToken)
            .deviceId(deviceId)
    }
}