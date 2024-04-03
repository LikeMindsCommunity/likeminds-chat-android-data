package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class WidgetRO(
    var id: String = "",
    var parentEntityId: String = "",
    var parentEntityType: String = "",
    var metadata: String? = null,
    var createdAt: Long = 0L,
    var updatedAt: Long = 0L
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.parentEntityId,
        builder.parentEntityType,
        builder.metadata,
        builder.createdAt,
        builder.updatedAt
    )

    companion object {
        inline fun build(
            id: String,
            block: Builder.() -> Unit
        ) = Builder(id).apply(block).build()
    }

    class Builder(var id: String) {
        var parentEntityId: String = ""
        var parentEntityType: String = ""
        var metadata: String? = null
        var createdAt: Long = 0L
        var updatedAt: Long = 0L

        fun build() = WidgetRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id).apply {
            parentEntityId = this@WidgetRO.parentEntityId
            parentEntityType = this@WidgetRO.parentEntityType
            metadata = this@WidgetRO.metadata
            createdAt = this@WidgetRO.createdAt
            updatedAt = this@WidgetRO.updatedAt
        }
    }
}