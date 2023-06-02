package com.likeminds.internalsdk.search

import com.likeminds.internalsdk.search.model._SearchChatroomResponse_
import com.likeminds.internalsdk.search.model._SearchConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SearchNetworkApi {

    @GET("chatroom/search")
    suspend fun searchChatroom(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SearchChatroomResponse_>>

    @GET("conversation/search")
    suspend fun searchConversation(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SearchConversationResponse_>>
}