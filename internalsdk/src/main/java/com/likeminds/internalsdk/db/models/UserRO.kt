package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRO(
    var id: String = "",

    @PrimaryKey
    var userUniqueId: String = "",
    var imageUrl: String = "",
    var isGuest: Boolean = false,
    var name: String = "",
    var organizationName: String? = null,
    var updatedAt: Long = 0L,
    var sdkClientInfoRO: SDKClientInfoRO? = null,
    var isDeleted: Boolean? = null,
    var customTitle: String? = null,
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.userUniqueId,
        builder.imageUrl,
        builder.isGuest,
        builder.name,
        builder.organizationName,
        builder.updatedAt,
        builder.sdkClientInfoRO,
        builder.isDeleted,
        builder.customTitle
    )

    companion object {

        inline fun build(
            id: String,
            userUniqueId: String,
            block: Builder.() -> Unit
        ) = Builder(id, userUniqueId).apply(block).build()
    }

    class Builder(var id: String, var userUniqueId: String) {

        var imageUrl: String = ""
        var isGuest: Boolean = false
        var name: String = ""
        var organizationName: String? = null
        var updatedAt: Long = 0L
        var sdkClientInfoRO: SDKClientInfoRO? = null
        var isDeleted: Boolean? = null
        var customTitle: String? = null

        fun build() = UserRO(this)
    }

}