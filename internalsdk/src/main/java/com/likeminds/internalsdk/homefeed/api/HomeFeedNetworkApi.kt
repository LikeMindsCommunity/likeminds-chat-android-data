package com.likeminds.internalsdk.homefeed.api

import com.likeminds.internalsdk.homefeed.model._ConfigResponse_
import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeFeedNetworkApi {

    @GET("community/member/home/meta")
    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>>

    @GET("user/config")
    suspend fun getConfig(@Query("ingest_your_communities") ingestYourCommunities: Boolean): NetworkResponse<APIResponse<_ConfigResponse_>>
}