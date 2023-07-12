package com.likeminds.internalsdk.helper.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

data class _GetTaggingListResponse_(
    @SerializedName("group_tags")
    val groupTags: List<_GroupTag_>,
    @SerializedName("chatroom_participants")
    val chatroomParticipants: List<_Member_>,
    @SerializedName("community_members")
    val communityMembers: List<_Member_>
)

data class _GroupTag_(
    @SerializedName("description")
    val description: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("route")
    val route: String,
    @SerializedName("tag")
    val tag: String,
    @SerializedName("image_url")
    val imageUrl: String
)