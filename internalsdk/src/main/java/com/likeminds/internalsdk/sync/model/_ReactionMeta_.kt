package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

class _ReactionMeta_ private constructor(
    @SerializedName("id")
    val id: Int,
    @SerializedName("reaction")
    val reaction: String,
    @SerializedName("chatroom_id")
    val chatroomId: Int?,
    @SerializedName("conversation_id")
    val conversationId: Int?,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("member")
    val member: _Member_?
) {

    class Builder {

        private var id: Int = 0
        private var reaction: String = ""
        private var chatroomId: Int? = null
        private var conversationId: Int? = null
        private var userId: Int = 0
        private var member: _Member_? = null

        fun id(id: Int) = apply { this.id = id }
        fun reaction(reaction: String) = apply { this.reaction = reaction }
        fun chatroomId(chatroomId: Int?) = apply { this.chatroomId = chatroomId }
        fun conversationId(conversationId: Int?) = apply { this.conversationId = conversationId }
        fun userId(userId: Int) = apply { this.userId = userId }
        fun member(member: _Member_?) = apply { this.member = member }

        fun build() = _ReactionMeta_(id, reaction, chatroomId, conversationId, userId, member)
    }

    override fun toString(): String {
        return "id: $id reaction: $reaction userId: $userId"
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .reaction(reaction)
            .chatroomId(chatroomId)
            .conversationId(conversationId)
            .userId(userId)
            .member(member)
    }
}