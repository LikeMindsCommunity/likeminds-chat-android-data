package com.likeminds.internalsdk.dm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckDMTabResponse(
    @SerializedName("hide_dm_tab")
    val hideDMTab: Boolean,
    @SerializedName("is_cm")
    val isCM: Boolean,
    @SerializedName("unread_dm_count")
    val unreadDMCount: Int,
    @SerializedName("hide_dm_text")
    val hideDMText: String?
) : Parcelable