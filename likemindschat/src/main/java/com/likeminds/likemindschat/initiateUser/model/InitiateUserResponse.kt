package com.likeminds.likemindschat.initiateUser.model

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.user.model.User

data class InitiateUserResponse(
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var user: User? = null, //user data
    var community: Community? = null, //community data
    var appAccess: Boolean?,
    var logoutResponse: LMResponse<Nothing>? = null, //logout data
)