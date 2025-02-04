package com.likeminds.likemindschat.chatroom.model

data class GetUnreadConversationsCountResponse(
    val unreadGroupChatroomConversations: Int,
    val unreadDMChatroomConversations: Int
)