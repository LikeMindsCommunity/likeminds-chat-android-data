package com.likeminds.likemindschat.dm.model

import com.likeminds.likemindschat.user.model.MemberBlockState

class BlockMemberRequest private constructor(
    val chatroomId: String,
    val status: MemberBlockState
) {
    class Builder {
        private var chatroomId: String = ""
        private var status: MemberBlockState = MemberBlockState.MEMBER_UNBLOCKED

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun status(status: MemberBlockState) = apply { this.status = status }

        fun build() = BlockMemberRequest(chatroomId, status)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .status(status)
    }
}