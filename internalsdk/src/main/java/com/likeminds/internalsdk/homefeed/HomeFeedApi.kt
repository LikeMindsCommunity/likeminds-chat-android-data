package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface HomeFeedApi {

    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>>
}