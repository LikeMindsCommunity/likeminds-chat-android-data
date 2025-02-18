package com.likeminds.chatinternalsdk.utils.websocket

import okio.ByteString

interface BaseSubscribeCallback {
    fun onSocketConnectionOpen()
    fun onSocketConnectionClosed()
    fun onMessageReceived(data: String)
    fun onMessageReceived(data: ByteString)
    fun onError(errorMessage: String)
}