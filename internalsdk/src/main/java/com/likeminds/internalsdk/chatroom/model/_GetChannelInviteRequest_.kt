package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _GetChannelInviteRequest_ private constructor(
    @SerializedName("channel_type")
    val channelType: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int
) {
    class Builder {
        private var channelType: Int = 0
        private var page: Int = 1
        private var pageSize: Int = 10

        fun channelType(channelType: Int) = apply {
            this.channelType = channelType
        }

        fun page(page: Int) = apply {
            this.page = page
        }

        fun pageSize(pageSize: Int) = apply {
            this.pageSize = pageSize
        }

        fun build() = _GetChannelInviteRequest_(
            channelType,
            page,
            pageSize
        )
    }

    fun toBuilder(): Builder {
        return Builder()
            .channelType(channelType)
            .page(page)
            .pageSize(pageSize)
    }
}