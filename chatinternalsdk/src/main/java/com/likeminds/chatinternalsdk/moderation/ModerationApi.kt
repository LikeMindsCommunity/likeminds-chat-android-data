package com.likeminds.chatinternalsdk.moderation

import com.likeminds.chatinternalsdk.moderation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

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