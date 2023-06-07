package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class MemberRO : RealmObject {

    @PrimaryKey
    var uid: String = ""
    var id: String = ""
    var name: String = ""
    var imageUrl: String = ""
    var state: Int = 0
    var customIntroText: String? = null
    var customClickText: String? = null
    var customTitle: String? = null
    var communityId: Int? = null
    var isOwner: Boolean? = null
    var isGuest: Boolean? = false
    var userUniqueId: String? = null
}