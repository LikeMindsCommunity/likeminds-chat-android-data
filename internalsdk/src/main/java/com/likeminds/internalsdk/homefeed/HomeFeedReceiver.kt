package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model.GetExploreTabCountResponse
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HomeFeedReceiver @Inject constructor(private val homeFeedNetworkApi: HomeFeedNetworkApi) {

    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<GetExploreTabCountResponse>> {
        return homeFeedNetworkApi.getExploreTabCount()
    }
}