package com.likeminds.likemindschat.helper

import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.helper.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.helper.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import javax.inject.Inject

class HelperClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().helperComponent()?.inject(this)
    }

    private val helperApi by lazy {
        chatSDK.getHelperApi()
    }

    private val helperDB by lazy {
        chatSDK.getHelperDB()
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

    /**
     * Converts client request model to internal model and calls the api
     * @param pushLogsRequest - client request model to push logs
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - PushLogsResponse for [pushLogsRequest]
     */
    suspend fun pushLogs(pushLogsRequest: PushLogsRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validatePushLogsRequest(pushLogsRequest)

        val request = _PushLogsRequest_.Builder()
            .logs(ModelConverter.createLogs(pushLogsRequest.logs))
            .build()

        //call api and process the response accordingly
        return when (val response = helperApi.pushLogs(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [pushLogsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePushLogsRequest(pushLogsRequest: PushLogsRequest) {
        if (pushLogsRequest.logs.isEmpty()) {
            RequestUtils.throwException("logs")
        }
    }

    /**
     * Converts client request model to internal model and saves log in DB
     * @param insertLogRequest - client request model to save log in DB
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     */
    fun insertLog(insertLogRequest: InsertLogRequest) {
        // validates the client request
        RequestUtils.validate()
        validateInsertLogRequest(insertLogRequest)

        val request = _InsertLogRequest_.Builder()
            .timestamp(insertLogRequest.timestamp)
            .stackTrace(ModelConverter.createStackTrace(insertLogRequest.stackTrace))
            .sdkMeta(ModelConverter.createSDKMeta(insertLogRequest.sdkMeta))
            .severity(insertLogRequest.severity?.severityName)
            .build()

        helperDB.insertLog(request)
    }

    /**
     * validates [insertLogRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateInsertLogRequest(insertLogRequest: InsertLogRequest) {
        if (insertLogRequest.timestamp == 0L) {
            RequestUtils.throwException("timestamp")
        }

        if (insertLogRequest.stackTrace.exception.isEmpty()) {
            RequestUtils.throwException("exception")
        }

        if (insertLogRequest.stackTrace.trace.isEmpty()) {
            RequestUtils.throwException("trace")
        }
    }

    /**
     * Converts client request model to internal model and  gets all the logs from DB
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return LMResponse<GetLogsResponse> - GetLogsResponse
     */
    fun getLogs(): LMResponse<GetLogsResponse> {
        // validates the client request
        RequestUtils.validate()

        val realm = Realm.getDefaultInstance()

        val logs = helperDB.getLogs(realm)
        val response = ModelConverter.convertGetLogsResponse(logs)

        realm.close()

        return LMResponse(
            success = true,
            data = response
        )
    }

    /**
     * Converts client request model to internal model and clears the logs from DB as per the clearLogsRequest
     * @param clearLogsRequest - client request model to clear logs from DB
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     */
    fun clearLogs(clearLogsRequest: ClearLogsRequest) {
        // validates the client request
        RequestUtils.validate()
        validateClearLogsRequest(clearLogsRequest)

        val request = _ClearLogsRequest_.Builder()
            .timestamp(clearLogsRequest.timestamp)
            .build()

        helperDB.clearLogs(request)
    }

    /**
     * validates [clearLogsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateClearLogsRequest(clearLogsRequest: ClearLogsRequest) {
        if (clearLogsRequest.timestamp == 0L) {
            RequestUtils.throwException("timestamp")
        }
    }
}