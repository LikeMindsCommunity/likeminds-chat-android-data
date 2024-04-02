package com.likeminds.likemindschat.dm.model

data class CheckDMTabResponse(
    val hideDMTab: Boolean,
    val isCM: Boolean,
    val unreadDMCount: Int,
    val hideDMText: String?
)