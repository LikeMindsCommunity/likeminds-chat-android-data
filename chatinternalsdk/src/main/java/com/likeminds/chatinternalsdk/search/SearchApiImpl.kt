package com.likeminds.chatinternalsdk.search

import com.likeminds.chatinternalsdk.search.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
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