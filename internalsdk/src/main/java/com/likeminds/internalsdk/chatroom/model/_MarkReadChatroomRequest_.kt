package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _MarkReadChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: Int
) {
    class Builder {
        private var chatroomId: Int = -1

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }

        fun build() = _MarkReadChatroomRequest_(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}