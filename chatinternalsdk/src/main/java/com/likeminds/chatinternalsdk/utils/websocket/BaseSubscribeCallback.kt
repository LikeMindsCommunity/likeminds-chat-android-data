package com.likeminds.chatinternalsdk.utils.websocket

interface BaseSubscribeCallback {
    fun onSocketConnectionOpen() // triggered when socket connection is opened
    fun onSocketConnectionClosed() // triggered when socket connection is closed
    fun onMessageReceived(data: String) // triggered when message is received from sockets
    fun onError(errorMessage: String) // triggered when socket connection receive an error
}