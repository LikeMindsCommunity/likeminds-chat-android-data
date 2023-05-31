package com.likeminds.internalsdk.moderation

import com.likeminds.internalsdk.moderation.model._GetReportTagsRequest_
import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._PostReportRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ModerationApi {

    // api to fetch report tags
    suspend fun getReportTags(
        request: _GetReportTagsRequest_
    ): NetworkResponse<APIResponse<_GetReportTagsResponse_>>

    // api to post report on the entity
    suspend fun postReport(
        request: _PostReportRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}