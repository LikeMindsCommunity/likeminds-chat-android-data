package com.likeminds.chatinternalsdk.helper.model

class _InsertLogRequest_ private constructor(
    val timestamp: Long,
    val stackTrace: _LMStackTrace_,
    val sdkMeta: _LMSDKMeta_?,
    val severity: String?
) {
    class Builder {
        private var timestamp: Long = 0L
        private var stackTrace: _LMStackTrace_ = _LMStackTrace_.Builder().build()
        private var sdkMeta: _LMSDKMeta_? = null
        private var severity: String? = null

        fun timestamp(timestamp: Long) = apply {
            this.timestamp = timestamp
        }

        fun stackTrace(stackTrace: _LMStackTrace_) = apply {
            this.stackTrace = stackTrace
        }

        fun sdkMeta(sdkMeta: _LMSDKMeta_?) = apply {
            this.sdkMeta = sdkMeta
        }

        fun severity(severity: String?) = apply {
            this.severity = severity
        }

        fun build() = _InsertLogRequest_(
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