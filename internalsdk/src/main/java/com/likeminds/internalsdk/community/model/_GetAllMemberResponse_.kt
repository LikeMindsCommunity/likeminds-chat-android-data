package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _GetAllMemberResponse_(
    @SerializedName("members")
    val members: List<_Member_>,
    @SerializedName("total_members")
    val totalMembers: Int?,
    @SerializedName("total_pending_members")
    val totalPendingMembers: Int?,
    @SerializedName("total_filtered_members")
    val totalFilteredMembers: Int?,
    @SerializedName("total_only_members")
    val totalOnlyMembers: Int?,
    @SerializedName("admins_count")
    val adminsCount: Int?
)