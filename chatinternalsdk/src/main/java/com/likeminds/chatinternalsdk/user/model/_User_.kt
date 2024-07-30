package com.likeminds.chatinternalsdk.user.model

import com.google.gson.annotations.SerializedName

data class _User_(
    @SerializedName("id")
    val id: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_guest")
    val isGuest: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("organisation_name")
    val organisationName: String?,
    @SerializedName("sdk_client_info")
    val sdkClientInfo: _SDKClientInfo_?,
    @SerializedName("is_deleted")
    val isDeleted: Boolean?,
    @SerializedName("custom_title")
    val customTitle: String?,
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @SerializedName("user_unique_id")
    val userUniqueId: String,
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("is_owner")
    val isOwner: Boolean?
)

data class _SDKClientInfo_(
    @SerializedName("community")
    val community: Int,
    @SerializedName("user")
    val user: String,
    @SerializedName("user_unique_id")
    val userUniqueId: String,
    @SerializedName("uuid")
    val uuid: String
)
