package com.likeminds.likemindschat.homefeed.model

import com.google.gson.annotations.SerializedName
import com.likeminds.likemindschat.user.model.User

data class ConfigResponse(
    val access: Boolean,
    val enableAudio: Boolean,
    val enableGifs: Boolean,
    val enableVoiceNote: Boolean,
    val enableMicroPolls: Boolean,
    val userDetails: UserDetail
)

data class UserDetail(
    @SerializedName("user")
    val user: User,
    @SerializedName("user_metrics")
    val userMetrics: UserMetrics
)