package com.likeminds.likemindschat.community

import com.likeminds.chatinternalsdk.community.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.community.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class CommunityClient @Inject constructor() : BaseClient() {
    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().communityComponent()?.inject(this)
    }

    private val communityApi by lazy {
        chatSDK.getCommunityApi()
    }

    private val communityDB by lazy {
        chatSDK.getCommunityDB()
    }

    private val sdkPreferences by lazy {
        chatSDK.getSDKPreferences()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getExploreFeedRequest - client request model to get explore feed
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetExploreFeedResponse - GetExploreFeedResponse model for getExploreFeedRequest
     */
    suspend fun getExploreFeed(getExploreFeedRequest: GetExploreFeedRequest): LMResponse<GetExploreFeedResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetExploreFeedRequest(getExploreFeedRequest)

        // builds internal request model
        val request =
            _GetExploreFeedRequest_.Builder()
                .orderType(getExploreFeedRequest.orderType)
                .isPinned(getExploreFeedRequest.isPinned)
                .page(getExploreFeedRequest.page)
                .build()

        // calls api and processes the response accordingly
        return when (val response = communityApi.getExploreFeed(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetExploreFeedAPIResponse(body)
            }
        }
    }

    /**
     * validates [getExploreFeedRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetExploreFeedRequest(getExploreFeedRequest: GetExploreFeedRequest) {
        if (getExploreFeedRequest.orderType == -1) {
            RequestUtils.throwException("orderType")
        }
    }

    /**
     * Calls the api to get content download settings
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return GetContentDownloadSettingsResponse - GetContentDownloadSettingsResponse model for getContentDownloadSettings
     */
    suspend fun getContentDownloadSettings(): LMResponse<GetContentDownloadSettingsResponse> {
        // validates the client request
        RequestUtils.validate()

        // calls api and processes the response accordingly
        return when (val response = communityApi.getContentDownloadSettings()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                body.data?.let { it ->
                    val communityId = sdkPreferences.getCommunityId() ?: ""
                    val settings = it.settings
                    val optionsDownloadable = settings.filter { it.enabled }
                    val contentTypes = optionsDownloadable.map { options ->
                        options.downloadSettingType
                    }
                    communityDB.updateContentDownloadSettings(contentTypes, communityId)
                }

                ModelConverter.convertGetContentDownloadSettingsAPIResponse(body)
            }
        }
    }

    /**
     * Observes the community stored in DB
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return Flowable<Optional<Community>> - Flow of community
     */
    fun observeCommunity(): Observable<Community> {
        // validates the client request
        RequestUtils.validate()

        val realm = Realm.getDefaultInstance()
        val communityId = sdkPreferences.getCommunityId() ?: ""
        val observable = communityDB.observeCommunity(
            realm,
            communityId
        )
        return observable.map {
            ModelConverter.convertCommunityRO(it)
        }
    }

    /**
     * Calls the api to get all members in community
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return GetAllMemberResponse - GetAllMemberResponse model for getAllMemberRequest
     */
    suspend fun getAllMember(getAllMemberRequest: GetAllMemberRequest): LMResponse<GetAllMemberResponse> {
        // validates the client request
        RequestUtils.validate()

        val filterMemberRoles = getAllMemberRequest.filterMemberRoles.map { role ->
            role.value
        }

        // builds internal request model
        val request = _GetAllMemberRequest_.Builder()
            .page(getAllMemberRequest.page)
            .filterMemberRoles(filterMemberRoles)
            .excludeSelfUser(getAllMemberRequest.excludeSelfUser)
            .build()

        return when (val response = communityApi.getAllMember(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetAllMemberResponse(body)
            }
        }
    }

    /**
     * Calls the api to search members in community
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return SearchMembersResponse - SearchMembersResponse model for searchMembersRequest
     */
    suspend fun searchMember(searchMembersRequest: SearchMembersRequest): LMResponse<SearchMembersResponse> {
        // validates the client request
        RequestUtils.validate()
        validateSearchMemberRequest(searchMembersRequest)

        // builds internal request model
        val request = _SearchMembersRequest_.Builder()
            .search(searchMembersRequest.search)
            .searchType(searchMembersRequest.searchType.value)
            .page(searchMembersRequest.page)
            .pageSize(searchMembersRequest.pageSize)
            .excludeSelfUser(searchMembersRequest.excludeSelfUser)
            .memberStates(searchMembersRequest.memberStates)
            .build()

        return when (val response = communityApi.searchMembers(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertSearchMembersResponse(body)
            }
        }
    }

    /**
     * validates [searchMembersRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSearchMemberRequest(searchMembersRequest: SearchMembersRequest) {
        if (searchMembersRequest.search.isEmpty()) {
            RequestUtils.throwException("search")
        }

        if (searchMembersRequest.searchType == MemberSearchType.EMPTY) {
            RequestUtils.throwException("searchType")
        }
    }

    /****
     * Calls the community configuration API and
     * convert internal response model to exposed response model
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return [GetCommunityConfigurationsResponse] - [GetCommunityConfigurationsResponse] model
     */
    suspend fun getCommunityConfigurations(): LMResponse<GetCommunityConfigurationsResponse> {
        // validates the client request
        RequestUtils.validate()

        return when (val response = communityApi.getCommunityConfiguration()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                ModelConverter.convertGetCommunityConfigurationAPIResponse(response.body)
            }
        }
    }

    /**
     * Calls the api to get list of AI chatbots
     * @param getAIChatbotsRequest -  client request model to get AI chatbots
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return GetAIChatbotsResponse - GetAIChatbotsResponse model for getAIChatbotsResponse
     */
    suspend fun getAIChatbots(getAIChatbotsRequest: GetAIChatbotsRequest): LMResponse<GetAIChatbotsResponse> {
        // validates the client request
        RequestUtils.validate()

        val request = _GetAIChatbotsRequest_.Builder()
            .page(getAIChatbotsRequest.page)
            .pageSize(getAIChatbotsRequest.pageSize)
            .build()

        return when (val response = communityApi.getAIChatbots(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                ModelConverter.convertGetAIChatbotsResponse(response.body)
            }
        }
    }
}