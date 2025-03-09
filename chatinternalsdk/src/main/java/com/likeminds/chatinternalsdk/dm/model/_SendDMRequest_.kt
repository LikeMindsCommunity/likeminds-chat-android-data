package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class _SendDMRequest_ private constructor(
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("chat_request_state")
    val chatRequestState: Int,
    @SerializedName("text")
    val text: String?,
    @SerializedName("metadata")
    val metadata: JSONObject?,
    @SerializedName("temporary_id")
    val temporaryId: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var chatRequestState: Int = 0
        private var text: String? = null
        private var metadata: JSONObject? = null
        private var temporaryId: String? = null

        fun chatroomId(chatroomId: String) = apply {
            this.chatroomId = chatroomId
        }

        fun chatRequestState(chatRequestState: Int) = apply {
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

        fun build() = _SendDMRequest_(
            chatroomId,
            chatRequestState,
            text,
            metadata,
            temporaryId
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .chatRequestState(chatRequestState)
            .text(text)
            .metadata(metadata)
            .temporaryId(temporaryId)
    }
}