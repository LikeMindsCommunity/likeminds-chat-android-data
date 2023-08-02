package com.likeminds.likemindschat.poll.model

class PostPollConversationRequest private constructor(
    val chatroomId: String,
    val text: String,
    val repliedConversationId: String?,
    val polls: List<Poll>,
    val pollType: Int,
    val multipleSelectState: Int?,
    val multipleSelectNo: Int?,
    val isAnonymous: Boolean,
    val allowAddOption: Boolean,
    val expiryTime: Long,
    val temporaryId: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var text: String = ""
        private var repliedConversationId: String? = null
        private var polls: List<Poll> = listOf()
        private var pollType: Int = -1
        private var multipleSelectState: Int? = null
        private var multipleSelectNo: Int? = null
        private var isAnonymous: Boolean = false
        private var allowAddOption: Boolean = false
        private var expiryTime: Long = -1L
        private var temporaryId: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun text(text: String) = apply { this.text = text }
        fun repliedConversationId(repliedConversationId: String?) =
            apply { this.repliedConversationId = repliedConversationId }

        fun polls(polls: List<Poll>) = apply { this.polls = polls }
        fun pollType(pollType: Int) = apply { this.pollType = pollType }
        fun multipleSelectState(multipleSelectState: Int?) =
            apply { this.multipleSelectState = multipleSelectState }

        fun multipleSelectNo(multipleSelectNo: Int?) =
            apply { this.multipleSelectNo = multipleSelectNo }

        fun isAnonymous(isAnonymous: Boolean) = apply { this.isAnonymous = isAnonymous }
        fun allowAddOption(allowAddOption: Boolean) = apply { this.allowAddOption = allowAddOption }
        fun expiryTime(expiryTime: Long) = apply { this.expiryTime = expiryTime }
        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }

        fun build() = PostPollConversationRequest(
            chatroomId,
            text,
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