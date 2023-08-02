package com.likeminds.likemindschat.conversation.model

class PostConversationRequest private constructor(
    val chatroomId: String,
    val text: String,
    val isFromNotification: Boolean,
    val shareLink: String?,
    val ogTags: LinkOGTags?,
    val repliedConversationId: String?,
    val attachmentCount: Int?,
    val temporaryId: String?,
    val repliedChatroomId: String?
) {

    class Builder {

        private var chatroomId: String = ""
        private var text: String = ""
        private var isFromNotification: Boolean = false
        private var shareLink: String? = null
        private var ogTags: LinkOGTags? = null
        private var repliedConversationId: String? = null
        private var attachmentCount: Int? = null
        private var temporaryId: String? = null
        private var repliedChatroomId: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun isFromNotification(isFromNotification: Boolean) =
            apply { this.isFromNotification = isFromNotification }

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
            isFromNotification,
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
            .isFromNotification(isFromNotification)
            .shareLink(shareLink)
            .ogTags(ogTags)
            .repliedConversationId(repliedConversationId)
            .attachmentCount(attachmentCount)
            .temporaryId(temporaryId)
            .repliedChatroomId(repliedChatroomId)
    }
}