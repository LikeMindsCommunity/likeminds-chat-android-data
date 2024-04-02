package com.likeminds.likemindschat.dm.model

class UserDMLimit private constructor(
    val numberInDuration: Int?,
    val duration: String?
) {
    class Builder {
        private var numberInDuration: Int? = null
        private var duration: String? = null

        fun numberInDuration(numberInDuration: Int?) =
            apply { this.numberInDuration = numberInDuration }

        fun duration(duration: String?) = apply { this.duration = duration }

        fun build() = UserDMLimit(numberInDuration, duration)
    }

    fun toBuilder(): Builder {
        return Builder().numberInDuration(numberInDuration)
            .duration(duration)
    }
}