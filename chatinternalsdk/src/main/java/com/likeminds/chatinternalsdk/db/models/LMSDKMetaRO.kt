package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LMSDKMetaRO(
    var dataLayerVersion: String? = null,
    var coreVersion: String? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.dataLayerVersion,
        builder.coreVersion
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var dataLayerVersion: String? = null
        var coreVersion: String? = null

        fun build() = LMSDKMetaRO(this)
    }
}