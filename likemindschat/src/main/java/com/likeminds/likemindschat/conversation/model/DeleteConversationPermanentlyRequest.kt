package com.likeminds.likemindschat.conversation.model

class DeleteConversationPermanentlyRequest private constructor(
    val conversationId: String,
    val chatroomId: String
) {
    class Builder {
        private var conversationId: String = ""
        private var chatroomId: String = ""

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun chatroomId(chatroomId: String) =
            apply { this.chatroomId = chatroomId }

        fun build() = DeleteConversationPermanentlyRequest(conversationId, chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId).chatroomId(chatroomId)
    }
}