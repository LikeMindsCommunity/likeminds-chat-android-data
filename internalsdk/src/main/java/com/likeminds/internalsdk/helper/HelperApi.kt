package com.likeminds.internalsdk.helper

import com.likeminds.internalsdk.helper.model._DecodeUrlRequest_
import com.likeminds.internalsdk.helper.model._DecodeUrlResponse_
import com.likeminds.internalsdk.helper.model._GetTaggingListRequest_
import com.likeminds.internalsdk.helper.model._GetTaggingListResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface HelperApi {

    // api to fetch ogTags of url
    suspend fun decodeUrl(
        request: _DecodeUrlRequest_
    ): NetworkResponse<APIResponse<_DecodeUrlResponse_>>

    //api to fetch taggingList
    suspend fun getTaggingList(
        request: _GetTaggingListRequest_
    ): NetworkResponse<APIResponse<_GetTaggingListResponse_>>
}