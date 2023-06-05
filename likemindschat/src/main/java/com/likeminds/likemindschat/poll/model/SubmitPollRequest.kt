package com.likeminds.likemindschat.poll.model

class SubmitPollRequest private constructor(
    val conversationId: String,
    val polls: List<Poll>
) {
    class Builder {
        private var conversationId: String = ""
        private var polls: List<Poll> = listOf()

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun polls(polls: List<Poll>) = apply { this.polls = polls }

        fun build() = SubmitPollRequest(conversationId, polls)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .polls(polls)
    }
}