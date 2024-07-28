package com.likeminds.chatinternalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _EditChatroomTitleRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("title")
    val text: String
) {
    class Builder {
        private var chatroomId: String = ""
        private var text: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }

        fun build() = _EditChatroomTitleRequest_(chatroomId, text)
    }
}