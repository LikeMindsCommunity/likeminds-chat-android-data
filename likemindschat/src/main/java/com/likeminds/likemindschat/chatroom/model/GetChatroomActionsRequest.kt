package com.likeminds.likemindschat.chatroom.model

class GetChatroomActionsRequest private constructor(
    val chatroomId: String
) {

    class Builder {

        private var chatroomId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }

        fun build() = GetChatroomActionsRequest(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}