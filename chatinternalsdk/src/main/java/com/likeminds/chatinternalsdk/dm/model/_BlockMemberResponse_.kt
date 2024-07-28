package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.conversation.model._Conversation_

data class _BlockMemberResponse_(
    @SerializedName("conversation")
    val conversation: _Conversation_
)