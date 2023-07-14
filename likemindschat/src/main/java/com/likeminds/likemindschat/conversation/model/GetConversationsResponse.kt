package com.likeminds.likemindschat.conversation.model

data class GetConversationsResponse(
    val conversations: List<Conversation>?,
    val count: Int = 0
)
