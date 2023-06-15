package com.likeminds.internalsdk.search

import com.likeminds.internalsdk.search.model._SearchChatroomRequest_
import com.likeminds.internalsdk.search.model._SearchChatroomResponse_
import com.likeminds.internalsdk.search.model._SearchConversationRequest_
import com.likeminds.internalsdk.search.model._SearchConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface SearchApi {

    // api to search a chatroom
    suspend fun searchChatroom(
        request: _SearchChatroomRequest_
    ): NetworkResponse<APIResponse<_SearchChatroomResponse_>>

    // api to search a conversation
    suspend fun searchConversation(
        request: _SearchConversationRequest_
    ): NetworkResponse<APIResponse<_SearchConversationResponse_>>
}