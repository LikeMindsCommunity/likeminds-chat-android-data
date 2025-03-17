package com.likeminds.likemindschat.dm.model

class GetExistingDMChatroomRequest private constructor(
    val userUUID: String,
) {
    class Builder {
        private var userUUID: String = ""

        fun userUUID(userUUID: String) = apply {
            this.userUUID = userUUID
        }

        fun build() = GetExistingDMChatroomRequest(userUUID)
    }

    override fun toString(): String {
        return "GetExistingDMChatroomRequest(userUUID='$userUUID')"
    }

    fun toBuilder(): Builder {
        return Builder().userUUID(userUUID)
    }
}