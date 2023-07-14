package com.likeminds.likemindschat.chatroom.model

data class GetChatroomActionsResponse(
    val canAccessSecretChatroom: Boolean,
    val chatroomActions: List<ChatroomAction>,
    val participantCount: Int,
    val placeHolder: String?
)

data class ChatroomAction(
    val id: Int,
    val title: String,
    val route: String? = null
)