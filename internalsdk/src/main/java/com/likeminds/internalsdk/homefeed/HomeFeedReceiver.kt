package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model._ConfigResponse_
import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HomeFeedReceiver @Inject constructor(private val homeFeedNetworkApi: HomeFeedNetworkApi) {

    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>> {
        return homeFeedNetworkApi.getExploreTabCount()
    }

    //todo remove ingestYourCommunities
    suspend fun getConfig(): NetworkResponse<APIResponse<_ConfigResponse_>> {
        return homeFeedNetworkApi.getConfig(true)
    }
}