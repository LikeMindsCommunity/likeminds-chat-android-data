package com.likeminds.chatinternalsdk.helper.model

import com.google.gson.annotations.SerializedName

class _LMStackTrace_ private constructor(
    @SerializedName("exception")
    val exception: String,
    @SerializedName("trace")
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

        fun build() = _LMStackTrace_(exception, trace)
    }

    fun toBuilder(): Builder {
        return Builder().exception(exception)
            .trace(trace)
    }
}