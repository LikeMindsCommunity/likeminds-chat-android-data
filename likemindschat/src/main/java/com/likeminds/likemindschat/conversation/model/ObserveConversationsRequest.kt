package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.conversation.util.ConversationChangeListener

class ObserveConversationsRequest private constructor(
    val chatroomId: String,
    val listener: ConversationChangeListener
) {

    class Builder {

        private var chatroomId: String = ""
        private lateinit var listener: ConversationChangeListener

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun listener(listener: ConversationChangeListener) = apply { this.listener = listener }

        fun build() = ObserveConversationsRequest(chatroomId, listener)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .listener(listener)
    }
}