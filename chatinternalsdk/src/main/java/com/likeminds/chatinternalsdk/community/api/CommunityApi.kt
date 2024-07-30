package com.likeminds.chatinternalsdk.community.api

import com.likeminds.chatinternalsdk.community.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface CommunityApi {

    // api to get explore feed
    suspend fun getExploreFeed(
        request: _GetExploreFeedRequest_
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>

    // api to get content download settings
    suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>>

    // api to get all the members in the community
    suspend fun getAllMember(request: _GetAllMemberRequest_): NetworkResponse<APIResponse<_GetAllMemberResponse_>>

    // api to search members in the community
    suspend fun searchMembers(request: _SearchMembersRequest_): NetworkResponse<APIResponse<_SearchMembersResponse_>>

    //api to get all community configuration
    suspend fun getCommunityConfiguration(): NetworkResponse<APIResponse<_GetCommunityConfiguration_>>
}