package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _UpdateChannelInviteRequest_ private constructor(
    @SerializedName("channel_id")
    val channelId: String,
    @SerializedName("invite_status")
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

        fun build() = _UpdateChannelInviteRequest_(channelId, inviteStatus)
    }

    fun toBuilder(): Builder {
        return Builder()
            .channelId(channelId)
            .inviteStatus(inviteStatus)
    }
}