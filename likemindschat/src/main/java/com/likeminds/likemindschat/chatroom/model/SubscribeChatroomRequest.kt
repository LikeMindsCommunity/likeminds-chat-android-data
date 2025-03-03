package com.likeminds.likemindschat.chatroom.model

class SubscribeChatroomRequest private constructor(
    val chatroomId: String,
) {
    class Builder {
        private var chatroomId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }

        fun build() = SubscribeChatroomRequest(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}