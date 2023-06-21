package com.likeminds.likemindschat.chatroom.model

data class GetParticipantsResponse(
    val canEditParticipant: Boolean,
    val participants: List<ParticipantData>,
    val totalParticipantsCount: Int
)

data class ParticipantData(
    val id: String,
    val imageUrl: String,
    val isGuest: Boolean?,
    val name: String,
    val userUniqueId: String,
    val customTitle: String?
)