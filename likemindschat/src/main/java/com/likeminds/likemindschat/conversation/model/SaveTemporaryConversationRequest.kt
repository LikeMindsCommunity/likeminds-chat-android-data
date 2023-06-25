package com.likeminds.likemindschat.conversation.model

class SaveTemporaryConversationRequest private constructor(
    val conversation: Conversation
) {

    class Builder {

        private var conversation: Conversation = Conversation.Builder().build()

        fun conversation(conversation: Conversation) = apply { this.conversation = conversation }

        fun build() = SaveTemporaryConversationRequest(conversation)
    }
}