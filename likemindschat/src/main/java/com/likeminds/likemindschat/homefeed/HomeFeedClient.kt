package com.likeminds.likemindschat.homefeed

import android.content.Context
import android.util.Log
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.ChatroomRO
import com.likeminds.internalsdk.homefeed.util._HomeFeedChangeListener_
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.RealmResults
import javax.inject.Inject

class HomeFeedClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().homeFeedComponent()?.inject(this)
    }

    private val homeFeedApi by lazy {
        groupChatSDK.getHomeFeedApi()
    }

    private val homeFeedDB by lazy {
        groupChatSDK.getHomeFeedDb()
    }

    /**
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return GetExploreTabCountResponse - GetExploreTabCountResponse model for getExploreTabCount
     */
    suspend fun getExploreTabCount(): LMResponse<GetExploreTabCountResponse> {
        //validates the client request
        RequestUtils.validate()
        // calls api and processes the response accordingly
        return when (val response = homeFeedApi.getExploreTabCount()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetExploreTabCountAPIResponse(body)
            }
        }
    }

    /**
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return ConfigResponse - ConfigResponse model for getConfig
     */
    suspend fun getConfig(): LMResponse<ConfigResponse> {
        //validates the client request
        RequestUtils.validate()
        // calls api and processes the response accordingly
        return when (val response = homeFeedApi.getConfig()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertConfigAPIResponse(body)
            }
        }
    }

    fun getChatrooms(context: Context, listener: HomeFeedChangeListener) {
        //validates the client request
        RequestUtils.validate()

        val isFirstTime = ChatDBUtil.isEmpty()
        if (isFirstTime) {
            Log.d("PUI", "first time")
            SyncSDK.startFirstHomeFeedSync(context)
        } else {
            Log.d("PUI", "reopen")
            SyncSDK.startReopenSyncForHomeFeed(context)
        }

        val queryListener = object : _HomeFeedChangeListener_() {
            override fun initial(chatrooms: RealmResults<ChatroomRO>) {
                listener.initial(listOf()) //todo
                Log.d("PUI", "initial chatroom: ${chatrooms.size}")
            }

            override fun onChanged(
                removedIndex: List<Int>,
                inserted: List<Pair<Int, ChatroomRO>>,
                changed: List<Pair<Int, ChatroomRO>>
            ) {
                Log.d("PUI", "inserted chatroom: ${inserted.size}")
                Log.d("PUI", "changed chatroom: ${changed.size}")
                listener.onChanged(removedIndex, listOf(), listOf()) //todo
            }

            override fun onError(throwable: Throwable) {
                Log.d("PUI", "error: ${throwable.message}")
                listener.onError(throwable)
            }

        }

        homeFeedDB.getChatrooms(queryListener)
    }
}