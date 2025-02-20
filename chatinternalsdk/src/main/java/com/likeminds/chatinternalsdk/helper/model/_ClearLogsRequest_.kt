package com.likeminds.chatinternalsdk.helper.model

class _ClearLogsRequest_ private constructor(
    val timestamp: Long
) {
    class Builder {
        private var timestamp: Long = 0

        fun timestamp(timestamp: Long) = apply {
            this.timestamp = timestamp
        }

        fun build() = _ClearLogsRequest_(timestamp)
    }

    fun toBuilder(): Builder {
        return Builder().timestamp(timestamp)
    }
}