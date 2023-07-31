package com.likeminds.internalsdk.community

import com.likeminds.internalsdk.community.api.CommunityNetworkApi
import com.likeminds.internalsdk.community.model.*
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.CommunityRO
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class CommunityReceiver @Inject constructor(private val communityNetworkApi: CommunityNetworkApi) {

    companion object {
        private const val ORDER_TYPE_KEY = "order_type"
        private const val IS_PINNED_KEY = "pinned"
        private const val PAGE_KEY = "page"
    }

    /**
     * API Functions
     */

    suspend fun getExploreFeed(
        request: _GetExploreFeedRequest_
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[ORDER_TYPE_KEY] = request.orderType
        if (request.isPinned != null) {
            queries[IS_PINNED_KEY] = request.isPinned
        }
        queries[PAGE_KEY] = request.page

        return communityNetworkApi.getExploreFeed(queries)
    }

    suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>> {
        return communityNetworkApi.getContentDownloadSettings()
    }

    /**
     * DB Functions
     */

    fun observeCommunity(realm: Realm, communityId: String): Observable<CommunityRO> {
        return realm.where(CommunityRO::class.java)
            .equalTo(DbKey.ID, communityId)
            .findFirstAsync()
            .asFlowable<CommunityRO>()
            .filter { it.isLoaded }
            .toObservable().take(1)
    }

    fun updateContentDownloadSettings(list: List<String>, communityId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getCommunity(it, communityId)?.let { communityRo ->
                communityRo.downloadableContentTypes = list.toRealmList()
            }
        })
    }
}