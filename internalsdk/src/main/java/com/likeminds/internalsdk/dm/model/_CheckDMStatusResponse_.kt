package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

data class _CheckDMStatusResponse_(
    @SerializedName("cta")
    val cta: String,
    @SerializedName("show_dm")
    val showDM: Boolean
)