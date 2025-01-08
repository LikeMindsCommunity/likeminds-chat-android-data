package com.likeminds.likemindschat.conversation.model

class ConversationWithinLimitRequest private constructor(
    val chatroomId: String,
    val conversationKey: Conversation,
    val targetConversationId: String,
    val limit: Int
) {
    class Builder {
        private var chatroomId: String = ""
        private var conversationKey: Conversation = Conversation.Builder().build()
        private var targetConversationId: String = ""
        private var limit: Int = 100

        fun chatroomId(chatroomId: String) = apply {
            this.chatroomId = chatroomId
        }

        fun conversationKey(conversationKey: Conversation) = apply {
            this.conversationKey = conversationKey
        }

        fun targetConversationId(targetConversationId: String) = apply {
            this.targetConversationId = targetConversationId
        }

        fun limit(limit: Int) = apply {
            this.limit = limit
        }

        fun build() = ConversationWithinLimitRequest(
            chatroomId,
            conversationKey,
            targetConversationId,
            limit
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .conversationKey(conversationKey)
            .targetConversationId(targetConversationId)
            .limit(limit)
    }
}