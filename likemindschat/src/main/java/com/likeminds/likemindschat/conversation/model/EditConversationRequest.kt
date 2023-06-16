package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

class EditConversationRequest private constructor(
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

        fun build() = EditConversationRequest(conversationId, text, shareLink)
    }
}