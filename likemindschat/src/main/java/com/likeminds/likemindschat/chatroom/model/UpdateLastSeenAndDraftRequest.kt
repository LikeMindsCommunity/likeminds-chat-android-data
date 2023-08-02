package com.likeminds.likemindschat.chatroom.model

class UpdateLastSeenAndDraftRequest private constructor(
    val chatroomId: String,
    val draft: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var draft: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun draft(draft: String?) = apply { this.draft = draft }

        fun build() = UpdateLastSeenAndDraftRequest(chatroomId, draft)
    }

    fun toBuilder(): Builder {
        return Builder().draft(draft).chatroomId(chatroomId)
    }
}