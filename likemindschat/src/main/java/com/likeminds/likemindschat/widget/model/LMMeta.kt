package com.likeminds.likemindschat.widget.model

import com.likeminds.likemindschat.conversation.model.Conversation

class LMMeta private constructor(
    val sourceChatroomId: String?,
    val sourceChatroomName: String?,
    val sourceConversation: Conversation?,
    val type: String?
) {
    class Builder {
        private var sourceChatroomId: String? = null
        private var sourceChatroomName: String? = null
        private var sourceConversation: Conversation? = null
        private var type: String? = null

        fun sourceChatroomId(sourceChatroomId: String?) = apply {
            this.sourceChatroomId = sourceChatroomId
        }

        fun sourceChatroomName(sourceChatroomName: String?) = apply {
            this.sourceChatroomName = sourceChatroomName
        }

        fun sourceConversation(sourceConversation: Conversation?) = apply {
            this.sourceConversation = sourceConversation
        }

        fun type(type: String?) = apply {
            this.type = type
        }

        fun build() = LMMeta(
            sourceChatroomId,
            sourceChatroomName,
            sourceConversation,
            type
        )
    }

    fun toBuilder(): Builder {
        return Builder().sourceChatroomId(sourceChatroomId)
            .sourceChatroomName(sourceChatroomName)
            .sourceConversation(sourceConversation)
            .type(type)
    }
}