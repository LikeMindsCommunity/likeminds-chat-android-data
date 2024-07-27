package com.likeminds.likemindschat.helper

import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.helper.model._DecodeUrlRequest_
import com.likeminds.chatinternalsdk.helper.model._GetTaggingListRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.helper.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class HelperClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().helperComponent()?.inject(this)
    }

    private val helperApi by lazy {
        chatSDK.getHelperApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param decodeUrlRequest - client request model to decode a url and get og tags
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return LMResponse<DecodeUrlResponse> - DecodeUrlResponse for [decodeUrlRequest]
     */
    suspend fun decodeUrl(decodeUrlRequest: DecodeUrlRequest): LMResponse<DecodeUrlResponse> {
        // validates the client request
        RequestUtils.validate()
        validateDecodeUrlRequest(decodeUrlRequest)

        // builds internal request model
        val request = _DecodeUrlRequest_.Builder()
            .url(decodeUrlRequest.url)
            .build()
        // calls api and processes the response accordingly
        return when (val response = helperApi.decodeUrl(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                ModelConverter.convertDecodeUrlAPIResponse(response.body)
            }
        }
    }

    /**
     * validates [decodeUrlRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateDecodeUrlRequest(decodeUrlRequest: DecodeUrlRequest) {
        if (decodeUrlRequest.url.isEmpty()) {
            RequestUtils.throwException("url")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getTaggingListRequest - client request model to fetch tagging list
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<GetTaggingListResponse> - GetTaggingListResponse for [getTaggingListRequest]
     */
    suspend fun getTaggingList(getTaggingListRequest: GetTaggingListRequest): LMResponse<GetTaggingListResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetTaggingListRequest(getTaggingListRequest)

        //build internal request model
        val request = _GetTaggingListRequest_.Builder()
            .chatroomId(getTaggingListRequest.chatroomId)
            .page(getTaggingListRequest.page)
            .pageSize(getTaggingListRequest.pageSize)
            .searchName(getTaggingListRequest.searchName)
            .build()

        //call api and process the response accordingly
        return when (val response = helperApi.getTaggingList(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                ModelConverter.convertGetTaggingListAPIResponse(response.body)
            }
        }
    }

    /**
     * validates [getTaggingListRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetTaggingListRequest(getTaggingListRequest: GetTaggingListRequest) {
        if (getTaggingListRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Makes a DB call to check whether DB is empty or not
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<GetDBEmptyResponse> - GetDBEmptyResponse that returns whether DB is empty or not
     */
    fun getDBEmpty(): LMResponse<GetDBEmptyResponse> {
        // validates the client request
        RequestUtils.validate()

        return LMResponse(
            success = true,
            null,
            GetDBEmptyResponse(ChatDBUtil.isEmpty())
        )
    }
}