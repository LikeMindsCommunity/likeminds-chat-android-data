package com.likeminds.internalsdk.homefeed.db

import com.likeminds.internalsdk.db.models.ChatroomRO
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange
import kotlinx.coroutines.flow.Flow

interface HomeFeedDB {

    fun getChatrooms(realm: Realm): Flow<CollectionChange<RealmResults<ChatroomRO>>>
}