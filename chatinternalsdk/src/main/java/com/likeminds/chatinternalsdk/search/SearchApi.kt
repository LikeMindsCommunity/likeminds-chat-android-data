package com.likeminds.chatinternalsdk.search

import com.likeminds.chatinternalsdk.search.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

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