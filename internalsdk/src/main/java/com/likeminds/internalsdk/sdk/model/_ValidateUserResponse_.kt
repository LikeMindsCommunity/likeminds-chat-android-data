package com.likeminds.internalsdk.sdk.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._CommunitySetting_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.user.model._User_

data class _ValidateUserResponse_(
    @SerializedName("community")
    val community: _Community_,
    @SerializedName("user")
    val user: _User_,
    @SerializedName("app_access")
    val appAccess: Boolean,
    @SerializedName("has_answers")
    val hasAnswers: Boolean?,
    @SerializedName("community_settings")
    val communitySettings: List<_CommunitySetting_>
)