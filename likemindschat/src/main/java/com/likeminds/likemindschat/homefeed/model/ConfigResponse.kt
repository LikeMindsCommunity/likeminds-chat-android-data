package com.likeminds.likemindschat.homefeed.model

import com.likeminds.likemindschat.community.model.Member

data class ConfigResponse(
    val access: Boolean,
    val enableAudio: Boolean,
    val enableGifs: Boolean,
    val enableVoiceNote: Boolean,
    val enableMicroPolls: Boolean,
    val userDetails: UserDetail
)

data class UserDetail(
    val member: Member,
    val userMetrics: UserMetrics
)