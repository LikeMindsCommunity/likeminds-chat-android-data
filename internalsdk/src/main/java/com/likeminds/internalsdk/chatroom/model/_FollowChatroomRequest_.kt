package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _FollowChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("value")
    val value: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var uuid: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun uuid(uuid: String) = apply { this.uuid = uuid }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = _FollowChatroomRequest_(chatroomId, uuid, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .uuid(uuid)
            .value(value)
    }
}