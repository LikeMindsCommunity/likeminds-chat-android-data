package com.likeminds.chatinternalsdk.helper.model

class _LMChatInitiateLoggerRequest_ private constructor(
    val shareLogsWithLM: Boolean,
    val onErrorHandler: ((String, String) -> Unit) = { _, _ -> },
    val logLevel: _LMSeverity_
) {
    class Builder {
        private var shareLogsWithLM: Boolean = true
        private var onErrorHandler: ((String, String) -> Unit) = { _, _ -> }
        private var logLevel: _LMSeverity_ = _LMSeverity_.INFO

        fun shareLogsWithLM(value: Boolean) = apply {
            this.shareLogsWithLM = value
        }

        fun onErrorHandler(handler: (String, String) -> Unit) = apply {
            this.onErrorHandler = handler
        }

        fun logLevel(logLevel: _LMSeverity_) = apply {
            this.logLevel = logLevel
        }

        fun build() = _LMChatInitiateLoggerRequest_(
            shareLogsWithLM,
            onErrorHandler,
            logLevel
        )
    }

    fun toBuilder(): Builder {
        return Builder().shareLogsWithLM(shareLogsWithLM)
            .onErrorHandler(onErrorHandler)
            .logLevel(logLevel)
    }
}