package com.likeminds.internalsdk.homefeed.model

import com.google.gson.annotations.SerializedName

data class _GetExploreTabCountResponse_(
    @SerializedName("unseen_channel_count")
    val unseenChatroomCount: Int,
    @SerializedName("total_channel_count")
    val totalChatroomCount: Int
)