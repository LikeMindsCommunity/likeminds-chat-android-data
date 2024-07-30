package com.likeminds.chatinternalsdk.moderation

import com.likeminds.chatinternalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.chatinternalsdk.moderation.model._PostReportRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface ModerationNetworkApi {

    @GET("community/report/tag")
    suspend fun getReportTags(
        @Query("type") type: Int
    ): NetworkResponse<APIResponse<_GetReportTagsResponse_>>

    @POST("community/report")
    suspend fun postReport(
        @Body request: _PostReportRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}