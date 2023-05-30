package com.likeminds.likemindschat.chatroom.model

class ShareChatroomUrlRequest private constructor(
    val chatroomId: String,
    val domain: String
) {
    class Builder {
        private var chatroomId: String = ""
        private var domain: String = ""

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun domain(domain: String) = apply { this.domain = domain }

        fun build() = ShareChatroomUrlRequest(chatroomId, domain)
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .domain(domain)
    }
}