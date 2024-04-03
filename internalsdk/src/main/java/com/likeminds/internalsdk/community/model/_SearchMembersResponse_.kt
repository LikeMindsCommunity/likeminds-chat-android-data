package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _SearchMembersResponse_(
    @SerializedName("members")
    val members: List<_Member_>,
    @SerializedName("records_count")
    val recordsCount: Int
)