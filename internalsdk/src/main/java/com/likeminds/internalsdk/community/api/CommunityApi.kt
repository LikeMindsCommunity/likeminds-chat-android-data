package com.likeminds.internalsdk.community.api

import com.likeminds.internalsdk.community.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface CommunityApi {

    // api to get explore feed
    suspend fun getExploreFeed(
        request: _GetExploreFeedRequest_
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>

    // api to get content download settings
    suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>>
}