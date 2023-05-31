package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

data class _GetChatroomParticipantsResponse_(
    @SerializedName("can_edit_participant")
    val canEditParticipant: Boolean,
    @SerializedName("participants")
    val participants: List<_ParticipantData_>,
    @SerializedName("total_participants_count")
    val totalParticipantsCount: Int
)

data class _ParticipantData_(
    @SerializedName("id")
    val id: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_guest")
    val isGuest: Boolean?,
    @SerializedName("name")
    val name: String,
    @SerializedName("user_unique_id")
    val userUniqueId: String,
    @SerializedName("custom_title")
    val customTitle: String?
)