package com.likeminds.chatinternalsdk.conversation.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class _PostConversationRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("share_link")
    val shareLink: String?,
    @SerializedName("og_tags")
    val ogTags: _LinkOGTags_?,
    @SerializedName("replied_conversation_id")
    val repliedConversationId: String?,
    @SerializedName("temporary_id")
    val temporaryId: String?,
    @SerializedName("replied_chatroom_id")
    val repliedChatroomId: String?,
    @SerializedName("metadata")
    val metadata: JsonObject?,
    @SerializedName("trigger_bot")
    val triggerBot: Boolean?,
    @SerializedName("attachments")
    val attachments: List<_Attachment_>?
) {

    class Builder {

        private var chatroomId: String = ""
        private var text: String = ""
        private var shareLink: String? = null
        private var ogTags: _LinkOGTags_? = null
        private var repliedConversationId: String? = null
        private var temporaryId: String? = null
        private var repliedChatroomId: String? = null
        private var metadata: JsonObject? = null
        private var triggerBot: Boolean? = null
        private var attachments: List<_Attachment_>? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun shareLink(shareLink: String?) = apply { this.shareLink = shareLink }
        fun ogTags(ogTags: _LinkOGTags_?) = apply { this.ogTags = ogTags }
        fun repliedConversationId(repliedConversationId: String?) =
            apply { this.repliedConversationId = repliedConversationId }

        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }
        fun repliedChatroomId(repliedChatroomId: String?) =
            apply { this.repliedChatroomId = repliedChatroomId }

        fun metadata(metadata: JsonObject?) = apply { this.metadata = metadata }
        fun triggerBot(triggerBot: Boolean?) = apply { this.triggerBot = triggerBot }
        fun attachments(attachments: List<_Attachment_>?) = apply { this.attachments = attachments }

        fun build() = _PostConversationRequest_(
            chatroomId,
            text,
            shareLink,
            ogTags,
            repliedConversationId,
            temporaryId,
            repliedChatroomId,
            metadata,
            triggerBot,
            attachments
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .text(text)
            .shareLink(shareLink)
            .ogTags(ogTags)
            .repliedConversationId(repliedConversationId)
            .temporaryId(temporaryId)
            .repliedChatroomId(repliedChatroomId)
            .metadata(metadata)
            .triggerBot(triggerBot)
            .attachments(attachments)
    }
}