package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LMStackTraceRO(
    var exception: String = "",
    var trace: String = ""
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.exception,
        builder.trace
    )

    companion object {

        inline fun build(
            exception: String,
            trace: String,
            block: Builder.() -> Unit
        ) = Builder(exception, trace).apply(block).build()
    }

    class Builder(var exception: String, var trace: String) {
        fun build() = LMStackTraceRO(this)
    }
}