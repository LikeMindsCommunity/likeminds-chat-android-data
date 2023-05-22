package com.likeminds.internalsdk.sdk.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.user.model._User_

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