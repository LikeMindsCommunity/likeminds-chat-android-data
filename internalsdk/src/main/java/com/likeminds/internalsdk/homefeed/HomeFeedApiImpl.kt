package com.likeminds.internalsdk.homefeed

import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HomeFeedApiImpl @Inject constructor(private val homeFeedReceiver: HomeFeedReceiver) :
    HomeFeedApi {

    override suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>> {
        return homeFeedReceiver.getExploreTabCount()
    }
}