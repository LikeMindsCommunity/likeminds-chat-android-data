package com.likeminds.chatinternalsdk.poll.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.conversation.model._Conversation_

data class _PostPollConversationResponse_(
    @SerializedName("id")
    val id: String,
    @SerializedName("conversation")
    val conversation: _Conversation_
)