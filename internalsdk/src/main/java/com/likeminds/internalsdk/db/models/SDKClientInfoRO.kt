package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class SDKClientInfoRO(
    var community: Int = 0,
    var user: String = "",
    var userUniqueId: String = "",
    var uuid: String = ""
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.community,
        builder.user,
        builder.userUniqueId,
        builder.uuid
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder() {

        var community: Int = 0
        var user: String = ""
        var userUniqueId: String = ""
        var uuid: String = ""

        fun build() = SDKClientInfoRO(this)
    }
}