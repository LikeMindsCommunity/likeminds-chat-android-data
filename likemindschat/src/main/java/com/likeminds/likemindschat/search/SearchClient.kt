package com.likeminds.likemindschat.search

import com.likeminds.chatinternalsdk.search.model._SearchChatroomRequest_
import com.likeminds.chatinternalsdk.search.model._SearchConversationRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.search.model.*
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class SearchClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().searchComponent()?.inject(this)
    }

    private val searchApi by lazy {
        chatSDK.getSearchApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param searchChatroomRequest - client request model to search a chatroom
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return SearchChatroomResponse - SearchChatroomResponse model for searchChatroomRequest
     */
    suspend fun searchChatroom(searchChatroomRequest: SearchChatroomRequest): LMResponse<SearchChatroomResponse> {
        // validates the client request
        RequestUtils.validate()
        validateSearchChatroomRequest(searchChatroomRequest)

        // builds internal request model
        val request = _SearchChatroomRequest_.Builder()
            .search(searchChatroomRequest.search)
            .searchType(searchChatroomRequest.searchType)
            .followStatus(searchChatroomRequest.followStatus)
            .page(searchChatroomRequest.page)
            .pageSize(searchChatroomRequest.pageSize)
            .build()

        // calls api and processes the response accordingly
        return when (val response = searchApi.searchChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertSearchChatroomAPIResponse(body)
            }
        }
    }

    /**
     * validates [searchChatroomRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSearchChatroomRequest(searchChatroomRequest: SearchChatroomRequest) {
        if (searchChatroomRequest.search.isEmpty()) {
            RequestUtils.throwException("search")
        }

        if (searchChatroomRequest.searchType.isEmpty()) {
            RequestUtils.throwException("searchType")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param searchConversationRequest - client request model to search a conversation
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return SearchConversationResponse - SearchConversationResponse model for searchConversationRequest
     */
    suspend fun searchConversation(searchConversationRequest: SearchConversationRequest): LMResponse<SearchConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateSearchConversationRequest(searchConversationRequest)

        // builds internal request model
        val request = _SearchConversationRequest_.Builder()
            .search(searchConversationRequest.search)
            .followStatus(searchConversationRequest.followStatus)
            .chatroomId(searchConversationRequest.chatroomId)
            .page(searchConversationRequest.page)
            .pageSize(searchConversationRequest.pageSize)
            .build()

        // calls api and processes the response accordingly
        return when (val response = searchApi.searchConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertSearchConversationAPIResponse(body)
            }
        }
    }

    /**
     * validates [searchConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSearchConversationRequest(searchConversationRequest: SearchConversationRequest) {
        if (searchConversationRequest.search.isEmpty()) {
            RequestUtils.throwException("search")
        }
    }
}