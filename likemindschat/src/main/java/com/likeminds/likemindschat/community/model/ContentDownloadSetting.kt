package com.likeminds.likemindschat.community.model

data class ContentDownloadSetting(
    val communityId: Int,
    val downloadSettingType: String,
    val downloadSettingTitle: String,
    val enabled: Boolean
)