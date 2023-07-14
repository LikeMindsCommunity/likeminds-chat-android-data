package com.likeminds.likemindschat.community.model

class GetMemberRequest private constructor(
    val memberId: String
) {
    class Builder {
        private var memberId: String = ""

        fun memberId(memberId: String) = apply { this.memberId = memberId }

        fun build() = GetMemberRequest(memberId)
    }

    fun toBuilder(): Builder {
        return Builder().memberId(memberId)
    }
}