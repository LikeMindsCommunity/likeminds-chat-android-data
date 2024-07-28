package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _Community_(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("members_count")
    val membersCount: Int?,
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @SerializedName("community_settings")
    val communitySettings: List<_CommunitySetting_>
)