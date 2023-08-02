package com.likeminds.likemindschat.chatroom.model

class MuteChatroomRequest private constructor(
    val chatroomId: String,
    val value: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = MuteChatroomRequest(chatroomId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .value(value)
    }
}