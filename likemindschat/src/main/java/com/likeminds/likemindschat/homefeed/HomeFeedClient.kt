package com.likeminds.likemindschat.homefeed

import android.content.Context
import android.util.Log
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.ChatroomRO
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.*
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

    private var collection: RealmResults<ChatroomRO>? = null

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

    suspend fun getChatrooms(context: Context, listener: HomeFeedChangeListener) {
        //validates the client request
        RequestUtils.validate()

        val realm = Realm.getDefaultInstance()

        val isFirstTime = ChatDBUtil.isEmpty()
        if (isFirstTime) {
            Log.d("PUI", "first time")
            SyncSDK.startFirstHomeFeedSync(context)
        } else {
            Log.d("PUI", "reopen")
            SyncSDK.startReopenSyncForHomeFeed(context)
        }

        val flowOfChatrooms = homeFeedDB.getChatrooms(realm)

        flowOfChatrooms.collect { collectionChange ->
            Log.d("PUI", "collect: $collectionChange")
            val changeSet = collectionChange.changeset
            val result = collectionChange.collection
            when (collectionChange.changeset?.state) {
                OrderedCollectionChangeSet.State.INITIAL -> {
                    result?.let {
                        Log.d("PUI", "INITIAL: ${result.size}")
                        collection = it
                        val chatrooms = it.mapNotNull { chatroomRO ->
                            ModelConverter.convertChatroomRO(chatroomRO)
                        }
                        listener.initial(chatrooms)
                    }
                }

                OrderedCollectionChangeSet.State.UPDATE -> {
                    changeSet?.let {
                        collection = result
                        val insertions = getIndexedChatrooms(it.insertions)
                        val changes = getIndexedChatrooms(it.changes)
                        Log.d(
                            "PUI", """
                            UPDATE:
                            insertion: ${insertions.size}
                            changes: ${changes.size}
                        """.trimIndent()
                        )
                        listener.onChanged(it.deletions.reversed(), insertions, changes)
                    }
                }

                OrderedCollectionChangeSet.State.ERROR -> {
                    Log.d("PUI", "ERROR: ${changeSet?.error?.message}")
                    listener.onError(changeSet?.error ?: Throwable("Something went wrong"))
                }

                null -> {
                    Log.d("PUI", "null")
                }
            }
        }
    }

    private fun getIndexedChatrooms(indexArray: IntArray): List<Pair<Int, Chatroom>> {
        return indexArray.toList().mapNotNull { index ->
            val chatroomRO = collection?.get(index)
            val chatroom = ModelConverter.convertChatroomRO(chatroomRO)
            return@mapNotNull if (chatroom != null) {
                Pair(index, chatroom)
            } else {
                null
            }
        }
    }
}