package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _SetChatroomTopicRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("conversation_id")
    val conversationId: String
) {
    class Builder {

        private var chatroomId: String = ""
        private var conversationId: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }

        fun build() = _SetChatroomTopicRequest_(chatroomId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}