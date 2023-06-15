package com.likeminds.likemindschat.community.model

import com.likeminds.likemindschat.chatroom.model.Chatroom

data class GetExploreFeedResponse(
    val chatrooms: List<Chatroom>,
    val pinnedChatroomCount: Int
)