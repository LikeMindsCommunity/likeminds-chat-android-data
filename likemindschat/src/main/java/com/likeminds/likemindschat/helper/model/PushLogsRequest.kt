package com.likeminds.likemindschat.helper.model

class PushLogsRequest private constructor(
    val logs: List<LMLog>
) {
    class Builder {
        private var logs: List<LMLog> = emptyList()

        fun logs(logs: List<LMLog>) = apply {
            this.logs = logs
        }

        fun build() = PushLogsRequest(logs)
    }

    fun toBuilder(): Builder {
        return Builder().logs(logs)
    }
}