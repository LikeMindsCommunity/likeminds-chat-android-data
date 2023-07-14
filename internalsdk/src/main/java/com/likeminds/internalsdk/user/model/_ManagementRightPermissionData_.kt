package com.likeminds.internalsdk.user.model

import com.google.gson.annotations.SerializedName

data class _ManagementRightPermissionData_(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_locked")
    val isLocked: Boolean?,
    @SerializedName("is_selected")
    val isSelected: Boolean,
    @SerializedName("state")
    val state: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("sub_title")
    val subtitle: String?
)