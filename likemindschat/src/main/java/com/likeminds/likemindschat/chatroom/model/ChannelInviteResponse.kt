package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.community.model.Member

data class ChannelInviteResponse(
    val channelInvites: List<ChannelInvite>
)

data class ChannelInvite(
    val chatroom: Chatroom,
    val createdAt: Long,
    val id: Int,
    val inviteStatus: ChannelInviteStatus,
    val updatedAt: Long,
    val inviteReceiver: Member,
    val inviteSender: Member
)