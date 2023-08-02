package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.community.model.Member

data class GetParticipantsResponse(
    val canEditParticipant: Boolean,
    val participants: List<Member>,
    val totalParticipantsCount: Int
)