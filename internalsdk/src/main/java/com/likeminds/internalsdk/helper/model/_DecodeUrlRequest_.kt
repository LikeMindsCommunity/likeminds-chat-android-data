package com.likeminds.internalsdk.helper.model

import com.google.gson.annotations.SerializedName

class _DecodeUrlRequest_ private constructor(
    @SerializedName("url")
    val url: String
) {
    class Builder {
        private var url: String = ""

        fun url(url: String) = apply { this.url = url }

        fun build() = _DecodeUrlRequest_(url)
    }

    fun toBuilder(): Builder {
        return Builder().url(url)
    }
}