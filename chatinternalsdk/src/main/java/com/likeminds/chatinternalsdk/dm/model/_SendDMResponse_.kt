package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.widget.model._Widget_

data class _SendDMResponse_(
    @SerializedName("conversation")
    val conversation: _Conversation_,
    @SerializedName("widgets")
    val widgets: Map<String, _Widget_>
)