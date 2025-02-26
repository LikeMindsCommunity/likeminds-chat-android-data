package com.likeminds.chatinternalsdk.websocket

import android.util.Log
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.ChatTokenManager
import com.likeminds.chatinternalsdk.di.WebSocketQualifier
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import com.likeminds.chatinternalsdk.utils.websocket.BaseSubscribeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okio.ByteString
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatWebSocketManager @Inject constructor(
    @WebSocketQualifier private val client: OkHttpClient,
    private val baseUrl: BaseUrl
) {

    companion object {
        const val TAG = "LMChatWebSocketManager"
        private const val X_PLATFORM_CODE = "x-platform-code"
        private const val X_SDK_SOURCE = "x-sdk-source"
        private const val X_VERSION_CODE = "x-version-code"
        private const val AUTH = "Authorization"
    }

    // Maps to manage multiple WebSocket connections dynamically
    private val webSocketMap: ConcurrentHashMap<String, WebSocket> = ConcurrentHashMap()
    private val isConnectedMap: ConcurrentHashMap<String, Boolean> = ConcurrentHashMap()
    private val reconnectAttemptsMap: ConcurrentHashMap<String, Int> = ConcurrentHashMap()
    private val callbackMap: ConcurrentHashMap<String, BaseSubscribeCallback> = ConcurrentHashMap()

    //create request for websocket connection
    private fun getRequest(endPoint: String): Request {
        // get access token
        val accessToken = ChatTokenManager.getInstance().accessToken

        // web socket url
        val url = baseUrl.getPandemoniumBaseUrl() + endPoint

        Log.d(TAG, "url: $url")

        return Request.Builder()
            .url(url)
            .addHeader(AUTH, "Bearer $accessToken")
            .addHeader(X_PLATFORM_CODE, "android")
            .addHeader(X_VERSION_CODE, BuildConfig.APP_VERSION_CODE.toString())
            .addHeader(X_SDK_SOURCE, "chat")
            .build()
    }

    // Creates a WebSocketListener for a given endpoint
    private fun getWebSocketListener(endpoint: String): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(TAG, "WebSocket Connected: $endpoint")
                updateConnectionState(endpoint, true)

                // Reset reconnect attempts on successful connection
                reconnectAttemptsMap[endpoint] = 0
                callbackMap[endpoint]?.onSocketConnectionOpen()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(TAG, "Message received in text from $endpoint: $text")
                callbackMap[endpoint]?.onMessageReceived(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(TAG, "Message received in bytes from $endpoint: $bytes")
                callbackMap[endpoint]?.onMessageReceived(bytes)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(TAG, "WebSocket Closing: $endpoint, code: $code, reason: $reason")
                updateConnectionState(endpoint, false)
                callbackMap[endpoint]?.onSocketConnectionClosed()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(TAG, "WebSocket Closed: $endpoint, code: $code, reason: $reason")
                updateConnectionState(endpoint, false)
                callbackMap[endpoint]?.onSocketConnectionClosed()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                updateConnectionState(endpoint, false)
                Log.e(TAG, "WebSocket Error for $endpoint: ${t.message}", t)
                callbackMap[endpoint]?.onError(t.message ?: "Unknown error")
            }
        }
    }

    private fun updateConnectionState(endpoint: String, state: Boolean) {
        isConnectedMap[endpoint] = state
    }

    // Initiates a WebSocket connection for a specific endpoint with a callback
    suspend fun connect(endpoint: String, callback: BaseSubscribeCallback) {
        if (isConnectedMap[endpoint] == true) {
            Log.d(TAG, "WebSocket for $endpoint is already connected.")
            return
        }
        callbackMap[endpoint] = callback
        withContext(Dispatchers.IO) {
            Log.d(TAG, "Connecting WebSocket for $endpoint...")
            val request = getRequest(endpoint)
            val webSocketListener = getWebSocketListener(endpoint)
            val webSocket = client.newWebSocket(request, webSocketListener)
            webSocketMap[endpoint] = webSocket
        }
    }

    suspend fun close(endpoint: String) {
        withContext(Dispatchers.IO) {
            webSocketMap[endpoint]?.close(1000, "Closing WebSocket for")
            webSocketMap.remove(endpoint)
            callbackMap.remove(endpoint)
            Log.d(TAG, "WebSocket connection closed and cleaned for $endpoint")
        }
    }
}