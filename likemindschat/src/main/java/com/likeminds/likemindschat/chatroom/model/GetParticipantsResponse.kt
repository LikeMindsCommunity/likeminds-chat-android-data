package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.user.model.User

data class GetParticipantsResponse(
    val canEditParticipant: Boolean,
    val participants: List<User>,
    val totalParticipantsCount: Int
)