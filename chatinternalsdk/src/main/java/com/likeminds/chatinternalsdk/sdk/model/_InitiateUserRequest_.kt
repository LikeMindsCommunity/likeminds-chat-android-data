package com.likeminds.chatinternalsdk.sdk.model

import com.google.gson.annotations.SerializedName

class _InitiateUserRequest_ private constructor(
    @SerializedName("api_key")
    val apiKey: String?,
    @SerializedName("user_name")
    val userName: String?,
    @SerializedName("user_unique_id")
    val userId: String?,
    @SerializedName("is_guest")
    val isGuest: Boolean?,
    @SerializedName("token_expiry_beta")
    val tokenExpiryBeta: Int?,
    @SerializedName("rtm_token_expiry_beta")
    val rtmTokenExpiryBeta: Int?,
    @SerializedName("device_id")
    val deviceId: String?
) {
    class Builder {
        private var apiKey: String? = null
        private var userName: String? = null
        private var userId: String? = null
        private var isGuest: Boolean? = null
        private var tokenExpiryBeta: Int? = null
        private var rtmTokenExpiryBeta: Int? = null
        private var deviceId: String? = null

        fun apiKey(apiKey: String?) = apply { this.apiKey = apiKey }
        fun userName(userName: String?) = apply { this.userName = userName }
        fun userId(userId: String?) = apply { this.userId = userId }
        fun isGuest(isGuest: Boolean?) = apply { this.isGuest = isGuest }
        fun tokenExpiryBeta(tokenExpiryBeta: Int?) =
            apply { this.tokenExpiryBeta = tokenExpiryBeta }

        fun rtmTokenExpiryBeta(rtmTokenExpiryBeta: Int?) =
            apply { this.rtmTokenExpiryBeta = rtmTokenExpiryBeta }

        fun deviceId(deviceId: String?) = apply { this.deviceId = deviceId }

        fun build() = _InitiateUserRequest_(
            apiKey,
            userName,
            userId,
            isGuest,
            tokenExpiryBeta,
            rtmTokenExpiryBeta,
            deviceId
        )
    }

    fun toBuilder(): Builder {
        return Builder().isGuest(isGuest)
            .apiKey(apiKey)
            .userId(userId)
            .userName(userName)
            .tokenExpiryBeta(tokenExpiryBeta)
            .rtmTokenExpiryBeta(rtmTokenExpiryBeta)
            .deviceId(deviceId)
    }
}