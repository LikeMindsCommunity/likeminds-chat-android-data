package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

data class CheckDMStatusResponse(
    @SerializedName("cta")
    val cta: String,
    @SerializedName("show_dm")
    val showDM: Boolean
)