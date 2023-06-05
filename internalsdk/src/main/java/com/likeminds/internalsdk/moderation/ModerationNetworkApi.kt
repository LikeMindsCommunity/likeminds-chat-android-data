package com.likeminds.internalsdk.moderation

import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._PostReportRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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