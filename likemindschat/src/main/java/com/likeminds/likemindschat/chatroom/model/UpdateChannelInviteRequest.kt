package com.likeminds.likemindschat.chatroom.model

class UpdateChannelInviteRequest private constructor(
    val channelId: String,
    val inviteStatus: Int
) {
    class Builder {
        private var channelId: String = ""
        private var inviteStatus: Int = 0

        fun channelId(channelId: String) = apply {
            this.channelId = channelId
        }

        fun inviteStatus(inviteStatus: Int) = apply {
            this.inviteStatus = inviteStatus
        }

        fun build() = UpdateChannelInviteRequest(channelId, inviteStatus)
    }

    fun toBuilder(): Builder {
        return Builder()
            .channelId(channelId)
            .inviteStatus(inviteStatus)
    }
}