package com.likeminds.likemindschat.chatroom.model

class GetChatroomParticipantsRequest private constructor(
    val isChatroomSecret: Boolean,
    val chatroomId: String,
    val participantName: String?,
    val page: Int,
    val pageSize: Int
) {
    class Builder {
        private var isChatroomSecret: Boolean = false
        private var chatroomId: String = ""
        private var participantName: String? = null
        private var page: Int = 1
        private var pageSize: Int = 10

        fun isChatroomSecret(isChatroomSecret: Boolean) =
            apply { this.isChatroomSecret = isChatroomSecret }

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun participantName(participantName: String?) =
            apply { this.participantName = participantName }

        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }

        fun build() = GetChatroomParticipantsRequest(
            isChatroomSecret,
            chatroomId,
            participantName,
            page,
            pageSize
        )
    }

    fun toBuilder(): Builder {
        return Builder().isChatroomSecret(isChatroomSecret)
            .chatroomId(chatroomId)
            .participantName(participantName)
            .page(page)
            .pageSize(pageSize)
    }
}