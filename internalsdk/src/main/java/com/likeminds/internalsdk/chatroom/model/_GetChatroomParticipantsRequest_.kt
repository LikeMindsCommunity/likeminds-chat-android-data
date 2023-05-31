package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _GetChatroomParticipantsRequest_ private constructor(
    @SerializedName("is_secret")
    val isChatroomSecret: Boolean,
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("participant_name")
    val participantName: String?,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int
) {
    class Builder {
        private var isChatroomSecret: Boolean = false
        private var chatroomId: String = ""
        private var participantName: String? = null
        private var page: Int = 1
        private var pageSize: Int = 10

        fun isChatroomSecret(isChatroomSecret: Boolean) =
            apply { this.isChatroomSecret = isChatroomSecret }

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun participantName(participantName: String?) =
            apply { this.participantName = participantName }

        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }

        fun build() = _GetChatroomParticipantsRequest_(
            isChatroomSecret,
            chatroomId,
            participantName,
            page,
            pageSize
        )
    }

    fun toBuilder(): Builder {
        return Builder().isChatroomSecret(isChatroomSecret)
            .chatroomId(chatroomId)
            .participantName(participantName)
            .page(page)
            .pageSize(pageSize)
    }
}