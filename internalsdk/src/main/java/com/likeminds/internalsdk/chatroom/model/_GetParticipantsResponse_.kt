package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

data class _GetParticipantsResponse_(
    @SerializedName("can_edit_participant")
    val canEditParticipant: Boolean,
    @SerializedName("participants")
    val participants: List<_Member_>,
    @SerializedName("total_participants_count")
    val totalParticipantsCount: Int
)