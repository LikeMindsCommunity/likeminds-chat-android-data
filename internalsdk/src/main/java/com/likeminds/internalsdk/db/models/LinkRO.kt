package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LinkRO : RealmObject() {

    var url: String = ""
    var chatroomId: String = ""
    var communityId: String = ""
    var title: String? = null
    var image: String? = null
    var description: String? = null
}