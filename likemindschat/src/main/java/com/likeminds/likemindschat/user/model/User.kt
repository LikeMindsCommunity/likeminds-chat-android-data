package com.likeminds.likemindschat.user.model

data class User(
    var id: Int,
    var imageUrl: String,
    var isGuest: Boolean,
    var name: String,
    var organisationName: String?,
    var sdkClientInfo: SDKClientInfo?,
    var isDeleted: Boolean?,
    var customTitle: String?,
    var updatedAt: Long,
    var userUniqueId: String
)

data class SDKClientInfo(
    var community: Int,
    var user: Int,
    var userUniqueId: String
)
