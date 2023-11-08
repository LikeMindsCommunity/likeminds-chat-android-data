package com.likeminds.likemindschat.dm.model

data class CheckDMLimitResponse(
    val isRequestDMLimitExceeded: Boolean?,
    val newRequestDMTimestamp: Long?,
    val userDMLimit: UserDMLimit?,
    val chatroomId: String?
)