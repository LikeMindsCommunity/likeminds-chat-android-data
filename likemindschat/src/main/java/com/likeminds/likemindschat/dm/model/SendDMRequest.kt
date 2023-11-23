package com.likeminds.likemindschat.dm.model

class SendDMRequest private constructor(
    val chatroomId: String,
    val chatRequestState: Int,
    val text: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var chatRequestState: Int = 0
        private var text: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun chatRequestState(chatRequestState: Int) =
            apply { this.chatRequestState = chatRequestState }

        fun text(text: String?) = apply { this.text = text }

        fun build() = SendDMRequest(chatroomId, chatRequestState, text)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .chatRequestState(chatRequestState)
            .text(text)
    }
}