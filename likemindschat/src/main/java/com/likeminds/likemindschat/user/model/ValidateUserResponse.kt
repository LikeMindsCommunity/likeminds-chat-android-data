package com.likeminds.likemindsfeed.user.model

import com.likeminds.likemindsfeed.LMResponse
import com.likeminds.likemindsfeed.sdk.model.Community
import com.likeminds.likemindsfeed.sdk.model.CommunitySetting
import com.likeminds.likemindsfeed.sdk.model.User

data class ValidateUserResponse(
    val user: User? = null, //user data
    val community: Community? = null, //community data
    val appAccess: Boolean?,
    val logoutResponse: LMResponse<Nothing>? = null, //logout data,
)
