package com.likeminds.internalsdk.poll.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

data class _GetPollUsersResponse_(
    @SerializedName("members")
    val members: List<_Member_>
)