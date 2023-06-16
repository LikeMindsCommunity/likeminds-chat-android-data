package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

data class EditConversationResponse(
    @SerializedName("conversation")
    var conversation: Conversation
)