package com.likeminds.chatinternalsdk.user.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.community.model._Member_

data class _MemberStateResponse_(
    @SerializedName("state")
    val state: Int,
    @SerializedName("member")
    val member: _Member_?,
    @SerializedName("manager_rights")
    val managerRights: List<_ManagementRightPermissionData_>?,
    @SerializedName("member_rights")
    val memberRights: List<_ManagementRightPermissionData_>,
)