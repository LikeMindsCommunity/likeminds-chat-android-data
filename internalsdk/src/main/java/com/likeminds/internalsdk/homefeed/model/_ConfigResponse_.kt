package com.likeminds.internalsdk.homefeed.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

data class _ConfigResponse_(
    @SerializedName("access")
    val access: Boolean,
    @SerializedName("enable_audio")
    val enableAudio: Boolean,
    @SerializedName("enable_gif")
    val enableGifs: Boolean,
    @SerializedName("enable_voice_notes")
    val enableVoiceNote: Boolean,
    @SerializedName("micro_polls_enabled")
    val enableMicroPolls: Boolean,
    @SerializedName("user_detail")
    val userDetails: _UserDetail_
)

data class _UserDetail_(
    @SerializedName("user")
    val member: _Member_,
    @SerializedName("user_metrics")
    val userMetrics: _UserMetrics_
)