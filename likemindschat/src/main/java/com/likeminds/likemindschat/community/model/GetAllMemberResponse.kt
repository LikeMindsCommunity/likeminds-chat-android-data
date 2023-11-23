package com.likeminds.likemindschat.community.model

data class GetAllMemberResponse(
    val members: List<Member>,
    val totalMembers: Int?,
    val totalPendingMembers: Int?,
    val totalFilteredMembers: Int?,
    val totalOnlyMembers: Int?
)