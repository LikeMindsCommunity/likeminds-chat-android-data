package com.likeminds.chatinternalsdk.moderation

import com.likeminds.chatinternalsdk.moderation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ModerationApiImpl @Inject constructor(
    private val moderationReceiver: ModerationReceiver
) : ModerationApi {

    override suspend fun getReportTags(
        request: _GetReportTagsRequest_
    ): NetworkResponse<APIResponse<_GetReportTagsResponse_>> {
        return moderationReceiver.getReportTags(request)
    }

    override suspend fun postReport(
        request: _PostReportRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return moderationReceiver.postReport(request)
    }
}