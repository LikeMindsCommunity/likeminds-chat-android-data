package com.likeminds.likemindschat.conversation.model

class GetConversationRequest private constructor(
    val conversationId: String
) {

    class Builder {

        private var conversationId: String = ""

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }

        fun build() = GetConversationRequest(conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
    }
}