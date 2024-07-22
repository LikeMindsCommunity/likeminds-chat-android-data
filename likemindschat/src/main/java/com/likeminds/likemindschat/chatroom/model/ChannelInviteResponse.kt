package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.community.model.Member

data class ChannelInviteResponse(
    val userInvites: List<UserInvite>
)

data class UserInvite(
    val chatroom: Chatroom,
    val createdAt: Long,
    val id: Int,
    val inviteStatus: ChannelInviteStatus,
    val updatedAt: Long,
    val inviteReceiver: Member,
    val inviteSender: Member
)