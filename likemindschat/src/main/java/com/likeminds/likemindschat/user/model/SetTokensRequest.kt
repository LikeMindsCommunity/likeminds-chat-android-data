package com.likeminds.likemindschat.user.model

class SetTokensRequest private constructor(
    val accessToken: String,
    val refreshToken: String
) {

    class Builder {
        private var accessToken: String = ""
        private var refreshToken: String = ""

        fun accessToken(accessToken: String) = apply { this.accessToken = accessToken }
        fun refreshToken(refreshToken: String) = apply { this.refreshToken = refreshToken }

        fun build() = SetTokensRequest(accessToken, refreshToken)
    }

    fun toBuilder(): Builder {
        return Builder().accessToken(accessToken)
            .refreshToken(refreshToken)
    }
}