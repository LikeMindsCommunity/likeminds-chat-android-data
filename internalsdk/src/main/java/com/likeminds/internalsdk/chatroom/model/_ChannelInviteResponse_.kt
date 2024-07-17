package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.user.model._User_

data class _ChannelInviteResponse_(
    @SerializedName("user_invites")
    val userInvites: List<_UserInvite_>
)

data class _UserInvite_(
    @SerializedName("chatroom")
    val chatroom: _Chatroom_,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("invite_status")
    val inviteStatus: Int,
    @SerializedName("updated_at")
    val updatedAt: Long,
    @SerializedName("invite_receiver")
    val inviteReceiver: _User_,
    @SerializedName("invite_sender")
    val inviteSender: _User_
)