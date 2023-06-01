package com.likeminds.internalsdk.moderation.model

import com.google.gson.annotations.SerializedName

data class _GetReportTagsResponse_ constructor(
    @SerializedName("report_tags")
    val tags: List<_ReportTag_>
)

data class _ReportTag_(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
