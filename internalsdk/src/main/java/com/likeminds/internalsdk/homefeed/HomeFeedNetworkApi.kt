package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET

interface HomeFeedNetworkApi {

    @GET("community/member/home/meta")
    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>>
}