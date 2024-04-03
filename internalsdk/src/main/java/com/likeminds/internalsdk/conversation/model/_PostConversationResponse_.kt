package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.widget.model._Widget_

data class _PostConversationResponse_(
    @SerializedName("conversation")
    val conversation: _Conversation_,
    @SerializedName("id")
    val id: String?,
    @SerializedName("widgets")
    val widgets: Map<String, _Widget_>
)