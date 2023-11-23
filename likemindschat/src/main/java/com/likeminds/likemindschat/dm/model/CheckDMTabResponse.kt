package com.likeminds.likemindschat.dm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CheckDMTabResponse(
    val hideDMTab: Boolean,
    val isCM: Boolean,
    val unreadDMCount: Int,
    val hideDMText: String?
) : Parcelable