package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _MuteChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: Int,
    @SerializedName("value")
    val value: Boolean
) {
    class Builder {
        private var chatroomId: Int = -1
        private var value: Boolean = false

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = _MuteChatroomRequest_(chatroomId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .value(value)
    }
}