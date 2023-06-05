package com.likeminds.likemindschat.conversation.model

class PutReactionRequest private constructor(
    val conversationId: String?,
    val chatroomId: String?,
    val reaction: String
) {
    class Builder {
        private var conversationId: String? = null
        private var chatroomId: String? = null
        private var reaction: String = ""

        fun conversationId(conversationId: String?) = apply { this.conversationId = conversationId }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun reaction(reaction: String) = apply { this.reaction = reaction }

        fun build() = PutReactionRequest(
            conversationId,
            chatroomId,
            reaction
        )
    }

    fun toBuilder(): Builder {
        return Builder().reaction(reaction)
            .chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}