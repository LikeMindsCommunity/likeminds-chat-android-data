package com.likeminds.likemindschat.chatroom.model

class FollowChatroomRequest private constructor(
    val chatroomId: String,
    val uuid: String,
    val value: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var uuid: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun uuid(uuid: String) = apply { this.uuid = uuid }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = FollowChatroomRequest(chatroomId, uuid, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .uuid(uuid)
            .value(value)
    }
}