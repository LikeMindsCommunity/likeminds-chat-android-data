package com.likeminds.likemindschat.chatroom.model

class SetChatroomTopicRequest private constructor(
    val chatroomId: String,
    val conversationId: String
) {
    class Builder {

        private var chatroomId: String = ""
        private var conversationId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }

        fun build() = SetChatroomTopicRequest(chatroomId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}