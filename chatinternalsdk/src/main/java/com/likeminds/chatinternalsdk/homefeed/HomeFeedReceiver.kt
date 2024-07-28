package com.likeminds.chatinternalsdk.homefeed

import com.likeminds.chatinternalsdk.chatroom.model.*
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.homefeed.api.HomeFeedNetworkApi
import com.likeminds.chatinternalsdk.homefeed.model._ConfigResponse_
import com.likeminds.chatinternalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
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