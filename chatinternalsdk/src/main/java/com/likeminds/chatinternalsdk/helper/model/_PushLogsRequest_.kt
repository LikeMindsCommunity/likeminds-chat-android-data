package com.likeminds.chatinternalsdk.helper.model

class _PushLogsRequest_ private constructor(
    val logs: List<_LMLog_>
) {
    class Builder {
        private var logs: List<_LMLog_> = emptyList()

        fun logs(logs: List<_LMLog_>) = apply {
            this.logs = logs
        }

        fun build() = _PushLogsRequest_(logs)
    }

    fun toBuilder(): Builder {
        return Builder().logs(logs)
    }
}