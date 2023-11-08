package com.likeminds.likemindschat.dm.model

import com.likeminds.likemindschat.conversation.model.Conversation

data class SendDMResponse(
    val conversation: Conversation
)