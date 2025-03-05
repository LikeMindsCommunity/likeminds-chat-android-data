package com.likeminds.chatinternalsdk.helper.model

class _LMChatInitiateLoggerRequest_ private constructor(
    val shareLogsWithLM: Boolean,
    val onErrorHandler: ((exception: String, stackTrace: String) -> Unit),
    val logLevel: _LMSeverity_,
    val coreVersion: String?
) {
    class Builder {
        private var shareLogsWithLM: Boolean = true
        private var onErrorHandler: ((exception: String, stackTrace: String) -> Unit) = { _, _ -> }
        private var logLevel: _LMSeverity_ = _LMSeverity_.INFO
        private var coreVersion: String? = null

        fun shareLogsWithLM(value: Boolean) = apply {
            this.shareLogsWithLM = value
        }

        fun onErrorHandler(handler: (exception: String, stackTrace: String) -> Unit) = apply {
            this.onErrorHandler = handler
        }

        fun logLevel(logLevel: _LMSeverity_) = apply {
            this.logLevel = logLevel
        }

        fun coreVersion(coreVersion: String?) = apply {
            this.coreVersion = coreVersion
        }

        fun build() = _LMChatInitiateLoggerRequest_(
            shareLogsWithLM,
            onErrorHandler,
            logLevel,
            coreVersion
        )
    }

    fun toBuilder(): Builder {
        return Builder().shareLogsWithLM(shareLogsWithLM)
            .onErrorHandler(onErrorHandler)
            .logLevel(logLevel)
            .coreVersion(coreVersion)
    }
}