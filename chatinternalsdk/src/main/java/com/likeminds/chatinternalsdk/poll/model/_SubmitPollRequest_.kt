package com.likeminds.chatinternalsdk.poll.model

import com.google.gson.annotations.SerializedName

class _SubmitPollRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String,
    @SerializedName("polls")
    val polls: List<_Poll_>
) {
    class Builder {
        private var conversationId: String = ""
        private var polls: List<_Poll_> = listOf()

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun polls(polls: List<_Poll_>) = apply { this.polls = polls }

        fun build() = _SubmitPollRequest_(conversationId, polls)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .polls(polls)
    }
}