package com.likeminds.internalsdk.sdk.model

import com.google.gson.annotations.SerializedName

class _InitiateUserRequest_ private constructor(
    @SerializedName("api_key")
    val apiKey: String?,
    @SerializedName("user_name")
    val userName: String?,
    @SerializedName("user_unique_id")
    val userId: String?,
    @SerializedName("is_guest")
    val isGuest: Boolean?
) {
    class Builder {
        private var apiKey: String? = null
        private var userName: String? = null
        private var userId: String? = null
        private var isGuest: Boolean? = null

        fun apiKey(apiKey: String?) = apply { this.apiKey = apiKey }
        fun userName(userName: String?) = apply { this.userName = userName }
        fun userId(userId: String?) = apply { this.userId = userId }
        fun isGuest(isGuest: Boolean?) = apply { this.isGuest = isGuest }

        fun build() = _InitiateUserRequest_(
            apiKey,
            userName,
            userId,
            isGuest
        )
    }

    fun toBuilder(): Builder {
        return Builder().isGuest(isGuest)
            .apiKey(apiKey)
            .userId(userId)
            .userName(userName)
    }
}