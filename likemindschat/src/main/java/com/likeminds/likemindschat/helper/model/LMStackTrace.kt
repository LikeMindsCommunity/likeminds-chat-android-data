package com.likeminds.likemindschat.helper.model

class LMStackTrace private constructor(
    val exception: String,
    val trace: String
) {
    class Builder {
        private var exception: String = ""
        private var trace: String = ""

        fun exception(exception: String) = apply {
            this.exception = exception
        }

        fun trace(trace: String) = apply {
            this.trace = trace
        }

        fun build() = LMStackTrace(exception, trace)
    }

    fun toBuilder(): Builder {
        return Builder().exception(exception)
            .trace(trace)
    }
}