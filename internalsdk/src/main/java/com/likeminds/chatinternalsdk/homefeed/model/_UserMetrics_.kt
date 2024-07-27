package com.likeminds.chatinternalsdk.homefeed.model

import com.google.gson.annotations.SerializedName

data class _UserMetrics_(
    @SerializedName("first_login")
    val firstLogin: String,
    @SerializedName("first_login_epoch")
    val firstLoginEpoch: Long,
    @SerializedName("count_communities_joined")
    val countCommunitiesJoined: Int,
    @SerializedName("name_communities_joined")
    val nameCommunitiesJoined: String?,
    @SerializedName("is_any_community_promoter")
    val isAnyCommunityPromoter: Boolean,
    @SerializedName("unique_chatroom_responded")
    val uniqueChatroomResponded: Int,
    @SerializedName("count_chatroom_created")
    val countChatroomCreated: Int,
    @SerializedName("count_chatroom_followed")
    val countChatroomFollowed: Int
)