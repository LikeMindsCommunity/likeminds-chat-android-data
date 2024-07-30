package com.likeminds.chatinternalsdk.moderation.model

import com.google.gson.annotations.SerializedName

class _PostReportRequest_ private constructor(
    @SerializedName("tag_id")
    var tagId: Int,
    @SerializedName("reason")
    var reason: String?,
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("conversation_id")
    var reportedConversationId: String?,
    @SerializedName("collabcard_id")
    var reportedChatroomId: String?,
    @SerializedName("link")
    var reportedLink: String?
) {

    class Builder {

        private var tagId: Int = -1
        private var reason: String? = null
        private var uuid: String? = null
        private var reportedConversationId: String? = null
        private var reportedChatroomId: String? = null
        private var reportedLink: String? = null

        fun tagId(tagId: Int) = apply { this.tagId = tagId }
        fun reason(reason: String?) = apply { this.reason = reason }
        fun uuid(uuid: String?) =
            apply { this.uuid = uuid }

        fun reportedConversationId(reportedConversationId: String?) =
            apply { this.reportedConversationId = reportedConversationId }

        fun reportedChatroomId(reportedChatroomId: String?) =
            apply { this.reportedChatroomId = reportedChatroomId }

        fun reportedLink(reportedLink: String?) = apply { this.reportedLink = reportedLink }

        fun build() = _PostReportRequest_(
            tagId,
            reason,
            uuid,
            reportedConversationId,
            reportedChatroomId,
            reportedLink
        )
    }

    fun toBuilder(): Builder {
        return Builder().tagId(tagId)
            .reason(reason)
            .uuid(uuid)
            .reportedConversationId(reportedConversationId)
            .reportedChatroomId(reportedChatroomId)
            .reportedLink(reportedLink)
    }
}