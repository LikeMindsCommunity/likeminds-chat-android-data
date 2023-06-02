package com.likeminds.likemindschat.chatroom.model

class FollowChatroomRequest private constructor(
    val chatroomId: String,
    val memberId: String,
    val value: Boolean
) {
    class Builder {
        private var chatroomId: String = ""
        private var memberId: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun memberId(memberId: String) = apply { this.memberId = memberId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = FollowChatroomRequest(chatroomId, memberId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .memberId(memberId)
            .value(value)
    }
}