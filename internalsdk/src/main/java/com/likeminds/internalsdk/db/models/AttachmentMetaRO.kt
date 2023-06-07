package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class AttachmentMetaRO : EmbeddedRealmObject {

    var numberOfPage: Int? = null
    var size: Long? = null //in bytes
    var duration: Int? = null//in seconds
}