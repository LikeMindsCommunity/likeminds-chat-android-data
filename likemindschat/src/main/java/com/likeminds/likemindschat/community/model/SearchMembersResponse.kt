package com.likeminds.likemindschat.community.model

data class SearchMembersResponse(
    val members: List<Member>,
    val recordsCount: Int
)