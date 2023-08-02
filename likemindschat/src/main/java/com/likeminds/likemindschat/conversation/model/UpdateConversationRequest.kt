package com.likeminds.likemindschat.conversation.model

class UpdateConversationRequest private constructor(
    val conversation: Conversation
) {
    class Builder {
        private var conversation = Conversation.Builder().build()

        fun conversation(conversation: Conversation) = apply { this.conversation = conversation }

        fun build() = UpdateConversationRequest(conversation)
    }

    fun toBuilder(): Builder {
        return Builder().conversation(conversation)
    }
}