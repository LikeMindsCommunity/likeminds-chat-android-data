package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _DeleteConversationRequest_ private constructor(
    @SerializedName("conversation_ids")
    val conversationIds: List<String>
) {

    class Builder {

        private var conversationIds: List<String> = emptyList()

        fun conversationIds(conversationIds: List<String>) =
            apply { this.conversationIds = conversationIds }

        fun build() = _DeleteConversationRequest_(conversationIds)
    }
}