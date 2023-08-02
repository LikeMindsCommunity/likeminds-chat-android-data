package com.likeminds.likemindschat.poll.model

import com.likeminds.likemindschat.community.model.Member

data class GetPollUsersResponse(
    val members: List<Member>
)