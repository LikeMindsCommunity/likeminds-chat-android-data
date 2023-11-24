package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_

data class _CreateDMChatroomResponse_(
    @SerializedName("chatroom_local")
    val chatroom: _Chatroom_
)