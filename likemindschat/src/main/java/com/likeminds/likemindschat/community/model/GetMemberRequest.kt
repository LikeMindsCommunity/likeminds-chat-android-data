package com.likeminds.likemindschat.community.model

class GetMemberRequest private constructor(
    val uuid: String
) {
    class Builder {
        private var uuid: String = ""

        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = GetMemberRequest(uuid)
    }

    fun toBuilder(): Builder {
        return Builder().uuid(uuid)
    }
}