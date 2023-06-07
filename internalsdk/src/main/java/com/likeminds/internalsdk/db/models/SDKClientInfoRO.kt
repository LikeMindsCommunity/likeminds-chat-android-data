package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class SDKClientInfoRO : EmbeddedRealmObject {

    var community: Int = 0
    var user: String = ""
    var userUniqueId: String = ""
}