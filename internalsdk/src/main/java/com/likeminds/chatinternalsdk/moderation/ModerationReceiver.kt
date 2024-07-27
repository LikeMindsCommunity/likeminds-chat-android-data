package com.likeminds.chatinternalsdk.moderation

import com.likeminds.chatinternalsdk.moderation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ModerationReceiver @Inject constructor(
    private val moderationNetworkApi: ModerationNetworkApi
) {

    suspend fun getReportTags(
        request: _GetReportTagsRequest_
    ): NetworkResponse<APIResponse<_GetReportTagsResponse_>> {
        return moderationNetworkApi.getReportTags(request.type)
    }

    suspend fun postReport(
        request: _PostReportRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return moderationNetworkApi.postReport(request)
    }
}