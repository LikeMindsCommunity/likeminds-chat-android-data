package com.likeminds.internalsdk.community.api

import com.likeminds.internalsdk.community.model._GetContentDownloadSettingsResponse_
import com.likeminds.internalsdk.community.model._GetExploreFeedResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface CommunityNetworkApi {

    @Headers("x-accept-version: v2")
    @GET("community/feed")
    suspend fun getExploreFeed(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>

    @GET("community/settings/content_download")
    suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>>
}