package com.likeminds.likemindschat.helper.model

data class GetTaggingListResponse(
    val groupTags: List<GroupTag>,
    val chatroomParticipants: List<UserTag>,
    val communityMembers: List<UserTag>
)

data class GroupTag(
    val description: String,
    val name: String,
    val route: String,
    val tag: String,
    val imageUrl: String
)

data class UserTag(
    val id: Int,
    val imageUrl: String,
    val isGuest: Boolean,
    val name: String,
    val userUniqueId: String
)