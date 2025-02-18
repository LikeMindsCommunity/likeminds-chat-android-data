package com.likeminds.likemindschat.helper.model

class LMChatInitiateLoggerRequest private constructor(
    val shareLogsWithLM: Boolean,
    val onErrorHandler: (String, String) -> Unit,
    val logLevel: LMSeverity,
    val coreVersion: String?
) {
    class Builder {
        private var shareLogsWithLM: Boolean = true
        private var onErrorHandler: ((String, String) -> Unit) = { _, _ -> }
        private var logLevel: LMSeverity = LMSeverity.INFO
        private var coreVersion: String? = null

        fun shareLogsWithLM(value: Boolean) = apply {
            this.shareLogsWithLM = value
        }

        fun onErrorHandler(handler: (String, String) -> Unit) = apply {
            this.onErrorHandler = handler
        }

        fun logLevel(level: LMSeverity) = apply {
            this.logLevel = level
        }

        fun coreVersion(coreVersion: String?) = apply {
            this.coreVersion = coreVersion
        }

        fun build() = LMChatInitiateLoggerRequest(
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