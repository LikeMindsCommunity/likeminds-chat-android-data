package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.conversation.util.GetConversationCountType

class GetConversationsCountRequest private constructor(
    val chatroomId: String,
    val type: GetConversationCountType,
    val conversation: Conversation
) {
    class Builder {
        private var chatroomId: String = ""
        private var type: GetConversationCountType = GetConversationCountType.NONE
        private var conversation: Conversation = Conversation.Builder().build()

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun type(type: GetConversationCountType) = apply { this.type = type }
        fun conversation(conversation: Conversation) = apply { this.conversation = conversation }

        fun build() = GetConversationsCountRequest(
            chatroomId,
            type,
            conversation
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .type(type)
            .conversation(conversation)
    }
}