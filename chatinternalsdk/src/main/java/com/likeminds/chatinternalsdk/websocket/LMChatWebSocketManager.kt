package com.likeminds.chatinternalsdk.websocket

import android.content.Context
import android.net.*
import android.util.Log
import com.likeminds.chatinternalsdk.*
import com.likeminds.chatinternalsdk.LMChatSDK.Companion.LOG_TAG
import com.likeminds.chatinternalsdk.di.WebSocketQualifier
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.chatinternalsdk.utils.websocket.BaseSubscribeCallback
import kotlinx.coroutines.*
import okhttp3.*
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.pow

@Singleton
class LMChatWebSocketManager @Inject constructor(
    private val context: Context,
    @WebSocketQualifier private val client: OkHttpClient,
    private val baseUrl: BaseUrl,
    private val sdkPreferences: SDKPreferences
) {

    companion object {
        const val TAG = "LMChatWebSocketManager"

        // headers
        private const val X_PLATFORM_CODE = "x-platform-code"
        private const val X_SDK_SOURCE = "x-sdk-source"
        private const val X_VERSION_CODE = "x-version-code"
        private const val AUTH = "Authorization"

        // error codes
        private const val UNAUTHORIZED = 401
        private const val SERVER_ERROR = 500
        private const val BAD_GATEWAY = 502
        private const val SERVICE_UNAVAILABLE = 503
        private const val GATEWAY_TIMEOUT = 504
        private const val TOO_MANY_REQUESTS = 429
    }


    // Maps to manage multiple WebSocket connections dynamically
    private val webSocketMap: ConcurrentHashMap<String, WebSocket> = ConcurrentHashMap()
    private val isConnectedMap: ConcurrentHashMap<String, Boolean> = ConcurrentHashMap()
    private val reconnectAttemptsMap: ConcurrentHashMap<String, Int> = ConcurrentHashMap()
    private val callbackMap: ConcurrentHashMap<String, BaseSubscribeCallback> = ConcurrentHashMap()
    private val chatTokenManager = ChatTokenManager.getInstance()
    private val storedEndpoints: MutableSet<String> = mutableSetOf()
    private var refreshJob: Job? = null
    private var isNetworkAvailable = true

    // CoroutineScope tied to WebSocketManager for structured concurrency
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        setupNetworkCallback()
    }

    /**
     * Sets up a network callback to monitor network connectivity.
     */
    private fun setupNetworkCallback() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d(TAG, "Network available. Reconnecting WebSockets...")
                    isNetworkAvailable = true
                    coroutineScope.launch {
                        reconnectAll()
                    }
                }

                override fun onLost(network: Network) {
                    Log.d(TAG, "Network lost. Closing all WebSockets...")
                    isNetworkAvailable = false
                    coroutineScope.launch {
                        closeAllPreservingEndpoints()
                    }
                }
            })
    }

    /**
     * create request for websocket connection
     */
    private fun getRequest(endPoint: String): Request {
        // get access token
        val accessToken = chatTokenManager.accessToken

        // web socket url
        val url = baseUrl.getPandemoniumBaseUrl() + endPoint

        // build request
        return Request.Builder()
            .url(url)
            .addHeader(AUTH, "Bearer $accessToken")
            .addHeader(X_PLATFORM_CODE, "an")
            .addHeader(X_VERSION_CODE, BuildConfig.APP_VERSION_CODE.toString())
            .addHeader(X_SDK_SOURCE, "chat")
            .build()
    }

    /**
     *  Creates a WebSocketListener for a given endpoint
     */
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

                when (response?.code) {
                    UNAUTHORIZED -> {
                        Log.e(TAG, "Unauthorized error for $endpoint. Token might be expired.")
                        handleTokenExpiry(endpoint)
                    }

                    SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT, TOO_MANY_REQUESTS -> {
                        Log.e(TAG, "Server error for $endpoint. Attempting reconnect.")
                        handleReconnect(endpoint)
                    }

                    else -> {
                        Log.e(TAG, "Unhandled error code: ${response?.code} for $endpoint")
                        callbackMap[endpoint]?.onError(t.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    /**
     * Updates the connection state for a given endpoint.
     */
    private fun updateConnectionState(endpoint: String, state: Boolean) {
        isConnectedMap[endpoint] = state
    }

    /**
     * Initiates a WebSocket connection for a specific endpoint with a callback
     */
    suspend fun connect(endpoint: String, callback: BaseSubscribeCallback) {
        if (!isNetworkAvailable) {
            Log.d(TAG, "Network unavailable. Delaying WebSocket connection for $endpoint")
            return
        }

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

    /**
     * Closes the WebSocket connection for a specific endpoint.
     */
    suspend fun close(endpoint: String, storeCallback: Boolean = false) {
        withContext(Dispatchers.IO) {
            webSocketMap[endpoint]?.close(1000, "Closing WebSocket for")
            webSocketMap.remove(endpoint)
            if (!storeCallback) {
                callbackMap.remove(endpoint)
            }
            Log.d(TAG, "WebSocket connection closed and cleaned for $endpoint")
        }
    }

    /**
     * Handles reconnection attempts for a specific endpoint.
     */
    private fun handleReconnect(endpoint: String) {
        val attempt = reconnectAttemptsMap[endpoint] ?: 0
        if (attempt >= 3) {
            Log.d(TAG, "Max reconnect attempts reached for $endpoint")
            return
        }
        val delayMillis = (1000L * 2.0.pow(attempt.toDouble())).toLong()
        Log.d(TAG, "Reconnecting $endpoint in $delayMillis ms (attempt: $attempt)")
        reconnectAttemptsMap[endpoint] = attempt + 1
        coroutineScope.launch {
            delay(delayMillis)
            callbackMap[endpoint]?.let { connect(endpoint, it) }
        }
    }

    /**
     * Handles token expiry and initiates a refresh attempt.
     */
    private fun handleTokenExpiry(endpoint: String) {
        if (refreshJob?.isActive == true) return // Prevent duplicate refreshes

        refreshJob = coroutineScope.launch {
            val chatSDK = LMChatSDK.getInstance()
            val lmInternalCallback = chatSDK.lmChatInternalCallback
            val refreshTokenNetworkApi = chatSDK.refreshTokenApiImpl

            val refreshToken = chatTokenManager.refreshToken ?: sdkPreferences.getRefreshToken()

            when (val refreshResponse =
                refreshTokenNetworkApi.refreshAccessToken("Bearer $refreshToken")) {
                is NetworkResponse.Error -> {
                    Log.d(
                        LOG_TAG,
                        "access token refresh failed: ${refreshResponse.body.errorMessage}"
                    )
                }

                is NetworkResponse.Success -> {
                    Log.d(LOG_TAG, "access token refreshed")

                    val newAccessToken = refreshResponse.body.data?.accessToken ?: ""
                    val newRefreshToken = refreshResponse.body.data?.refreshToken ?: ""

                    //update token manager
                    chatTokenManager.updateTokens(newAccessToken, newRefreshToken)

                    //update local prefs
                    sdkPreferences.setAccessToken(newAccessToken)
                    sdkPreferences.setRefreshToken(newRefreshToken)

                    //through callback
                    lmInternalCallback?.onAccessTokenExpiredAndRefreshed(
                        newAccessToken,
                        newRefreshToken
                    )

                    //reconnect callback map
                    callbackMap[endpoint]?.let {
                        connect(endpoint, it)
                    }
                }
            }
        }
    }

    /**
     * Closes all WebSocket connections and clears the stored endpoints.
     */
    private suspend fun closeAllPreservingEndpoints() {
        withContext(Dispatchers.IO) {
            storedEndpoints.clear()
            storedEndpoints.addAll(webSocketMap.keys)
            webSocketMap.keys.forEach { close(it, true) }
        }
    }

    /**
     * Reconnects to all stored WebSocket connections.
     */
    private suspend fun reconnectAll() {
        withContext(Dispatchers.IO) {
            storedEndpoints.forEach { endpoint ->
                Log.d(TAG, "Reconnecting WebSocket for $endpoint")
                callbackMap[endpoint]?.let {
                    connect(endpoint, it)
                }
            }
        }
    }

    /**
     * Closes all WebSocket connections.
     */
    suspend fun closeAll() {
        withContext(Dispatchers.IO) {
            webSocketMap.keys.forEach { close(it) }
        }
    }
}