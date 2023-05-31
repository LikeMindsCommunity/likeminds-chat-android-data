package com.likeminds.internalsdk.community

import com.likeminds.internalsdk.community.model._GetExploreFeedRequest_
import com.likeminds.internalsdk.community.model._GetExploreFeedResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class CommunityApiImpl @Inject constructor(
    private val communityReceiver: CommunityReceiver
) : CommunityApi {

    override suspend fun getExploreFeed(request: _GetExploreFeedRequest_): NetworkResponse<APIResponse<_GetExploreFeedResponse_>> {
        return communityReceiver.getExploreFeed(request)
    }
}