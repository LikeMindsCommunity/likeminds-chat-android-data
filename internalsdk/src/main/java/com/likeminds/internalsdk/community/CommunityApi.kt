package com.likeminds.internalsdk.community

import com.likeminds.internalsdk.community.model._GetExploreFeedRequest_
import com.likeminds.internalsdk.community.model._GetExploreFeedResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface CommunityApi {

    // api to get explore feed
    suspend fun getExploreFeed(
        request: _GetExploreFeedRequest_
    ): NetworkResponse<APIResponse<_GetExploreFeedResponse_>>
}