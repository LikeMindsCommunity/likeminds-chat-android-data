package com.likeminds.chatinternalsdk.homefeed.api

import com.likeminds.chatinternalsdk.homefeed.model._ConfigResponse_
import com.likeminds.chatinternalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET

interface HomeFeedNetworkApi {

    @GET("community/member/home/meta")
    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>>

    @GET("user/config")
    suspend fun getConfig(): NetworkResponse<APIResponse<_ConfigResponse_>>
}