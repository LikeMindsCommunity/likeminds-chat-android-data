package com.likeminds.likemindschat.dm.model

import com.likeminds.likemindschat.chatroom.model.ChatRequestState

class SendDMRequest private constructor(
    val chatroomId: String,
    val chatRequestState: ChatRequestState,
    val text: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var chatRequestState: ChatRequestState = ChatRequestState.NOTHING
        private var text: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun chatRequestState(chatRequestState: ChatRequestState) =
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