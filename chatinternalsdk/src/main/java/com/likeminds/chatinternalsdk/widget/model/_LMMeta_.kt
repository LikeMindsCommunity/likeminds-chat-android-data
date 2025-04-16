package com.likeminds.chatinternalsdk.widget.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.conversation.model._Conversation_

class _LMMeta_ private constructor(
    @SerializedName("source_chatroom_id")
    val sourceChatroomId: String?,
    @SerializedName("source_chatroom_name")
    val sourceChatroomName: String?,
    @SerializedName("source_conversation")
    val sourceConversation: _Conversation_?,
    @SerializedName("type")
    val type: String?
) {
    class Builder {
        private var sourceChatroomId: String? = null
        private var sourceChatroomName: String? = null
        private var sourceConversation: _Conversation_? = null
        private var type: String? = null

        fun sourceChatroomId(sourceChatroomId: String?) = apply {
            this.sourceChatroomId = sourceChatroomId
        }

        fun sourceChatroomName(sourceChatroomName: String?) = apply {
            this.sourceChatroomName = sourceChatroomName
        }

        fun sourceConversation(sourceConversation: _Conversation_?) = apply {
            this.sourceConversation = sourceConversation
        }

        fun type(type: String?) = apply {
            this.type = type
        }

        fun build() = _LMMeta_(
            sourceChatroomId,
            sourceChatroomName,
            sourceConversation,
            type
        )
    }

    fun toBuilder(): Builder {
        return Builder().sourceChatroomId(sourceChatroomId)
            .sourceChatroomName(sourceChatroomName)
            .sourceConversation(sourceConversation)
            .type(type)
    }
}