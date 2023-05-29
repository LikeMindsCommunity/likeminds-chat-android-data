package com.likeminds.internalsdk.chatroom.model

class _GetChatroomRequest_ private constructor(
    val chatroomId: String
) {
    class Builder {
        private var chatroomId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }

        fun build() = _GetChatroomRequest_(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder()
    }
}