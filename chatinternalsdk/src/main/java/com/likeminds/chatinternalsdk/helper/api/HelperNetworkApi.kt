package com.likeminds.chatinternalsdk.helper.api

import com.likeminds.chatinternalsdk.helper.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface HelperNetworkApi {

    @GET("helper/url")
    suspend fun decodeUrl(
        @Query("url") url: String,
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>>

    @GET("community/tag")
    suspend fun getTaggingList(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>>

    @POST("logs")
    suspend fun pushLogs(
        @Body request: _PushLogsRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}