package com.likeminds.likemindschat.conversation.model

class UpdateTemporaryConversationRequest private constructor(
    val conversationId: String,
    val localSavedEpoch: Long
) {
    class Builder {
        private var conversationId: String = ""
        private var localSavedEpoch: Long = -1L

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun localSavedEpoch(localSavedEpoch: Long) =
            apply { this.localSavedEpoch = localSavedEpoch }

        fun build() = UpdateTemporaryConversationRequest(conversationId, localSavedEpoch)
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId).localSavedEpoch(localSavedEpoch)
    }
}