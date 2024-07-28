package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName

class _SendDMRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("chat_request_state")
    val chatRequestState: Int,
    @SerializedName("text")
    val text: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var chatRequestState: Int = 0
        private var text: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun chatRequestState(chatRequestState: Int) =
            apply { this.chatRequestState = chatRequestState }

        fun text(text: String?) = apply { this.text = text }

        fun build() = _SendDMRequest_(chatroomId, chatRequestState, text)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .chatRequestState(chatRequestState)
            .text(text)
    }
}