package com.likeminds.likemindschat.user.model

enum class MemberRole(val value:String) {
    ADMIN("admin"),
    MEMBER("member"),
    PENDING_MEMBER("pending_member")
}