package com.likeminds.likemindschat.dm.model

class BlockMemberRequest private constructor(
    val chatroomId: String,
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