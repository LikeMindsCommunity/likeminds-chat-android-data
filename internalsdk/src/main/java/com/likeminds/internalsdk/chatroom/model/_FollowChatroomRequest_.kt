package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _FollowChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("member_id")
    val memberId: String,
    @SerializedName("value")
    val value: Boolean
) {
    class Builder {
        private var chatroomId: String = ""
        private var memberId: String = ""
        private var value: Boolean = false

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun memberId(memberId: String) = apply { this.memberId = memberId }
        fun value(value: Boolean) = apply { this.value = value }

        fun build() = _FollowChatroomRequest_(chatroomId, memberId, value)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .memberId(memberId)
            .value(value)
    }
}