package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _SetChatroomTopicRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: Int,
    @SerializedName("conversation_id")
    val conversationId: Int
) {
    class Builder {
        private var chatroomId: Int = 0
        private var conversationId: Int = 0

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun conversationId(conversationId: Int) = apply { this.conversationId = conversationId }

        fun build() = _SetChatroomTopicRequest_(chatroomId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}