package com.likeminds.chatinternalsdk.helper

import com.likeminds.chatinternalsdk.helper.model._DecodeUrlRequest_
import com.likeminds.chatinternalsdk.helper.model._DecodeUrlResponse_
import com.likeminds.chatinternalsdk.helper.model._GetTaggingListRequest_
import com.likeminds.chatinternalsdk.helper.model._GetTaggingListResponse_
import com.likeminds.chatinternalsdk.helper.model._PushLogsRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface HelperApi {

    // api to fetch ogTags of url
    suspend fun decodeUrl(
        request: _DecodeUrlRequest_
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>>

    //api to fetch taggingList
    suspend fun getTaggingList(
        request: _GetTaggingListRequest_
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>>

    //api to push logs
    suspend fun pushLogs(
        request: _PushLogsRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}