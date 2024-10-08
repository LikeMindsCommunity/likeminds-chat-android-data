package com.likeminds.likemindschat.user.util

import com.likeminds.likemindschat.user.model.UserRole

object UserRoleUtil {

    /***
     * Convert enum [UserRole] to the [String] value
     */
    fun UserRole.getUserRoleValue(): String {
        return when (this) {
            UserRole.CHATBOT -> UserRole.CHATBOT.value
            UserRole.MEMBER -> UserRole.MEMBER.value
        }
    }

    /***
     * Convert [String] value to enum [UserRole]
     */
    fun String.getUserRole(): UserRole {
        return when (this) {
            UserRole.CHATBOT.value -> UserRole.CHATBOT
            UserRole.MEMBER.value -> UserRole.MEMBER
            else -> UserRole.MEMBER
        }
    }
}