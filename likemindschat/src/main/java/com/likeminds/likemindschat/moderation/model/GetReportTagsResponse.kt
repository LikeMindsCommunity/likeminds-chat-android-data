package com.likeminds.likemindschat.moderation.model

data class GetReportTagsResponse constructor(
    var tags: List<ReportTag>
)

data class ReportTag(
    var id: Int,
    var name: String
)
