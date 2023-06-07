package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class LinkRO : EmbeddedRealmObject {

    var url: String = ""
    var chatroomId: String = ""
    var communityId: String = ""
    var title: String? = null
    var image: String? = null
    var description: String? = null
}