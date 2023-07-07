package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class AttachmentMetaRO(
    var numberOfPage: Int? = null,
    var size: Long? = null, //in bytes
    var duration: Int? = null//in seconds
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.numberOfPage,
        builder.size,
        builder.duration
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var numberOfPage: Int? = null
        var size: Long? = null
        var duration: Int? = null

        fun build() = AttachmentMetaRO(this)
    }

    fun toBuilder(): Builder {
        return Builder().apply {
            numberOfPage = this@AttachmentMetaRO.numberOfPage
            size = this@AttachmentMetaRO.size
            duration = this@AttachmentMetaRO.duration
        }
    }
}