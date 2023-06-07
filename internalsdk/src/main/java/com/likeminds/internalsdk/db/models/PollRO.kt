package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class PollRO : EmbeddedRealmObject {

    var id: String = ""
    var text: String = ""
    var subText: String? = null
    var isSelected: Boolean? = null
    var percentage: Int? = null
    var noVotes: Int? = null
    var member: MemberRO? = null
}