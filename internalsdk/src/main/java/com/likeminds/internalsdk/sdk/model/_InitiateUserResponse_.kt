package com.likeminds.internalsdk.sdk.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.user.model._User_

data class _InitiateUserResponse_(
    @SerializedName("community")
    var community: _Community_,
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("refresh_token")
    var refreshToken: String,
    @SerializedName("user")
    var user: _User_,
    @SerializedName("app_access")
    var appAccess: Boolean,
    @SerializedName("has_answers")
    var hasAnswers: Boolean?
)