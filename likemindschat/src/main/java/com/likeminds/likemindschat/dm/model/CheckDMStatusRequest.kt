package com.likeminds.likemindschat.dm.model

class CheckDMStatusRequest private constructor(
    val requestFrom: String,
    val chatroomId: String?,
    val uuid: String
) {
    class Builder {
        private var requestFrom: String = ""
        private var chatroomId: String? = null
        private var uuid: String = ""

        fun requestFrom(requestFrom: String) = apply { this.requestFrom = requestFrom }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = CheckDMStatusRequest(
            requestFrom,
            chatroomId,
            uuid
        )
    }

    fun toBuilder(): Builder {
        return Builder().requestFrom(requestFrom)
            .chatroomId(chatroomId)
            .uuid(uuid)
    }
}