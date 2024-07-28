package com.likeminds.chatinternalsdk.sdk.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.community.model._Community_
import com.likeminds.chatinternalsdk.user.model._User_

data class _InitiateUserResponse_(
    @SerializedName("community")
    val community: _Community_,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("user")
    val user: _User_,
    @SerializedName("app_access")
    val appAccess: Boolean,
    @SerializedName("has_answers")
    val hasAnswers: Boolean?
)