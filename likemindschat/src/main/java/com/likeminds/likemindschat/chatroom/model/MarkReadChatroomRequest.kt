package com.likeminds.likemindschat.chatroom.model

class MarkReadChatroomRequest private constructor(
    val chatroomId: String
) {
    class Builder {
        private var chatroomId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }

        fun build() = MarkReadChatroomRequest(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}