package com.likeminds.internalsdk.user.model

import com.google.gson.annotations.SerializedName

data class _User_(
    @SerializedName("id")
    var id: Int,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("is_guest")
    var isGuest: Boolean,
    @SerializedName("name")
    var name: String,
    @SerializedName("organisation_name")
    var organisationName: String?,
    @SerializedName("sdk_client_info")
    var sdkClientInfo: _SDKClientInfo_?,
    @SerializedName("is_deleted")
    var isDeleted: Boolean?,
    @SerializedName("custom_title")
    var customTitle: String?,
    @SerializedName("updated_at")
    var updatedAt: Long,
    @SerializedName("user_unique_id")
    var userUniqueId: String
)

data class _SDKClientInfo_(
    @SerializedName("community")
    var community: Int,
    @SerializedName("user")
    var user: Int,
    @SerializedName("user_unique_id")
    var userUniqueId: String
)
