package com.likeminds.likemindschat.user.model

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.community.model.Community


data class ValidateUserResponse(
    val user: User? = null, //user data
    val community: Community? = null, //community data
    val appAccess: Boolean?,
    val logoutResponse: LMResponse<Nothing>? = null, //logout data,
)
