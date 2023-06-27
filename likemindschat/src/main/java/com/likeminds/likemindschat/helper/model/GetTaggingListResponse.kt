package com.likeminds.likemindschat.helper.model

import com.likeminds.likemindschat.user.model.User

data class GetTaggingListResponse(
    val groupTags: List<GroupTag>,
    val chatroomParticipants: List<User>,
    val communityMembers: List<User>
)

data class GroupTag(
    val description: String,
    val name: String,
    val route: String,
    val tag: String,
    val imageUrl: String
)