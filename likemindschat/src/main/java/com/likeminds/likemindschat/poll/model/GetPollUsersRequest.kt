package com.likeminds.likemindschat.poll.model

class GetPollUsersRequest private constructor(
    val pollId: String,
    val conversationId: String,
) {
    class Builder {
        private var pollId: String = ""
        private var conversationId: String = ""

        fun pollId(pollId: String) = apply { this.pollId = pollId }
        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }

        fun build() = GetPollUsersRequest(pollId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().pollId(pollId)
            .conversationId(conversationId)
    }
}