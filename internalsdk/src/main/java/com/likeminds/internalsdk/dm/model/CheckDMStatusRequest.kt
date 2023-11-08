package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

class CheckDMStatusRequest private constructor(
    @SerializedName("req_from")
    val requestFrom: String,
    @SerializedName("chatroom_id")
    val chatroomId: String?,
    @SerializedName("uuid")
    val uuid: String?,
    @SerializedName("member_id")
    val memberId: String?
) {
    class Builder {
        private var requestFrom: String = ""
        private var chatroomId: String? = null
        private var uuid: String? = null
        private var memberId: String? = null

        fun requestFrom(requestFrom: String) = apply { this.requestFrom = requestFrom }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun uuid(uuid: String?) = apply { this.uuid = uuid }
        fun memberId(memberId: String?) = apply { this.memberId = memberId }

        fun build() = CheckDMStatusRequest(
            requestFrom,
            chatroomId,
            uuid,
            memberId
        )
    }

    fun toBuilder(): Builder {
        return Builder().requestFrom(requestFrom)
            .chatroomId(chatroomId)
            .uuid(uuid)
            .memberId(memberId)
    }
}