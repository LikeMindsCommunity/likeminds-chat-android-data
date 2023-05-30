package com.likeminds.likemindschat.chatroom.model

class MarkReadChatroomRequest private constructor(
    val chatroomId: Int
) {
    class Builder {
        private var chatroomId: Int = -1

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }

        fun build() = MarkReadChatroomRequest(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}