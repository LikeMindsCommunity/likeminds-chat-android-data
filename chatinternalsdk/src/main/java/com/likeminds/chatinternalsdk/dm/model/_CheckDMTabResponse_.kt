package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName

data class _CheckDMTabResponse_(
    @SerializedName("hide_dm_tab")
    val hideDMTab: Boolean,
    @SerializedName("is_cm")
    val isCM: Boolean,
    @SerializedName("unread_dm_count")
    val unreadDMCount: Int,
    @SerializedName("hide_dm_text")
    val hideDMText: String?
)