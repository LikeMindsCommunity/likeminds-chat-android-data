package com.likeminds.internalsdk.moderation.model

import com.google.gson.annotations.SerializedName

class _PostReportRequest_ private constructor(
    @SerializedName("tag_id")
    var tagId: Int,
    @SerializedName("reason")
    var reason: String?,
    @SerializedName("reported_member_id")
    var reportedMemberId: Int?,
    @SerializedName("conversation_id")
    var reportedConversationId: Int?,
    @SerializedName("collabcard_id")
    var reportedChatroomId: Int?,
    @SerializedName("link")
    var reportedLink: String?
) {
    class Builder {
        private var tagId: Int = -1
        private var reason: String? = null
        private var reportedMemberId: Int? = null
        private var reportedConversationId: Int? = null
        private var reportedChatroomId: Int? = null
        private var reportedLink: String? = null

        fun tagId(tagId: Int) = apply { this.tagId = tagId }
        fun reason(reason: String?) = apply { this.reason = reason }
        fun reportedMemberId(reportedMemberId: Int?) =
            apply { this.reportedMemberId = reportedMemberId }

        fun reportedConversationId(reportedConversationId: Int?) =
            apply { this.reportedConversationId = reportedConversationId }

        fun reportedChatroomId(reportedChatroomId: Int?) =
            apply { this.reportedChatroomId = reportedChatroomId }

        fun reportedLink(reportedLink: String?) = apply { this.reportedLink = reportedLink }

        fun build() = _PostReportRequest_(
            tagId,
            reason,
            reportedMemberId,
            reportedConversationId,
            reportedChatroomId,
            reportedLink
        )
    }

    fun toBuilder(): Builder {
        return Builder().tagId(tagId)
            .reason(reason)
            .reportedMemberId(reportedMemberId)
            .reportedConversationId(reportedConversationId)
            .reportedChatroomId(reportedChatroomId)
            .reportedLink(reportedLink)
    }
}