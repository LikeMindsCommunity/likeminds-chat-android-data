package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserRO : RealmObject {

    var id: Int = 0

    @PrimaryKey
    var userUniqueId: String = ""
    var imageUrl: String = ""
    var isGuest: Boolean = false
    var name: String = ""
    var organizationName: String? = null
    var updatedAt: Long = 0L
    var sdkClientInfoRO: SDKClientInfoRO? = null
}