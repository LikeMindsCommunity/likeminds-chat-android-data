package com.likeminds.chatinternalsdk.helper

import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.helper.model.*
import javax.inject.Inject

class _LMChatLogger_ @Inject constructor(
    private val initiateLoggerRequest: _LMChatInitiateLoggerRequest_
) {

    @Inject
    lateinit var chatSDK: LMChatSDK

    private val helperDB by lazy {
        chatSDK.getHelperDB()
    }

    companion object {
        private var chatLogger: _LMChatLogger_? = null

        fun getInstance(
            initiateLoggerRequest: _LMChatInitiateLoggerRequest_
        ): _LMChatLogger_ {
            if (chatLogger == null) {
                chatLogger = _LMChatLogger_(initiateLoggerRequest)
            }

            return chatLogger!!
        }

        fun getInstance(): _LMChatLogger_? {
            return chatLogger
        }
    }

    fun handleException(
        exception: String,
        stackTrace: String,
        severity: _LMSeverity_
    ) {
        if (initiateLoggerRequest.logLevel.severityLevel <= severity.severityLevel && initiateLoggerRequest.shareLogsWithLM) {
            helperDB.insertLog(
                _InsertLogRequest_.Builder()
                    .timestamp(System.currentTimeMillis())
                    .severity(severity.severityName)
                    .sdkMeta(
                        _LMSDKMeta_.Builder()
//                            .dataLayerVersion()
//                            .coreVersion()
                            .build()
                    )
                    .stackTrace(
                        _LMStackTrace_.Builder()
                            .exception(exception)
                            .trace(stackTrace)
                            .build()
                    )
                    .build()
            )
        }

        initiateLoggerRequest.onErrorHandler(exception, stackTrace)
    }
}