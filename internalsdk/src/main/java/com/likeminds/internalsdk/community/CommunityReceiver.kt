package com.likeminds.internalsdk.community

import com.likeminds.internalsdk.community.model._GetExploreFeedRequest_
import com.likeminds.internalsdk.community.model._GetExploreFeedResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class CommunityReceiver @Inject constructor(private val communityNetworkApi: CommunityNetworkApi) {

    companion object {
        private const val ORDER_TYPE_KEY = "order_type"
        private const val IS_PINNED_KEY = "pinned"
        private const val PAGE_KEY = "page"
    }

    suspend fun getExploreFeed(
        request: _GetExploreFeedRequest_
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[ORDER_TYPE_KEY] = request.orderType
        queries[IS_PINNED_KEY] = request.isPinned
        queries[PAGE_KEY] = request.page

        return communityNetworkApi.getExploreFeed(queries)
    }
}