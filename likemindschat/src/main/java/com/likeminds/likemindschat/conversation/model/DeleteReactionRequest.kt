package com.likeminds.likemindschat.conversation.model

class DeleteReactionRequest private constructor(
    val conversationId: String?,
    val chatroomId: String?
) {
    class Builder {
        private var conversationId: String? = null
        private var chatroomId: String? = null

        fun conversationId(conversationId: String?) = apply { this.conversationId = conversationId }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }

        fun build() = DeleteReactionRequest(conversationId, chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId).conversationId(conversationId)
    }
}