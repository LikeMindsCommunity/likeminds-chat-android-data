package com.likeminds.chatinternalsdk.user.model

class _EditUserProfileRequest_ private constructor(
    val name: String?,
    val imageUrl: String?
) {
    class Builder {
        private var name: String? = null
        private var imageUrl: String? = null

        fun name(name: String?) = apply {
            this.name = name
        }

        fun imageUrl(imageUrl: String?) = apply {
            this.imageUrl = imageUrl
        }

        fun build() = _EditUserProfileRequest_(name, imageUrl)
    }

    fun toBuilder(): Builder {
        return Builder().name(name)
            .imageUrl(imageUrl)
    }
}