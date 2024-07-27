package com.likeminds.chatinternalsdk.poll.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.community.model._Member_

data class _GetPollUsersResponse_(
    @SerializedName("members")
    val members: List<_Member_>
)