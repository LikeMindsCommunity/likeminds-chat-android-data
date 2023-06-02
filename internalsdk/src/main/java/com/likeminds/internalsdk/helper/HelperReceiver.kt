package com.likeminds.internalsdk.helper

import com.likeminds.internalsdk.helper.model._DecodeUrlRequest_
import com.likeminds.internalsdk.helper.model._DecodeUrlResponse_
import com.likeminds.internalsdk.helper.model._GetTaggingListRequest_
import com.likeminds.internalsdk.helper.model._GetTaggingListResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HelperReceiver @Inject constructor(
    private val helperNetworkApi: HelperNetworkApi
) {

    companion object {
        private const val CHATROOM_ID_KEY = "chatroom_id"
        private const val PAGE_KEY = "page"
        private const val PAGE_SIZE_KEY = "page_size"
        private const val SEARCH_NAME_KEY = "search_name"
    }

    suspend fun decodeUrl(
        request: _DecodeUrlRequest_
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>> {
        return helperNetworkApi.decodeUrl(request.url)
    }

    suspend fun getTaggingList(
        request: _GetTaggingListRequest_
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[CHATROOM_ID_KEY] = request.chatroomId
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize
        if (!request.searchName.isNullOrEmpty()) {
            queries[SEARCH_NAME_KEY] = request.searchName
        }

        return helperNetworkApi.getTaggingList(queries)
    }
}