package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class SDKClientInfoRO : EmbeddedRealmObject {

    var community: Int = 0
    var user: Int = 0
    var userUniqueId: String = ""
}