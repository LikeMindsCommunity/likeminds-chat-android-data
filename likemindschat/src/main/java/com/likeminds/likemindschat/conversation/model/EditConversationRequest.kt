package com.likeminds.likemindschat.conversation.model

class EditConversationRequest private constructor(
    val conversationId: String,
    val text: String,
    val shareLink: String?
) {

    class Builder {

        private var conversationId: String = ""
        private var text: String = ""
        private var shareLink: String? = null

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun text(text: String) = apply { this.text = text }
        fun shareLink(shareLink: String?) = apply { this.shareLink = shareLink }

        fun build() = EditConversationRequest(conversationId, text, shareLink)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .text(text)
            .shareLink(shareLink)
    }
}