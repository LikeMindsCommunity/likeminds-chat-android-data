package com.likeminds.internalsdk.community.api

import com.likeminds.internalsdk.community.CommunityReceiver
import com.likeminds.internalsdk.community.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class CommunityApiImpl @Inject constructor(
    private val communityReceiver: CommunityReceiver
) : CommunityApi {

    override suspend fun getExploreFeed(request: _GetExploreFeedRequest_): NetworkResponse<APIResponse<_GetExploreFeedResponse_>> {
        return communityReceiver.getExploreFeed(request)
    }

    override suspend fun getContentDownloadSettings(): NetworkResponse<APIResponse<_GetContentDownloadSettingsResponse_>> {
        return communityReceiver.getContentDownloadSettings()
    }

    override suspend fun getAllMember(request: _GetAllMemberRequest_): NetworkResponse<APIResponse<_GetAllMemberResponse_>> {
        return communityReceiver.getAllMember(request)
    }

    override suspend fun searchMembers(request: _SearchMembersRequest_): NetworkResponse<APIResponse<_SearchMembersResponse_>> {
        return communityReceiver.searchMembers(request)
    }
}