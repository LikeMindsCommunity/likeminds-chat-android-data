package com.likeminds.chatinternalsdk.community.api

import com.likeminds.chatinternalsdk.community.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface CommunityNetworkApi {

    @Headers("x-accept-version: v2")
    @GET("community/feed")
    suspend fun getExploreFeed(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>

    @GET("community/settings/content_download")
    suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>>

    @GET("community/member")
    suspend fun getAllMembers(@QueryMap queries: HashMap<String, Any>): NetworkResponse<APIResponse<_GetAllMemberResponse_>>

    @GET("community/member/search")
    suspend fun searchMembers(
        @QueryMap queries: HashMap<String, Any>
    ): NetworkResponse<APIResponse<_SearchMembersResponse_>>

    @GET("community/configurations")
    suspend fun getCommunityConfiguration(): NetworkResponse<APIResponse<_GetCommunityConfiguration_>>
}