package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _ContentDownloadSetting_(
    @SerializedName("community_id")
    val communityId: Int,
    @SerializedName("download_setting_type")
    val downloadSettingType: String,
    @SerializedName("download_setting_title")
    val downloadSettingTitle: String,
    @SerializedName("enabled")
    val enabled: Boolean
)