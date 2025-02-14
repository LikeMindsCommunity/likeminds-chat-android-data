package com.likeminds.chatinternalsdk.helper.api

import com.likeminds.chatinternalsdk.helper.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class HelperReceiver @Inject constructor(
    private val helperNetworkApi: HelperNetworkApi
) {

    companion object {
        private const val CHATROOM_ID_KEY = "chatroom_id"
        private const val PAGE_KEY = "page"
        private const val PAGE_SIZE_KEY = "page_size"
        private const val SEARCH_NAME_KEY = "search_name"
    }

    suspend fun decodeUrl(
        request: _DecodeUrlRequest_
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>> {
        return helperNetworkApi.decodeUrl(request.url)
    }

    suspend fun getTaggingList(
        request: _GetTaggingListRequest_
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[CHATROOM_ID_KEY] = request.chatroomId
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize
        if (!request.searchName.isNullOrEmpty()) {
            queries[SEARCH_NAME_KEY] = request.searchName
        }

        return helperNetworkApi.getTaggingList(queries)
    }

    suspend fun pushLogs(request: _PushLogsRequest_): NetworkResponse<APIResponse<Nothing>> {
        return helperNetworkApi.pushLogs(request)
    }

    fun insertLog(insertLogRequest: _InsertLogRequest_) {
        ChatDBUtil.write { realm ->

            val stackTrace = ROConverter.convertStackTrace(insertLogRequest.stackTrace)
            realm.insertOrUpdate(stackTrace)

            val sdkMeta = ROConverter.convertSDKMeta(insertLogRequest.sdkMeta)

            if (sdkMeta != null) {
                realm.insertOrUpdate(sdkMeta)
            }

            realm.insertOrUpdate(
                ROConverter.convertLog(
                    insertLogRequest.timestamp,
                    stackTrace,
                    sdkMeta,
                    insertLogRequest.severity
                )
            )
        }
    }

    fun getLogs(realm: Realm): RealmResults<LMLogRO> {
        return realm.where(LMLogRO::class.java)
            .findAll()
    }

    fun clearLogs(clearLogsRequest: _ClearLogsRequest_) {
        ChatDBUtil.write { realm ->
            val logs = realm.where(LMLogRO::class.java)
                .lessThan(DbKey.TIMESTAMP, clearLogsRequest.timestamp)
                .findAll()

            logs.deleteAllFromRealm()
        }
    }
}