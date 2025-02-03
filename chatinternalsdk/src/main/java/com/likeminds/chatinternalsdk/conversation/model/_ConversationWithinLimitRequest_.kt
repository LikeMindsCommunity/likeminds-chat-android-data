package com.likeminds.chatinternalsdk.conversation.model

class _ConversationWithinLimitRequest_ private constructor(
    val chatroomId: String,
    val conversationKey: _Conversation_,
    val targetConversationId: String,
    val limit: Int
) {
    class Builder {
        private var chatroomId: String = ""
        private var conversationKey: _Conversation_ = _Conversation_.Builder().build()
        private var targetConversationId: String = ""
        private var limit: Int = 100

        fun chatroomId(chatroomId: String) = apply {
            this.chatroomId = chatroomId
        }

        fun conversationKey(conversationKey: _Conversation_) = apply {
            this.conversationKey = conversationKey
        }

        fun targetConversationId(targetConversationId: String) = apply {
            this.targetConversationId = targetConversationId
        }

        fun limit(limit: Int) = apply {
            this.limit = limit
        }

        fun build() = _ConversationWithinLimitRequest_(
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