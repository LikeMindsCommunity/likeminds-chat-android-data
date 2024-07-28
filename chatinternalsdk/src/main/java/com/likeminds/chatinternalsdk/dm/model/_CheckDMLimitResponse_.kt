package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName

data class _CheckDMLimitResponse_(
    @SerializedName("is_request_dm_limit_exceeded")
    val isRequestDMLimitExceeded: Boolean?,
    @SerializedName("new_request_dm_timestamp")
    val newRequestDMTimestamp: Long?,
    @SerializedName("user_dm_limit")
    val userDMLimit: _UserDMLimit_?,
    @SerializedName("chatroom_id")
    val chatroomId: String?
)