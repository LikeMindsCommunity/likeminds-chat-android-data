package com.likeminds.likemindschat.conversation.model

class SaveConversationRequest private constructor(
    val conversation: Conversation
) {

    class Builder {

        private var conversation: Conversation = Conversation.Builder().build()

        fun conversation(conversation: Conversation) = apply { this.conversation = conversation }

        fun build() = SaveConversationRequest(conversation)
    }
}