package com.likeminds.likemindschat.chatroom.model

class MuteChatroomRequest private constructor(
    val chatroomId: Int,
    val value: Boolean
) {
    class Builder {
        private var chatroomId: Int = -1
        private var value: Boolean = false

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = MuteChatroomRequest(chatroomId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .value(value)
    }
}