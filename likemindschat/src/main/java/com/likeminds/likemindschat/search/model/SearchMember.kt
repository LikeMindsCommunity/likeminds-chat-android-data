package com.likeminds.likemindschat.search.model

data class SearchMember(
    val id: Int,
    val profile: SearchProfile
)

data class SearchProfile(
    val name: String
)
