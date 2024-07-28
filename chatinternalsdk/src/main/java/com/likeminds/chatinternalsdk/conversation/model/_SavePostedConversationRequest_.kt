package com.likeminds.chatinternalsdk.conversation.model

class _SavePostedConversationRequest_ private constructor(
    val conversation: _Conversation_,
    val isFromNotification: Boolean
) {
    class Builder {
        private var conversation: _Conversation_ = _Conversation_.Builder().build()
        private var isFromNotification: Boolean = false

        fun conversation(conversation: _Conversation_) = apply { this.conversation = conversation }
        fun isFromNotification(isFromNotification: Boolean) =
            apply { this.isFromNotification = isFromNotification }

        fun build() = _SavePostedConversationRequest_(conversation, isFromNotification)
    }

    fun toBuilder(): Builder {
        return Builder().conversation(conversation).isFromNotification(isFromNotification)
    }
}