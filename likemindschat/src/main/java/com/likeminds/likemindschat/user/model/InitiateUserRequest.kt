package com.likeminds.likemindschat.user.model

class InitiateUserRequest private constructor(
    val apiKey: String,
    val deviceId: String,
    val userName: String,
    val userId: String?,
    val isGuest: Boolean?
) {
    class Builder {
        private var apiKey: String = ""
        private var deviceId: String = ""
        private var userName: String = ""
        private var userId: String? = null
        private var isGuest: Boolean? = null

        fun apiKey(apiKey: String) = apply { this.apiKey = apiKey }
        fun deviceId(deviceId: String) = apply { this.deviceId = deviceId }
        fun userName(userName: String) = apply { this.userName = userName }
        fun userId(userId: String?) = apply { this.userId = userId }
        fun isGuest(isGuest: Boolean?) = apply { this.isGuest = isGuest }

        fun build() = InitiateUserRequest(
            apiKey,
            deviceId,
            userName,
            userId,
            isGuest
        )
    }

    fun toBuilder(): Builder {
        return Builder().userId(userId)
            .apiKey(apiKey)
            .deviceId(deviceId)
            .isGuest(isGuest)
            .userName(userName)
    }
}