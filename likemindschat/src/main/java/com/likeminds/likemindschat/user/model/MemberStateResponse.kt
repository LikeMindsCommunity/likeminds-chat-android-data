package com.likeminds.likemindschat.user.model

data class MemberStateResponse(
    val id: String,
    val state: Int,
    val userUniqueId: String,
    val customTitle: String?,
    val imageUrl: String,
    val isGuest: Boolean,
    val isOwner: Boolean?,
    val name: String,
    val managerRights: List<ManagementRightPermissionData>?,
    val memberRights: List<ManagementRightPermissionData>,
    val updatedAt: Long,
    val sdkClientInfo: SDKClientInfo?
)