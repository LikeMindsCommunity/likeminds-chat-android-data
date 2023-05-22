package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _Community_(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("image_url")
    var imageUrl: String?,
    @SerializedName("members_count")
    var membersCount: Int?,
    @SerializedName("updated_at")
    var updatedAt: String?
)