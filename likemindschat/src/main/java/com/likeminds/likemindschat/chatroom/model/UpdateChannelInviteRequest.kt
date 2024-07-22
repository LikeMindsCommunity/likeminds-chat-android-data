package com.likeminds.likemindschat.chatroom.model

class UpdateChannelInviteRequest private constructor(
    val channelId: String,
    val inviteStatus: ChannelInviteStatus
) {
    class Builder {
        private var channelId: String = ""
        private var inviteStatus: ChannelInviteStatus = ChannelInviteStatus.INVITED

        fun channelId(channelId: String) = apply {
            this.channelId = channelId
        }

        fun inviteStatus(inviteStatus: ChannelInviteStatus) = apply {
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