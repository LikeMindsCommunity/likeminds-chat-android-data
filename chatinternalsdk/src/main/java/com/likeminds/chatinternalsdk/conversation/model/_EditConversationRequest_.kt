package com.likeminds.chatinternalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _EditConversationRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("share_link")
    val shareLink: String?
) {

    class Builder {

        private var conversationId: String = ""
        private var text: String = ""
        private var shareLink: String? = null

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun text(text: String) = apply { this.text = text }
        fun shareLink(shareLink: String?) = apply { this.shareLink = shareLink }

        fun build() = _EditConversationRequest_(conversationId, text, shareLink)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .text(text)
            .shareLink(shareLink)
    }
}