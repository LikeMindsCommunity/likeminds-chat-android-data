package com.likeminds.likemindschat.conversation.model

class SavePostedConversationRequest private constructor(
    val conversation: Conversation,
    val isFromNotification: Boolean
) {
    class Builder {
        private var conversation: Conversation = Conversation.Builder().build()
        private var isFromNotification: Boolean = false

        fun conversation(conversation: Conversation) = apply { this.conversation = conversation }
        fun isFromNotification(isFromNotification: Boolean) =
            apply { this.isFromNotification = isFromNotification }

        fun build() = SavePostedConversationRequest(conversation, isFromNotification)
    }

    fun toBuilder(): Builder {
        return Builder().conversation(conversation).isFromNotification(isFromNotification)
    }
}