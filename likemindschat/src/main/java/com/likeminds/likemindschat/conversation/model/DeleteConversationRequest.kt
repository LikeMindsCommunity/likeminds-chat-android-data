package com.likeminds.likemindschat.conversation.model

class DeleteConversationRequest private constructor(
    val conversationIds: List<String>
) {

    class Builder {

        private var conversationIds: List<String> = emptyList()

        fun conversationIds(conversationIds: List<String>) =
            apply { this.conversationIds = conversationIds }

        fun build() = DeleteConversationRequest(conversationIds)
    }
}