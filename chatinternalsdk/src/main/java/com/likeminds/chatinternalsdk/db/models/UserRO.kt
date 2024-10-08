package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmList
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
    var uuid: String = "",
    var roles: RealmList<String> = RealmList()
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
        builder.customTitle,
        builder.uuid,
        builder.roles
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
        var uuid: String = ""
        var roles: RealmList<String> = RealmList()

        fun build() = UserRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id, userUniqueId).apply {
            imageUrl = this@UserRO.imageUrl
            isGuest = this@UserRO.isGuest
            name = this@UserRO.name
            organizationName = this@UserRO.organizationName
            updatedAt = this@UserRO.updatedAt
            sdkClientInfoRO = this@UserRO.sdkClientInfoRO
            isDeleted = this@UserRO.isDeleted
            customTitle = this@UserRO.customTitle
            uuid = this@UserRO.uuid
            roles = this@UserRO.roles
        }
    }

}