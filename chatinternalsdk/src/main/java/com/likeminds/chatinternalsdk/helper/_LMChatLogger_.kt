package com.likeminds.chatinternalsdk.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.helper.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class _LMChatLogger_ private constructor(
    private val initiateLoggerRequest: _LMChatInitiateLoggerRequest_
) {
    companion object {
        private var chatLogger: _LMChatLogger_? = null

        // initiates the [_LMChatLogger_] with provided request
        fun initiate(
            initiateLoggerRequest: _LMChatInitiateLoggerRequest_
        ): _LMChatLogger_ {
            if (chatLogger == null) {
                chatLogger = _LMChatLogger_(initiateLoggerRequest)
            }

            return chatLogger!!
        }

        // returns the instance of [_LMChatLogger_]
        fun getInstance(): _LMChatLogger_? {
            return chatLogger
        }
    }

    // handles the exception by storing the exception in local DB
    fun handleException(
        exception: String,
        stackTrace: String,
        severity: _LMSeverity_,
    ) {
        if (chatLogger == null) {
            return
        }

        val helperDB = LMChatSDK.getInstance().helperDBImpl
        if (initiateLoggerRequest.logLevel.severityLevel <= severity.severityLevel && initiateLoggerRequest.shareLogsWithLM) {
            val dataLayerVersion = "${BuildConfig.SDK_MAJOR}.${BuildConfig.SDK_MINOR}.${BuildConfig.SDK_PATCH}"

            val sdkMeta = _LMSDKMeta_.Builder()
                    .dataLayerVersion(dataLayerVersion)
                    .coreVersion(initiateLoggerRequest.coreVersion)
                    .build()

            val logStackTrace = _LMStackTrace_.Builder()
                    .exception(exception)
                    .trace(stackTrace)
                    .build()

            helperDB.insertLog(
                _InsertLogRequest_.Builder()
                    .timestamp(System.currentTimeMillis())
                    .severity(severity.severityName)
                    .sdkMeta(sdkMeta)
                    .stackTrace(logStackTrace)
                    .build()
            )
        }

        initiateLoggerRequest.onErrorHandler(exception, stackTrace)
    }

    // flushes the logs by calling the API and clears the DB
    fun flushLogs() {
        if (chatLogger == null) {
            return
        }

        val clearLogTimestamp = System.currentTimeMillis()

        val chatSDK = LMChatSDK.getInstance()
        val helperDB = chatSDK.helperDBImpl
        val application = chatSDK.application

        val realm = Realm.getDefaultInstance()
        val logsRO = helperDB.getLogs(realm)
        if (logsRO.isNotEmpty()) {
            val deviceDimensions = getScreenWidthHeight(application.applicationContext)

            val deviceDetails = _LMDeviceDetails_.Builder()
                .deviceName(getDeviceName())
                .wifi(isConnectedToWifi(application.applicationContext))
                .versionOS(getAndroidVersion())
                .screenWidth(deviceDimensions.first)
                .screenHeight(deviceDimensions.second)
                .build()

            val logs = logsRO.map { logRO ->
                ROConverter.convertLogRO(
                    logRO,
                    deviceDetails
                )
            }

            realm.close()

            CoroutineScope(Dispatchers.IO).launch {
                val helperApi = LMChatSDK.getInstance().helperApiImpl
                val pushLogsRequest = _PushLogsRequest_.Builder()
                    .logs(logs)
                    .build()

                val response = helperApi.pushLogs(pushLogsRequest)

                if (response is NetworkResponse.Success) {
                    val clearLogRequest = _ClearLogsRequest_.Builder()
                        .timestamp(clearLogTimestamp)
                        .build()

                    helperDB.clearLogs(clearLogRequest)
                }
            }
        }
    }

    // returns the screen width and height
    private fun getScreenWidthHeight(context: Context): Pair<Int, Int> {
        val displayMetrics = context.resources.displayMetrics
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        return Pair(width, height)
    }

    // returns the Device's Android Version
    private fun getAndroidVersion(): String {
        val versionName = Build.VERSION.RELEASE // e.g., "14", "13", "12"
        val apiLevel = Build.VERSION.SDK_INT // e.g., 34, 33, 32
        return "Android Version: $versionName (API Level: $apiLevel)"
    }

    // returns whether the device is connected to Wifi or not
    private fun isConnectedToWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) // Checks if connected via WiFi
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.type == ConnectivityManager.TYPE_WIFI
        }
    }

    /** Returns the consumer friendly device name  */
    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        if (model.startsWith(manufacturer)) {
            return capitalize(model)
        }
        return capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(c.uppercaseChar())
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }
}