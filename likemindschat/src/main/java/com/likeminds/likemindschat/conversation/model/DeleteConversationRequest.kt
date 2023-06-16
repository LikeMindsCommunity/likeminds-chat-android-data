package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

class DeleteConversationRequest private constructor(
    @SerializedName("conversation_ids")
    val conversationIds: List<String>
) {

    class Builder {

        private var conversationIds: List<String> = emptyList()

        fun conversationIds(conversationIds: List<String>) =
            apply { this.conversationIds = conversationIds }

        fun build() = DeleteConversationRequest(conversationIds)
    }
}