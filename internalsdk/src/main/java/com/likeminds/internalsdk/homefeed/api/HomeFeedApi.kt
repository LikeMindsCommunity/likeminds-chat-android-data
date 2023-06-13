package com.likeminds.internalsdk.homefeed.api

import com.likeminds.internalsdk.homefeed.model._ConfigResponse_
import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface HomeFeedApi {

    //api to get explore tab count
    suspend fun getExploreTabCount(): NetworkResponse<APIResponse<_GetExploreTabCountResponse_>>

    //api to get config
    suspend fun getConfig(): NetworkResponse<APIResponse<_ConfigResponse_>>
}