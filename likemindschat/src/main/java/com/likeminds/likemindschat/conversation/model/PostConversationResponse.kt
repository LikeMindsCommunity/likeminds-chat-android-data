package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.widget.model.Widget

data class PostConversationResponse(
    val conversation: Conversation,
    val id: String?,
    val widgets: Map<String, Widget>
)