package com.likeminds.chatinternalsdk.poll.model

import com.google.gson.annotations.SerializedName

class _AddPollOptionRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String,
    @SerializedName("poll")
    val poll: _Poll_
) {
    class Builder {
        private var conversationId: String = ""
        private var poll: _Poll_ = _Poll_.Builder().build()

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun poll(poll: _Poll_) = apply { this.poll = poll }

        fun build() = _AddPollOptionRequest_(conversationId, poll)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .poll(poll)
    }
}