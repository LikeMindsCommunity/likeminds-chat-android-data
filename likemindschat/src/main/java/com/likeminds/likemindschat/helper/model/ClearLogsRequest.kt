package com.likeminds.likemindschat.helper.model

class ClearLogsRequest private constructor(
    val timestamp: Long
) {
    class Builder {
        private var timestamp: Long = 0

        fun timestamp(timestamp: Long) = apply {
            this.timestamp = timestamp
        }

        fun build() = ClearLogsRequest(timestamp)
    }

    fun toBuilder(): Builder {
        return Builder().timestamp(timestamp)
    }
}