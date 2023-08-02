package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.conversation.util.GetConversationType

class GetConversationsRequest private constructor(
    val chatroomId: String,
    val type: GetConversationType,
    val conversation: Conversation?,
    val limit: Int
) {

    class Builder {

        private var chatroomId: String = ""
        private var type: GetConversationType = GetConversationType.NONE
        private var conversation: Conversation? = null
        private var limit: Int = 50

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun type(type: GetConversationType) = apply { this.type = type }
        fun conversation(conversation: Conversation?) = apply { this.conversation = conversation }
        fun limit(limit: Int) = apply { this.limit = limit }

        fun build() = GetConversationsRequest(chatroomId, type, conversation, limit)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .type(type)
            .conversation(conversation)
            .limit(limit)
    }
}
