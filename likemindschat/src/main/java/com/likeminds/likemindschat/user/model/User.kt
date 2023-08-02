package com.likeminds.likemindschat.user.model

data class User(
    val id: String,
    val imageUrl: String,
    val isGuest: Boolean,
    val name: String,
    val organisationName: String?,
    val sdkClientInfo: SDKClientInfo?,
    val isDeleted: Boolean?,
    val customTitle: String?,
    val updatedAt: Long?,
    val userUniqueId: String,
    val uuid: String
)

data class SDKClientInfo(
    val community: Int,
    val user: String,
    val userUniqueId: String,
    val uuid: String
)
