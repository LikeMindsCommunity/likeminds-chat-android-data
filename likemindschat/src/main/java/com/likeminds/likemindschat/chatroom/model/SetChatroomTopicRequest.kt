package com.likeminds.likemindschat.chatroom.model

class SetChatroomTopicRequest private constructor(
    val chatroomId: Int,
    val conversationId: Int
) {
    class Builder {
        private var chatroomId: Int = 0
        private var conversationId: Int = 0

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun conversationId(conversationId: Int) = apply { this.conversationId = conversationId }

        fun build() = SetChatroomTopicRequest(chatroomId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}