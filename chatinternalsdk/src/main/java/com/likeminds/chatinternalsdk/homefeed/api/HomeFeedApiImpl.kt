package com.likeminds.chatinternalsdk.homefeed.api

import com.likeminds.chatinternalsdk.homefeed.HomeFeedReceiver
import com.likeminds.chatinternalsdk.homefeed.model._ConfigResponse_
import com.likeminds.chatinternalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class HomeFeedApiImpl @Inject constructor(private val homeFeedReceiver: HomeFeedReceiver) :
    HomeFeedApi {

    override suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>> {
        return homeFeedReceiver.getExploreTabCount()
    }

    override suspend fun getConfig(): NetworkResponse<APIResponse<_ConfigResponse_>> {
        return homeFeedReceiver.getConfig()
    }
}