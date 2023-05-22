package com.likeminds.likemindschat.user.model

data class User(
    val id: Int,
    val imageUrl: String,
    val isGuest: Boolean,
    val name: String,
    val organisationName: String?,
    val sdkClientInfo: SDKClientInfo?,
    val isDeleted: Boolean?,
    val customTitle: String?,
    val updatedAt: Long,
    val userUniqueId: String
)

data class SDKClientInfo(
    val community: Int,
    val user: Int,
    val userUniqueId: String
)
