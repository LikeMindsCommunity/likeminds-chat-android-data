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
        private const val PAGE = "page"
        private const val FILTER_MEMBER_ROLES = "filter_member_roles"
        private const val QUESTION_ANSWERS_VERSION = "question_answers_version"
        private const val MEMBER_STATES = "member_states"
        private const val SEARCH = "search"
        private const val SEARCH_TYPE = "search_type"
        private const val PAGE_SIZE = "page_size"
        private const val QUERY_EXCLUDE_SELF_USER = "exclude_self_user"
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

    suspend fun getAllMember(request: _GetAllMemberRequest_): NetworkResponse<APIResponse<_GetAllMemberResponse_>> {
        val queries = HashMap<String, Any>()

        queries[PAGE] = request.page
        queries[FILTER_MEMBER_ROLES] = request.filterMemberRoles
        queries[QUESTION_ANSWERS_VERSION] = "v2"

        if (request.excludeSelfUser != null) {
            queries[QUERY_EXCLUDE_SELF_USER] = request.excludeSelfUser
        }

        return communityNetworkApi.getAllMembers(queries)
    }

    suspend fun searchMembers(request: _SearchMembersRequest_): NetworkResponse<APIResponse<_SearchMembersResponse_>> {
        val queries = HashMap<String, Any>()
        queries[SEARCH] = request.search
        queries[SEARCH_TYPE] = request.searchType
        queries[PAGE] = request.page
        queries[PAGE_SIZE] = request.pageSize
        queries[QUESTION_ANSWERS_VERSION] = "v2"

        if (request.memberStates != null) {
            queries[MEMBER_STATES] = request.memberStates
        }

        if (request.excludeSelfUser != null) {
            queries[QUERY_EXCLUDE_SELF_USER] = request.excludeSelfUser
        }

        return communityNetworkApi.searchMembers(queries)
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