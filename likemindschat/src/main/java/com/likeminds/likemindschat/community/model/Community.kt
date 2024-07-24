package com.likeminds.likemindschat.community.model

data class Community(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val membersCount: Int?,
    val updatedAt: Long?,
    val downloadableContentTypes: List<String>? = null,
    val communitySettings: List<CommunitySetting>? = null // community settings data
)
