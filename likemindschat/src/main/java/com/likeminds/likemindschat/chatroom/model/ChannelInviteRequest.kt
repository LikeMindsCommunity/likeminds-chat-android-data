package com.likeminds.likemindschat.chatroom.model

class ChannelInviteRequest private constructor(
    val channelType: Int,
    val page: Int,
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

        fun build() = ChannelInviteRequest(
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