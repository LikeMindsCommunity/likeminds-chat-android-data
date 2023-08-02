package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _DeleteReactionRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String?,
    @SerializedName("chatroom_id")
    val chatroomId: String?
) {
    class Builder {
        private var conversationId: String? = null
        private var chatroomId: String? = null

        fun conversationId(conversationId: String?) = apply { this.conversationId = conversationId }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }

        fun build() = _DeleteReactionRequest_(conversationId, chatroomId)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId).conversationId(conversationId)
    }
}