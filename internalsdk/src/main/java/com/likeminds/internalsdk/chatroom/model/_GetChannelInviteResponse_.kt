package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

data class _GetChannelInviteResponse_(
    @SerializedName("user_invites")
    val channelInvites: List<_ChannelInvite_>
)

data class _ChannelInvite_(
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
    @SerializedName("invite_sender")
    val inviteSender: _Member_,
    @SerializedName("invite_receiver")
    val inviteReceiver: _Member_
)