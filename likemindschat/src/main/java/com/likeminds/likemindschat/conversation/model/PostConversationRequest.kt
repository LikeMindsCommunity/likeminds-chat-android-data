package com.likeminds.likemindschat.conversation.model

import org.json.JSONObject

class PostConversationRequest private constructor(
    val chatroomId: String,
    val text: String,
    val isFromNotification: Boolean,
    val shareLink: String?,
    val ogTags: LinkOGTags?,
    val repliedConversationId: String?,
    val temporaryId: String?,
    val repliedChatroomId: String?,
    val metadata: JSONObject?,
    val triggerBot: Boolean,
    val attachments: List<Attachment>?,
    val replyPrivatelySourceConversation: Conversation?
) {
    class Builder {

        private var chatroomId: String = ""
        private var text: String = ""
        private var isFromNotification: Boolean = false
        private var shareLink: String? = null
        private var ogTags: LinkOGTags? = null
        private var repliedConversationId: String? = null
        private var temporaryId: String? = null
        private var repliedChatroomId: String? = null
        private var metadata: JSONObject? = null
        private var triggerBot: Boolean = false
        private var attachments: List<Attachment>? = null
        private var replyPrivatelySourceConversation: Conversation? = null

        fun chatroomId(chatroomId: String) = apply {
            this.chatroomId = chatroomId
        }

        fun text(text: String) = apply {
            this.text = text
        }

        fun isFromNotification(isFromNotification: Boolean) = apply {
            this.isFromNotification = isFromNotification
        }

        fun shareLink(shareLink: String?) = apply {
            this.shareLink = shareLink
        }

        fun ogTags(ogTags: LinkOGTags?) = apply {
            this.ogTags = ogTags
        }

        fun repliedConversationId(repliedConversationId: String?) = apply {
            this.repliedConversationId = repliedConversationId
        }

        fun temporaryId(temporaryId: String?) = apply {
            this.temporaryId = temporaryId
        }

        fun repliedChatroomId(repliedChatroomId: String?) = apply {
            this.repliedChatroomId = repliedChatroomId
        }

        fun metadata(metadata: JSONObject?) = apply {
            this.metadata = metadata
        }

        fun triggerBot(triggerBot: Boolean) = apply {
            this.triggerBot = triggerBot
        }

        fun attachments(attachments: List<Attachment>?) = apply {
            this.attachments = attachments
        }

        fun replyPrivatelySourceConversation(replyPrivatelySourceConversation: Conversation?) = apply {
            this.replyPrivatelySourceConversation = replyPrivatelySourceConversation
        }

        fun build() = PostConversationRequest(
            chatroomId,
            text,
            isFromNotification,
            shareLink,
            ogTags,
            repliedConversationId,
            temporaryId,
            repliedChatroomId,
            metadata,
            triggerBot,
            attachments,
            replyPrivatelySourceConversation
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .text(text)
            .isFromNotification(isFromNotification)
            .shareLink(shareLink)
            .ogTags(ogTags)
            .repliedConversationId(repliedConversationId)
            .temporaryId(temporaryId)
            .repliedChatroomId(repliedChatroomId)
            .metadata(metadata)
            .triggerBot(triggerBot)
            .attachments(attachments)
            .replyPrivatelySourceConversation(replyPrivatelySourceConversation)
    }
}