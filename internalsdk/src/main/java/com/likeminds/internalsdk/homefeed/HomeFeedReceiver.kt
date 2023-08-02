package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.db.models.ChatroomRO
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.homefeed.api.HomeFeedNetworkApi
import com.likeminds.internalsdk.homefeed.model._ConfigResponse_
import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.sdk.util.SDKPreferences
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.reactivex.Observable
import io.realm.*
import io.realm.rx.CollectionChange
import javax.inject.Inject

class HomeFeedReceiver @Inject constructor(
    private val homeFeedNetworkApi: HomeFeedNetworkApi,
    private val sdkPreferences: SDKPreferences
) {

    /*
       API Functions
    */

    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>> {
        return homeFeedNetworkApi.getExploreTabCount()
    }

    suspend fun getConfig(): NetworkResponse<APIResponse<_ConfigResponse_>> {
        return homeFeedNetworkApi.getConfig()
    }

    /*
        Db Functions
     */

    fun getChatrooms(
        realm: Realm,
    ): Observable<CollectionChange<RealmResults<ChatroomRO>>>? {
        val communityId = sdkPreferences.getCommunityId()
        return realm.where(ChatroomRO::class.java)
            .isNull(DbKey.DELETED_BY)
            .equalTo(DbKey.COMMUNITY_ID, communityId)
            .notEqualTo(DbKey.TYPE, TYPE_DIRECT_MESSAGE)
            .notEqualTo(DbKey.TYPE, TYPE_EVENT)
            .notEqualTo(DbKey.TYPE, TYPE_EVENT_PUBLIC)
            .beginGroup()
            .equalTo(DbKey.FOLLOW_STATUS, true)
            .or()
            .equalTo(DbKey.IS_DRAFT, true)
            .endGroup()
            .sort(DbKey.UPDATED_AT, Sort.DESCENDING)
            .findAllAsync()
            .asChangesetObservable()
            .filter {
                it.collection.isLoaded && it.changeset != null
            }
    }
}