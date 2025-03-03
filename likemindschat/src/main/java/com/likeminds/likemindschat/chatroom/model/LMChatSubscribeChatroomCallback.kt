package com.likeminds.likemindschat.chatroom.model

interface LMChatSubscribeChatroomCallback {
    fun onSocketConnectionOpen() // triggered when socket connection is opened
    fun onSocketConnectionClosed() // triggered when socket connection is closed
    fun onError(errorMessage: String) // triggered when error occurs
}