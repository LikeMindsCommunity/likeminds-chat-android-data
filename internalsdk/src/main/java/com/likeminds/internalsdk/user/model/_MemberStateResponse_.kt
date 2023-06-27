package com.likeminds.internalsdk.user.model

import com.google.gson.annotations.SerializedName

data class _MemberStateResponse_(
    @SerializedName("state")
    val state: Int,
    @SerializedName("member")
    val member: _User_?,
    @SerializedName("manager_rights")
    val managerRights: List<_ManagementRightPermissionData_>?,
    @SerializedName("member_rights")
    val memberRights: List<_ManagementRightPermissionData_>,
)