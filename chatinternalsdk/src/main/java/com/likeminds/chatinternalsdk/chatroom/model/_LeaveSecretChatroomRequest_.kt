package com.likeminds.chatinternalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _LeaveSecretChatroomRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("is_secret")
    val isSecret: Boolean
) {
    class Builder {

        private var chatroomId: String = ""
        private var isSecret: Boolean = true

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun isSecret(isSecret: Boolean) = apply { this.isSecret = isSecret }

        fun build() = _LeaveSecretChatroomRequest_(chatroomId, isSecret)
    }

    fun toBuilder(): Builder {
        return Builder().isSecret(isSecret)
            .chatroomId(chatroomId)
    }
}