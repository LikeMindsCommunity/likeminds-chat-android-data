package com.likeminds.likemindschat.conversation.model

class DeleteConversationsRequest private constructor(
    val conversationIds: List<String>
) {

    class Builder {

        private var conversationIds: List<String> = emptyList()

        fun conversationIds(conversationIds: List<String>) =
            apply { this.conversationIds = conversationIds }

        fun build() = DeleteConversationsRequest(conversationIds)
    }

    fun toBuilder(): Builder {
        return Builder().conversationIds(conversationIds)
    }
}