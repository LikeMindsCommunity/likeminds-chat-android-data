package com.likeminds.internalsdk.homefeed.db

import com.likeminds.internalsdk.db.models.ChatroomRO
import com.likeminds.internalsdk.homefeed.HomeFeedReceiver
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFeedDBImpl @Inject constructor(private val homeFeedReceiver: HomeFeedReceiver) :
    HomeFeedDB {

    override fun getChatrooms(
        realm: Realm
    ): Flow<CollectionChange<RealmResults<ChatroomRO>>> {
        return homeFeedReceiver.getChatrooms(realm)
    }
}