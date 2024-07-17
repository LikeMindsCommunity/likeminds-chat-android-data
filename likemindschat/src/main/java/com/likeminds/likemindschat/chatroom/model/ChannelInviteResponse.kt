package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.user.model.User

data class ChannelInviteResponse(
    val userInvites: List<UserInvite>
)

data class UserInvite(
    val chatroom: Chatroom,
    val createdAt: Long,
    val id: Int,
    val inviteStatus: Int,
    val updatedAt: Long,
    val inviteReceiver: User,
    val inviteSender: User
)