package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

data class _GetChatroomResponse_(
    @SerializedName("can_access_secret_chatroom")
    val canAccessSecretChatroom: Boolean,
    @SerializedName("chatroom_actions")
    val chatroomActions: List<_ChatroomAction_>,
    @SerializedName("participant_count")
    val participantCount: Int,
    @SerializedName("placeholder")
    val placeHolder: String?
)

data class _ChatroomAction_(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("route")
    val route: String? = null
)