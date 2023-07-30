package com.likeminds.internalsdk.homefeed.db

import com.likeminds.internalsdk.db.models.ChatroomRO
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange

interface HomeFeedDB {

    //db query to get chatrooms for home feed
    fun getChatrooms(realm: Realm): Observable<CollectionChange<RealmResults<ChatroomRO>>>?
}