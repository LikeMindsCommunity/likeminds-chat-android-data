package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _LeaveSecretChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: Int,
    @SerializedName("is_secret")
    val isSecret: Boolean
) {
    class Builder {
        private var chatroomId: Int = -1
        private var isSecret: Boolean = true

        fun chatroomId(chatroomId: Int) = apply { this.chatroomId = chatroomId }
        fun isSecret(isSecret: Boolean) = apply { this.isSecret = isSecret }

        fun build() = _LeaveSecretChatroomRequest_(chatroomId, isSecret)
    }

    fun toBuilder(): Builder {
        return Builder().isSecret(isSecret)
            .chatroomId(chatroomId)
    }
}