package com.likeminds.likemindschat.chatroom.model

class LeaveSecretChatroomRequest private constructor(
    val chatroomId: String,
    val isSecret: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var isSecret: Boolean = true

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun isSecret(isSecret: Boolean) = apply { this.isSecret = isSecret }

        fun build() = LeaveSecretChatroomRequest(chatroomId, isSecret)
    }

    fun toBuilder(): Builder {
        return Builder().isSecret(isSecret)
            .chatroomId(chatroomId)
    }
}