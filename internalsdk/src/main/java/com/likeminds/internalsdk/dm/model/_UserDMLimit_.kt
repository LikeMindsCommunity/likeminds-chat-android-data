package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

class _UserDMLimit_ private constructor(
    @SerializedName("number_in_duration")
    val numberInDuration: Int?,
    @SerializedName("duration")
    val duration: String?
) {
    class Builder {
        private var numberInDuration: Int? = null
        private var duration: String? = null

        fun numberInDuration(numberInDuration: Int?) =
            apply { this.numberInDuration = numberInDuration }

        fun duration(duration: String?) = apply { this.duration = duration }

        fun build() = _UserDMLimit_(numberInDuration, duration)
    }

    fun toBuilder(): Builder {
        return Builder().numberInDuration(numberInDuration)
            .duration(duration)
    }
}