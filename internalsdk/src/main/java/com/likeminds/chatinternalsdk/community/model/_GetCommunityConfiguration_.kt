package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _GetCommunityConfiguration_(
    @SerializedName("community_configurations")
    val configurations: List<_Configuration_>
)