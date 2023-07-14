package com.likeminds.likemindschat.moderation.model

class PostReportRequest private constructor(
    var tagId: Int,
    var reason: String?,
    var uuid: String?,
    var reportedConversationId: String?,
    var reportedChatroomId: String?,
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

        fun build() = PostReportRequest(
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