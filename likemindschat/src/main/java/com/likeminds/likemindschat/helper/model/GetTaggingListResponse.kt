package com.likeminds.likemindschat.helper.model

import com.likeminds.likemindschat.community.model.Member

data class GetTaggingListResponse(
    val groupTags: List<GroupTag>,
    val chatroomParticipants: List<Member>,
    val communityMembers: List<Member>
)

data class GroupTag(
    val description: String,
    val name: String,
    val route: String,
    val tag: String,
    val imageUrl: String
)