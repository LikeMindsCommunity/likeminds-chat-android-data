package com.likeminds.chatinternalsdk.refreshtoken.model

import com.google.gson.annotations.SerializedName

class _RefreshTokenRequest_ private constructor(
    @SerializedName("token_expiry_beta")
    val tokenExpiryBeta: Int,
) {
    class Builder {
        private var tokenExpiryBeta: Int = 0

        fun tokenExpiryBeta(tokenExpiryBeta: Int) = apply { this.tokenExpiryBeta = tokenExpiryBeta }

        fun build() = _RefreshTokenRequest_(
            tokenExpiryBeta
        )
    }

    fun toBuilder(): Builder {
        return Builder()
            .tokenExpiryBeta(tokenExpiryBeta)
    }
}