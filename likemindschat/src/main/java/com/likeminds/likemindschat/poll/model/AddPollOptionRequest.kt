package com.likeminds.likemindschat.poll.model

class AddPollOptionRequest private constructor(
    val conversationId: String,
    val poll: Poll
) {
    class Builder {
        private var conversationId: String = ""
        private var poll: Poll = Poll.Builder().build()

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun poll(poll: Poll) = apply { this.poll = poll }

        fun build() = AddPollOptionRequest(conversationId, poll)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .poll(poll)
    }
}