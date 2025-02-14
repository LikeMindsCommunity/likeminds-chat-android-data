package com.likeminds.chatinternalsdk.helper.api

import com.likeminds.chatinternalsdk.helper.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HelperApiImpl @Inject constructor(
    private val helperReceiver: HelperReceiver
) : HelperApi {

    override suspend fun decodeUrl(
        request: _DecodeUrlRequest_
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>> {
        return helperReceiver.decodeUrl(request)
    }

    override suspend fun getTaggingList(
        request: _GetTaggingListRequest_
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>> {
        return helperReceiver.getTaggingList(request)
    }

    override suspend fun pushLogs(request: _PushLogsRequest_): NetworkResponse<APIResponse<Nothing>> {
        return helperReceiver.pushLogs(request)
    }
}