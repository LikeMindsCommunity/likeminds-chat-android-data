package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

class BlockMemberRequest private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("status")
    val status: Int
) {
    class Builder {
        private var chatroomId: String = ""
        private var status: Int = 0

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun status(status: Int) = apply { this.status = status }

        fun build() = BlockMemberRequest(chatroomId, status)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .status(status)
    }
}