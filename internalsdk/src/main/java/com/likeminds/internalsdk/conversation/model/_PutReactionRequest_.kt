package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _PutReactionRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String?,
    @SerializedName("chatroom_id")
    val chatroomId: String?,
    @SerializedName("reaction")
    val reaction: String
) {
    class Builder {
        private var conversationId: String? = null
        private var chatroomId: String? = null
        private var reaction: String = ""

        fun conversationId(conversationId: String?) = apply { this.conversationId = conversationId }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun reaction(reaction: String) = apply { this.reaction = reaction }

        fun build() = _PutReactionRequest_(
            conversationId,
            chatroomId,
            reaction
        )
    }

    fun toBuilder(): Builder {
        return Builder().reaction(reaction)
            .chatroomId(chatroomId)
            .conversationId(conversationId)
    }
}