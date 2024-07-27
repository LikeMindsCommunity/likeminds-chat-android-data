package com.likeminds.chatinternalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _AttachmentMeta_ private constructor(
    @SerializedName("number_of_page")
    val numberOfPage: Int?,
    @SerializedName("size")
    val size: Long?,
    @SerializedName("duration")
    val duration: Int? //in seconds
) {
    class Builder {
        private var numberOfPage: Int? = null
        private var size: Long? = null
        private var duration: Int? = null

        fun numberOfPage(numberOfPage: Int?) = apply { this.numberOfPage = numberOfPage }
        fun size(size: Long?) = apply { this.size = size }
        fun duration(duration: Int?) = apply { this.duration = duration }

        fun build() = _AttachmentMeta_(numberOfPage, size, duration)
    }

    fun toBuilder(): Builder {
        return Builder().duration(duration)
            .numberOfPage(numberOfPage)
            .size(size)
    }
}