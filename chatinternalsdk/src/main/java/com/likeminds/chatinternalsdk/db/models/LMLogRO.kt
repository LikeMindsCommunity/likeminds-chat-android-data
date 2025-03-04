package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject

open class LMLogRO(
    var timestamp: Long = 0L,
    var stackTrace: LMStackTraceRO? = null,
    var sdkMeta: LMSDKMetaRO? = null,
    var severity: String? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.timestamp,
        builder.stackTrace,
        builder.sdkMeta,
        builder.severity,
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var timestamp: Long = 0L
        var stackTrace: LMStackTraceRO? = null
        var sdkMeta: LMSDKMetaRO? = null
        var severity: String? = null

        fun build() = LMLogRO(this)
    }
}