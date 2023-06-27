package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.user.model._User_

data class _GetParticipantsResponse_(
    @SerializedName("can_edit_participant")
    val canEditParticipant: Boolean,
    @SerializedName("participants")
    val participants: List<_User_>,
    @SerializedName("total_participants_count")
    val totalParticipantsCount: Int
)