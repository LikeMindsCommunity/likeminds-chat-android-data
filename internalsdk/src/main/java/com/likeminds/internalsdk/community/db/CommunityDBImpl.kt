package com.likeminds.internalsdk.community.db

import com.likeminds.internalsdk.community.CommunityReceiver
import com.likeminds.internalsdk.db.models.CommunityRO
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class CommunityDBImpl @Inject constructor(
    private val communityReceiver: CommunityReceiver
) : CommunityDB {

    override fun observeCommunity(
        realm: Realm,
        communityId: String
    ): Observable<CommunityRO> {
        return communityReceiver.observeCommunity(realm, communityId)
    }

    override fun updateContentDownloadSettings(list: List<String>, communityId: String) {
        communityReceiver.updateContentDownloadSettings(list, communityId)
    }
}