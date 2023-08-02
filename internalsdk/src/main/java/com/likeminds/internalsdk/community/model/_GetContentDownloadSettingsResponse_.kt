package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _GetContentDownloadSettingsResponse_(
    @SerializedName("content_download_settings")
    val settings: List<_ContentDownloadSetting_>,
)