package com.likeminds.likemindschat.helper.model

class DecodeUrlRequest private constructor(
    val url: String
) {
    class Builder {
        private var url: String = ""

        fun url(url: String) = apply { this.url = url }

        fun build() = DecodeUrlRequest(url)
    }

    fun toBuilder(): Builder {
        return Builder().url(url)
    }
}