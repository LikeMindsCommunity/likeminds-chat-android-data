package com.likeminds.internalsdk.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class CommunityRO : RealmObject {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var imageUrl: String? = null
    var membersCount: Int? = null
    var updatedAt: Long? = null
    var relationshipNeeded: Boolean = true

    var conversations: RealmList<ConversationRO> = realmListOf()
    var chatrooms: RealmList<ChatroomRO> = realmListOf()
}