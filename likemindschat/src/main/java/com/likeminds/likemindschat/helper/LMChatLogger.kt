package com.likeminds.likemindschat.helper

import com.likeminds.chatinternalsdk.helper._LMChatLogger_
import com.likeminds.chatinternalsdk.helper.model._LMChatInitiateLoggerRequest_
import com.likeminds.likemindschat.helper.model.LMChatInitiateLoggerRequest
import com.likeminds.likemindschat.helper.model.LMSeverity
import com.likeminds.likemindschat.sdk.ModelConverter

class LMChatLogger private constructor() {
    companion object {
        private var chatLogger: LMChatLogger? = null
        private lateinit var internalChatLogger: _LMChatLogger_

        // initiates the [LMChatLogger] with provided request
        fun initiate(initiateLoggerRequest: LMChatInitiateLoggerRequest): LMChatLogger {
            if (chatLogger == null) {
                chatLogger = LMChatLogger()

                val internalRequest = _LMChatInitiateLoggerRequest_.Builder()
                    .shareLogsWithLM(initiateLoggerRequest.shareLogsWithLM)
                    .logLevel(ModelConverter.convertSeverity(initiateLoggerRequest.logLevel))
                    .onErrorHandler(initiateLoggerRequest.onErrorHandler)
                    .coreVersion(initiateLoggerRequest.coreVersion)
                    .build()

                internalChatLogger = _LMChatLogger_.initiate(internalRequest)
            }

            return chatLogger!!
        }

        // returns the instance of [LMChatLogger]
        fun getInstance(): LMChatLogger? {
            return chatLogger
        }
    }

    // handles the exception by calling the internal Logger
    fun handleException(
        exception: String,
        stackTrace: String,
        severity: LMSeverity
    ) {
        if (chatLogger == null) {
            return
        }

        internalChatLogger.handleException(
            exception,
            stackTrace,
            ModelConverter.convertSeverity(severity)
        )
    }

    // flushes the logs to backend by calling the internal Logger
    fun flushLogs() {
        if (chatLogger == null) {
            return
        }

        internalChatLogger.flushLogs()
    }
}