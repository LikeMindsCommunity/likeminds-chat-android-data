package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.conversation.model._Conversation_

data class _BlockMemberResponse_(
    @SerializedName("conversation")
    val conversation: _Conversation_
)