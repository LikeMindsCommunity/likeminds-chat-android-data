package com.likeminds.internalsdk.moderation

import com.likeminds.internalsdk.moderation.model._GetReportTagsRequest_
import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._PostReportRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
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