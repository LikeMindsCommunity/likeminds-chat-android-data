package com.likeminds.internalsdk.search.model

import com.google.gson.annotations.SerializedName

data class _SearchMember_(
    @SerializedName("id")
    val id: Int,
    @SerializedName("profile")
    val profile: _SearchProfile_
)

data class _SearchProfile_(
    @SerializedName("name")
    val name: String
)
