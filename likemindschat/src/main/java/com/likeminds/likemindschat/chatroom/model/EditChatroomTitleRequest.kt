package com.likeminds.likemindschat.chatroom.model

class EditChatroomTitleRequest private constructor(
    val chatroomId: String,
    val text: String
) {
    class Builder {
        private var chatroomId: String = ""
        private var text: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }

        fun build() = EditChatroomTitleRequest(chatroomId, text)
    }
}