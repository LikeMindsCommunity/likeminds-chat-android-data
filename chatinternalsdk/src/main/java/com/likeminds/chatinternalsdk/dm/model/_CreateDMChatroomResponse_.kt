package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_

data class _CreateDMChatroomResponse_(
    @SerializedName("chatroom")
    val chatroom: _Chatroom_
)