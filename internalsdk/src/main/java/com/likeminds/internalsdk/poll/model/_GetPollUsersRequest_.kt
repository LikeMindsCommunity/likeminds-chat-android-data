package com.likeminds.internalsdk.poll.model

import com.google.gson.annotations.SerializedName

class _GetPollUsersRequest_ private constructor(
    @SerializedName("poll_id")
    val pollId: String,
    @SerializedName("conversation_id")
    val conversationId: String,
) {
    class Builder {
        private var pollId: String = ""
        private var conversationId: String = ""

        fun pollId(pollId: String) = apply { this.pollId = pollId }
        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }

        fun build() = _GetPollUsersRequest_(pollId, conversationId)
    }

    fun toBuilder(): Builder {
        return Builder().pollId(pollId)
            .conversationId(conversationId)
    }
}