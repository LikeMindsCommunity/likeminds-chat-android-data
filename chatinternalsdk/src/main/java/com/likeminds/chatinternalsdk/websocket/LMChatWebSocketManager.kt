package com.likeminds.chatinternalsdk.websocket

import android.util.Log
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.ChatTokenManager
import com.likeminds.chatinternalsdk.di.WebSocketQualifier
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import okhttp3.*
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
}