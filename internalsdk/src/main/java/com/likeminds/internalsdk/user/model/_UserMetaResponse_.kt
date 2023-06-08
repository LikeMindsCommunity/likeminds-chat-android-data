package com.likeminds.internalsdk.user.model

import com.google.gson.annotations.SerializedName

data class _UserMetaResponse_(
    @SerializedName("community_ids")
    val communityIds: List<_IdResponse_>
)