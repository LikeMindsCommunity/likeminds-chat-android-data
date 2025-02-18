package com.likeminds.likemindschat.chatroom.model

interface LMChatSubscribeChatroomCallback {
    fun onSocketConnectionOpen()
    fun onSocketConnectionClosed()
    fun onError(errorMessage: String)
}