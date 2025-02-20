package com.likeminds.likemindschat.helper.model

class InsertLogRequest private constructor(
    val timestamp: Long,
    val stackTrace: LMStackTrace,
    val sdkMeta: LMSDKMeta?,
    val severity: LMSeverity?
) {
    class Builder {
        private var timestamp: Long = 0L
        private var stackTrace: LMStackTrace = LMStackTrace.Builder().build()
        private var sdkMeta: LMSDKMeta? = null
        private var severity: LMSeverity? = null

        fun timestamp(timestamp: Long) = apply {
            this.timestamp = timestamp
        }

        fun stackTrace(stackTrace: LMStackTrace) = apply {
            this.stackTrace = stackTrace
        }

        fun sdkMeta(sdkMeta: LMSDKMeta?) = apply {
            this.sdkMeta = sdkMeta
        }

        fun severity(severity: LMSeverity?) = apply {
            this.severity = severity
        }

        fun build() = InsertLogRequest(
            timestamp,
            stackTrace,
            sdkMeta,
            severity
        )
    }

    fun toBuilder(): Builder {
        return Builder().timestamp(timestamp)
            .stackTrace(stackTrace)
            .sdkMeta(sdkMeta)
            .severity(severity)
    }
}