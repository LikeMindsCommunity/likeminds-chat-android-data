package com.likeminds.internalsdk.helper

import com.likeminds.internalsdk.helper.model._DecodeUrlResponse_
import com.likeminds.internalsdk.helper.model._GetTaggingListResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface HelperNetworkApi {

    @GET("helper/url")
    suspend fun decodeUrl(
        @Query("url") url: String,
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>>

    @GET("community/tag")
    suspend fun getTaggingList(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>>
}