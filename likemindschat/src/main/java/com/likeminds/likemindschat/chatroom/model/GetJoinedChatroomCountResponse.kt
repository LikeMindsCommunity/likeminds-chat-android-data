package com.likeminds.likemindschat.chatroom.model

data class GetJoinedChatroomCountResponse(
    val joinedGroupChatrooms: Int,
    val joinedDMChatrooms: Int
)