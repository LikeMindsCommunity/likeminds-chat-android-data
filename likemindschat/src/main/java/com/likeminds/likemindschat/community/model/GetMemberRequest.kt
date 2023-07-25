package com.likeminds.likemindschat.community.model

class GetMemberRequest private constructor(
    val uuid: String
) {
    class Builder {
        private var uuid: String = ""

        fun memberId(uuid: String) = apply { this.uuid = uuid }

        fun build() = GetMemberRequest(uuid)
    }

    fun toBuilder(): Builder {
        return Builder().memberId(uuid)
    }
}