package com.likeminds.internalsdk.helper.model

import com.google.gson.annotations.SerializedName

data class _GetTaggingListResponse_(
    @SerializedName("group_tags")
    val groupTags: List<_GroupTag_>,
    @SerializedName("chatroom_participants")
    val chatroomParticipants: List<_UserTag_>,
    @SerializedName("community_members")
    val communityMembers: List<_UserTag_>
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

data class _UserTag_(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_guest")
    val isGuest: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_unique_id")
    val userUniqueId: String
)