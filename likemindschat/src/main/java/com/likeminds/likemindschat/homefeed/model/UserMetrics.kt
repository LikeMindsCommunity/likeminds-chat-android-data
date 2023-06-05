package com.likeminds.likemindschat.homefeed.model

data class UserMetrics(
    val firstLogin: String,
    val firstLoginEpoch: Long,
    val countCommunitiesJoined: Int,
    val nameCommunitiesJoined: String?,
    val isAnyCommunityPromoter: Boolean,
    val uniqueChatroomResponded: Int,
    val countChatroomCreated: Int,
    val countChatroomFollowed: Int
)