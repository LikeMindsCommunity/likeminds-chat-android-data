package com.likeminds.likemindschat.moderation

import com.likeminds.internalsdk.moderation.model._GetReportTagsRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.moderation.model.GetReportTagsRequest
import com.likeminds.likemindschat.moderation.model.GetReportTagsResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class ModerationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().moderationComponent()?.inject(this)
    }

    private val moderationApi by lazy {
        groupChatSDK.getModerationApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getReportTagsRequest - client request model to fetch report tags
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return GetReportTagsResponse - GetReportTagsResponse model for getReportTagsRequest
     */
    suspend fun getReportTags(getReportTagsRequest: GetReportTagsRequest): LMResponse<GetReportTagsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateReportTagsRequest(getReportTagsRequest)

        // builds internal request model
        val request = _GetReportTagsRequest_.Builder()
            .type(getReportTagsRequest.type)
            .build()

        // calls api and processes the response accordingly
        return when (val response = moderationApi.getReportTags(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }
            is NetworkResponse.Success -> {
                ModelConverter.convertGetReportTagsAPIResponse(response.body)
            }
        }
    }

    /**
     * validates [reportTagsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateReportTagsRequest(reportTagsRequest: GetReportTagsRequest) {
        if (reportTagsRequest.type == -1) {
            RequestUtils.throwException("type")
        }
    }
}