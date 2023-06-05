package com.likeminds.internalsdk.search

import com.likeminds.internalsdk.search.model._SearchChatroomRequest_
import com.likeminds.internalsdk.search.model._SearchChatroomResponse_
import com.likeminds.internalsdk.search.model._SearchConversationRequest_
import com.likeminds.internalsdk.search.model._SearchConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class SearchApiImpl @Inject constructor(
    private val searchReceiver: SearchReceiver
) : SearchApi {

    override suspend fun searchChatroom(
        request: _SearchChatroomRequest_
    ): NetworkResponse<APIResponse<_SearchChatroomResponse_>> {
        return searchReceiver.searchChatroom(request)
    }

    override suspend fun searchConversation(
        request: _SearchConversationRequest_
    ): NetworkResponse<APIResponse<_SearchConversationResponse_>> {
        return searchReceiver.searchConversation(request)
    }
}