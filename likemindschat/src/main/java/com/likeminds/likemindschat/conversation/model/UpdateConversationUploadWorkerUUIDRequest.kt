package com.likeminds.likemindschat.conversation.model

class UpdateConversationUploadWorkerUUIDRequest private constructor(
    val conversationId: String,
    val uuid: String
) {
    class Builder {
        private var conversationId: String = ""
        private var uuid: String = ""

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = UpdateConversationUploadWorkerUUIDRequest(conversationId, uuid)
    }

    fun toBuilder(): Builder {
        return Builder().uuid(uuid).conversationId(conversationId)
    }
}