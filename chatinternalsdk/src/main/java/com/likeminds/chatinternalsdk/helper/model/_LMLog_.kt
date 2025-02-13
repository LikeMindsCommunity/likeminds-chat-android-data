package com.likeminds.chatinternalsdk.helper.model

class _LMLog_ private constructor(
    val timestamp: Long,
    val deviceMeta: _LMDeviceDetails_,
    val stackTrace: _LMStackTrace_,
    val sdkMeta: _LMSDKMeta_?,
    val severity: String?
) {
    class Builder {
        private var timestamp: Long = 0L
        private var deviceMeta: _LMDeviceDetails_ = _LMDeviceDetails_.Builder().build()
        private var stackTrace: _LMStackTrace_ = _LMStackTrace_.Builder().build()
        private var sdkMeta: _LMSDKMeta_? = null
        private var severity: String? = null

        fun timestamp(timestamp: Long) = apply {
            this.timestamp = timestamp
        }

        fun deviceMeta(deviceMeta: _LMDeviceDetails_) = apply {
            this.deviceMeta = deviceMeta
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

        fun build() = _LMLog_(
            timestamp,
            deviceMeta,
            stackTrace,
            sdkMeta,
            severity
        )
    }

    fun toBuilder(): Builder {
        return Builder().timestamp(timestamp)
            .deviceMeta(deviceMeta)
            .stackTrace(stackTrace)
            .sdkMeta(sdkMeta)
            .severity(severity)
    }
}