package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_

data class CreateDMChatroomResponse(
    @SerializedName("chatroom_local")
    val chatroomLocal: _Chatroom_
)