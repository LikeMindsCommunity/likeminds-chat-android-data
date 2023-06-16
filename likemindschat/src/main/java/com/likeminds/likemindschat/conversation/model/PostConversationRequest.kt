package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

class PostConversationRequest private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("share_link")
    val shareLink: String?,
    @SerializedName("og_tags")
    val ogTags: LinkOGTags?,
    @SerializedName("replied_conversation_id")
    val repliedConversationId: String?,
    @SerializedName("attachment_count")
    val attachmentCount: Int?,
    @SerializedName("temporary_id")
    val temporaryId: String?,
    @SerializedName("replied_chatroom_id")
    val repliedChatroomId: String?
) {

    class Builder {

        private var chatroomId: String = ""
        private var text: String = ""
        private var shareLink: String? = null
        private var ogTags: LinkOGTags? = null
        private var repliedConversationId: String? = null
        private var attachmentCount: Int? = null
        private var temporaryId: String? = null
        private var repliedChatroomId: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun shareLink(shareLink: String?) = apply { this.shareLink = shareLink }
        fun ogTags(ogTags: LinkOGTags?) = apply { this.ogTags = ogTags }
        fun repliedConversationId(repliedConversationId: String?) =
            apply { this.repliedConversationId = repliedConversationId }

        fun attachmentCount(attachmentCount: Int?) =
            apply { this.attachmentCount = attachmentCount }

        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }
        fun repliedChatroomId(repliedChatroomId: String?) =
            apply { this.repliedChatroomId = repliedChatroomId }

        fun build() = PostConversationRequest(
            chatroomId,
            text,
            shareLink,
            ogTags,
            repliedConversationId,
            attachmentCount,
            temporaryId,
            repliedChatroomId
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .text(text)
            .shareLink(shareLink)
            .ogTags(ogTags)
            .repliedConversationId(repliedConversationId)
            .attachmentCount(attachmentCount)
            .temporaryId(temporaryId)
            .repliedChatroomId(repliedChatroomId)
    }
}