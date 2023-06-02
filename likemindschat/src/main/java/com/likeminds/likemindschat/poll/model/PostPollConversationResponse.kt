package com.likeminds.likemindschat.poll.model

import com.likeminds.likemindschat.conversation.model.Conversation

data class PostPollConversationResponse(
    val id: String,
    val conversation: Conversation
)