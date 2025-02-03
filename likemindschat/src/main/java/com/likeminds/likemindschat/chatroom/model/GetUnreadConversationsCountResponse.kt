package com.likeminds.likemindschat.chatroom.model

data class GetUnreadConversationsCountResponse(
    val unreadGroupChatroomConversations: Long,
    val unreadDMChatroomConversations: Long
)