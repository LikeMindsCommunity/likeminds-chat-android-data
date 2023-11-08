package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

data class UserDMLimit(
    @SerializedName("number_in_duration")
    val numberInDuration: Int?,
    @SerializedName("duration")
    val duration: String?
)