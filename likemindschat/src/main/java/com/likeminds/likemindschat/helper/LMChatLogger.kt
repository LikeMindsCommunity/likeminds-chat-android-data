package com.likeminds.likemindschat.helper

import com.likeminds.chatinternalsdk.helper._LMChatLogger_
import com.likeminds.chatinternalsdk.helper.model._LMChatInitiateLoggerRequest_
import com.likeminds.likemindschat.helper.model.LMChatInitiateLoggerRequest
import com.likeminds.likemindschat.helper.model.LMSeverity
import com.likeminds.likemindschat.sdk.ModelConverter

class LMChatLogger private constructor() {
    companion object {
        private var chatLogger: LMChatLogger? = null
        private var internalChatLogger: _LMChatLogger_? = null

        fun getInstance(initiateLoggerRequest: LMChatInitiateLoggerRequest): LMChatLogger {
            if (chatLogger == null) {
                chatLogger = LMChatLogger()

                val internalRequest = _LMChatInitiateLoggerRequest_.Builder()
                    .shareLogsWithLM(initiateLoggerRequest.shareLogsWithLM)
                    .logLevel(ModelConverter.convertSeverity(initiateLoggerRequest.logLevel))
                    .onErrorHandler(initiateLoggerRequest.onErrorHandler)
                    .build()

                internalChatLogger = _LMChatLogger_.getInstance(internalRequest)
            }

            return chatLogger!!
        }

        fun getInstance(): LMChatLogger? {
            return chatLogger
        }
    }

    fun handleException(
        exception: String,
        stackTrace: String,
        severity: LMSeverity
    ) {
        if (chatLogger == null) {
            return
        }

        internalChatLogger?.handleException(
            exception,
            stackTrace,
            ModelConverter.convertSeverity(severity)
        )
    }
}