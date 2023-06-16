package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

data class PostConversationResponse(
    @SerializedName("conversation")
    val conversation: Conversation,
    @SerializedName("id")
    val id: String?
)