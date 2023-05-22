package com.likeminds.likemindschat.initiateUser.model

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.user.model.User

data class InitiateUserResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val user: User? = null, //user data
    val community: Community? = null, //community data
    val appAccess: Boolean?,
    val logoutResponse: LMResponse<Nothing>? = null, //logout data
)