package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model.GetExploreTabCountResponse
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface HomeFeedApi {
    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<GetExploreTabCountResponse>>
}