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
    @SerializedName("attachment_count")
    val attachmentCount: Int?,
    @SerializedName("temporary_id")
    val temporaryId: String?,
    @SerializedName("replied_chatroom_id")
    val repliedChatroomId: String?,
    @SerializedName("metadata")
    val metadata: JsonObject?
) {

    class Builder {

        private var chatroomId: String = ""
        private var text: String = ""
        private var shareLink: String? = null
        private var ogTags: _LinkOGTags_? = null
        private var repliedConversationId: String? = null
        private var attachmentCount: Int? = null
        private var temporaryId: String? = null
        private var repliedChatroomId: String? = null
        private var metadata: JsonObject? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun shareLink(shareLink: String?) = apply { this.shareLink = shareLink }
        fun ogTags(ogTags: _LinkOGTags_?) = apply { this.ogTags = ogTags }
        fun repliedConversationId(repliedConversationId: String?) =
            apply { this.repliedConversationId = repliedConversationId }

        fun attachmentCount(attachmentCount: Int?) =
            apply { this.attachmentCount = attachmentCount }

        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }
        fun repliedChatroomId(repliedChatroomId: String?) =
            apply { this.repliedChatroomId = repliedChatroomId }
        fun metadata(metadata: JsonObject?) = apply { this.metadata = metadata }

        fun build() = _PostConversationRequest_(
            chatroomId,
            text,
            shareLink,
            ogTags,
            repliedConversationId,
            attachmentCount,
            temporaryId,
            repliedChatroomId,
            metadata
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
            .metadata(metadata)
    }
}