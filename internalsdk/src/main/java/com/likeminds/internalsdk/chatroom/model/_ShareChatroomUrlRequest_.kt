package com.likeminds.internalsdk.chatroom.model

class _ShareChatroomUrlRequest_ private constructor(
    val chatroomId: String,
    val domain: String
) {
    class Builder {
        private var chatroomId: String = ""
        private var domain: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun domain(domain: String) = apply { this.domain = domain }

        fun build() = _ShareChatroomUrlRequest_(chatroomId, domain)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .domain(domain)
    }
}