package com.likeminds.internalsdk.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AppConfigRO : RealmObject {


    @PrimaryKey
    var id: Int = 0
    var communities: RealmList<Int> = realmListOf()
    var isConversationsSynced: Boolean = false
    var isChatroomsSynced: Boolean = false
    var isCommunitiesSynced: Boolean = false
}

