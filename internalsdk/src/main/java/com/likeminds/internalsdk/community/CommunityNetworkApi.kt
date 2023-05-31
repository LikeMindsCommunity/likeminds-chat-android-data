package com.likeminds.internalsdk.community

import com.likeminds.internalsdk.community.model._GetExploreFeedResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface CommunityNetworkApi {

    @Headers("x-accept-version: v2")
    @GET("community/feed")
    suspend fun getExploreFeed(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>
}