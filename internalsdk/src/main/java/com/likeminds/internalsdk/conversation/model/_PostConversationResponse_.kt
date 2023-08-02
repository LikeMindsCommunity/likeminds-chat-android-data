package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

data class _PostConversationResponse_(
    @SerializedName("conversation")
    val conversation: _Conversation_,
    @SerializedName("id")
    val id: String?
)