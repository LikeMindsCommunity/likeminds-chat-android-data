package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

data class _DeleteConversationResponse_(
    @SerializedName("conversations")
    val conversations: List<_Conversation_>
)