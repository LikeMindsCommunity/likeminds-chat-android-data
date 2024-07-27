package com.likeminds.chatinternalsdk.community.db

import com.likeminds.chatinternalsdk.db.models.CommunityRO
import io.reactivex.Observable
import io.realm.Realm

interface CommunityDB {
    // observes the community and returns an observable
    fun observeCommunity(
        realm: Realm,
        communityId: String
    ): Observable<CommunityRO>

    // updates the content download settings in db
    fun updateContentDownloadSettings(list: List<String>, communityId: String)
}