package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

data class DeleteConversationResponse(
    @SerializedName("conversations")
    val conversations: List<Conversation>
)