package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _GetChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String
) {
    class Builder {
        private var chatroomId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }

        fun build() = _GetChatroomRequest_(chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
    }
}