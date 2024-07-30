package com.likeminds.chatinternalsdk.search

import com.likeminds.chatinternalsdk.search.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class SearchReceiver @Inject constructor(
    private val searchNetworkApi: SearchNetworkApi
) {

    companion object {
        private const val SEARCH_KEY = "search"
        private const val FOLLOW_STATUS_KEY = "follow_status"
        private const val PAGE_KEY = "page"
        private const val PAGE_SIZE_KEY = "page_size"
        private const val SEARCH_TYPE_KEY = "search_type"
    }

    suspend fun searchChatroom(
        request: _SearchChatroomRequest_
    ): NetworkResponse<APIResponse<_SearchChatroomResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[SEARCH_KEY] = request.search
        queries[FOLLOW_STATUS_KEY] = request.followStatus
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize
        queries[SEARCH_TYPE_KEY] = request.searchType

        return searchNetworkApi.searchChatroom(queries)
    }

    suspend fun searchConversation(
        request: _SearchConversationRequest_
    ): NetworkResponse<APIResponse<_SearchConversationResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[SEARCH_KEY] = request.search
        queries[FOLLOW_STATUS_KEY] = request.followStatus
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize

        return searchNetworkApi.searchConversation(queries)
    }
}