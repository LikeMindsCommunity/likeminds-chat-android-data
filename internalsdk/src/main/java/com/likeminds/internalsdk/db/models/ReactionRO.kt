package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class ReactionRO : EmbeddedRealmObject {

    var member: MemberRO? = null
    var reaction: String = ""
}