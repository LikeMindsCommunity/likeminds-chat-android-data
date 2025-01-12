package com.likeminds.likemindschat.user.model

class EditProfileRequest private constructor(
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

        fun build() = EditProfileRequest(name, imageUrl)
    }

    fun toBuilder(): Builder {
        return Builder().name(name)
            .imageUrl(imageUrl)
    }
}