package com.likeminds.likemindschat.dm.model

import com.likeminds.likemindschat.chatroom.model.ChatRequestState
import com.likeminds.likemindschat.conversation.model.Conversation
import org.json.JSONObject

class SendDMRequest private constructor(
    val chatroomId: String,
    val chatRequestState: ChatRequestState,
    val text: String?,
    val metadata: JSONObject?,
    val temporaryId: String?,
    val replyPrivatelySourceConversation: Conversation?
) {
    class Builder {
        private var chatroomId: String = ""
        private var chatRequestState: ChatRequestState = ChatRequestState.NOTHING
        private var text: String? = null
        private var metadata: JSONObject? = null
        private var temporaryId: String? = null
        private var replyPrivatelySourceConversation: Conversation? = null

        fun chatroomId(chatroomId: String) = apply {
            this.chatroomId = chatroomId
        }

        fun chatRequestState(chatRequestState: ChatRequestState) = apply {
            this.chatRequestState = chatRequestState
        }

        fun text(text: String?) = apply {
            this.text = text
        }

        fun metadata(metadata: JSONObject?) = apply {
            this.metadata = metadata
        }

        fun temporaryId(temporaryId: String?) = apply {
            this.temporaryId = temporaryId
        }

        fun replyPrivatelySourceConversation(replyPrivatelySourceConversation: Conversation?) = apply {
            this.replyPrivatelySourceConversation = replyPrivatelySourceConversation
        }

        fun build() = SendDMRequest(
            chatroomId,
            chatRequestState,
            text,
            metadata,
            temporaryId,
            replyPrivatelySourceConversation
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .chatRequestState(chatRequestState)
            .text(text)
            .metadata(metadata)
            .temporaryId(temporaryId)
            .replyPrivatelySourceConversation(replyPrivatelySourceConversation)
    }
}