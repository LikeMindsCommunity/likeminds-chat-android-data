package com.likeminds.chatinternalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _MuteChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("value")
    val value: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = _MuteChatroomRequest_(chatroomId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .value(value)
    }
}