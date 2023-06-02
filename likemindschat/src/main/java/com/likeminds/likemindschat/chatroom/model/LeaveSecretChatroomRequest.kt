package com.likeminds.likemindschat.chatroom.model

class LeaveSecretChatroomRequest private constructor(
    val chatroomId: Int,
    val isSecret: Boolean
) {
    class Builder {
        private var chatroomId: Int = -1
        private var isSecret: Boolean = true

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun isSecret(isSecret: Boolean) = apply { this.isSecret = isSecret }

        fun build() = LeaveSecretChatroomRequest(chatroomId, isSecret)
    }

    fun toBuilder(): Builder {
        return Builder().isSecret(isSecret)
            .chatroomId(chatroomId)
    }
}