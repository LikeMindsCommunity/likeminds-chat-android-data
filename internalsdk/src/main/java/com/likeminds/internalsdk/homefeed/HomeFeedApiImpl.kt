package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model.GetExploreTabCountResponse
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HomeFeedApiImpl @Inject constructor(private val homeFeedReceiver: HomeFeedReceiver) :
    HomeFeedApi {

    override suspend fun getExploreTabCount(): NetworkResponse<APIResponse<GetExploreTabCountResponse>> {
        return homeFeedReceiver.getExploreTabCount()
    }
}