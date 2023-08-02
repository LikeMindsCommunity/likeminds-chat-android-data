package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _ShareChatroomUrlRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("domain")
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