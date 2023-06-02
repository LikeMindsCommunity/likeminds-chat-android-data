package com.likeminds.internalsdk.poll.model

import com.google.gson.annotations.SerializedName

class _PostPollConversationRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("state")
    val state: Int,
    @SerializedName("replied_conversation_id")
    val repliedConversationId: String?,
    @SerializedName("polls")
    val polls: List<_Poll_>,
    @SerializedName("poll_type")
    val pollType: Int,
    @SerializedName("multiple_select_state")
    val multipleSelectState: Int?,
    @SerializedName("multiple_select_no")
    val multipleSelectNo: Int?,
    @SerializedName("is_anonymous")
    val isAnonymous: Boolean,
    @SerializedName("allow_add_option")
    val allowAddOption: Boolean,
    @SerializedName("expiry_time")
    val expiryTime: Long,
    @SerializedName("temporary_id")
    val temporaryId: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var text: String = ""
        private var state: Int = 10
        private var repliedConversationId: String? = null
        private var polls: List<_Poll_> = listOf()
        private var pollType: Int = -1
        private var multipleSelectState: Int? = null
        private var multipleSelectNo: Int? = null
        private var isAnonymous: Boolean = false
        private var allowAddOption: Boolean = false
        private var expiryTime: Long = -1L
        private var temporaryId: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun state(state: Int) = apply { this.state = state }
        fun repliedConversationId(repliedConversationId: String?) =
            apply { this.repliedConversationId = repliedConversationId }

        fun polls(polls: List<_Poll_>) = apply { this.polls = polls }
        fun pollType(pollType: Int) = apply { this.pollType = pollType }
        fun multipleSelectState(multipleSelectState: Int?) =
            apply { this.multipleSelectState = multipleSelectState }

        fun multipleSelectNo(multipleSelectNo: Int?) =
            apply { this.multipleSelectNo = multipleSelectNo }

        fun isAnonymous(isAnonymous: Boolean) = apply { this.isAnonymous = isAnonymous }
        fun allowAddOption(allowAddOption: Boolean) = apply { this.allowAddOption = allowAddOption }
        fun expiryTime(expiryTime: Long) = apply { this.expiryTime = expiryTime }
        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }

        fun build() = _PostPollConversationRequest_(
            chatroomId,
            text,
            state,
            repliedConversationId,
            polls,
            pollType,
            multipleSelectState,
            multipleSelectNo,
            isAnonymous,
            allowAddOption,
            expiryTime,
            temporaryId
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .text(text)
            .state(state)
            .repliedConversationId(repliedConversationId)
            .polls(polls)
            .pollType(pollType)
            .multipleSelectState(multipleSelectState)
            .multipleSelectNo(multipleSelectNo)
            .isAnonymous(isAnonymous)
            .allowAddOption(allowAddOption)
            .expiryTime(expiryTime)
            .temporaryId(temporaryId)
    }
}