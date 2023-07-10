package com.likeminds.likemindschat.homefeed

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.ChatroomRO
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.OrderedCollectionChangeSet
import io.realm.Realm
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

    private var collection: RealmResults<ChatroomRO>? = null

    private lateinit var valueChangeListener: ValueEventListener
    private var databaseReference: DatabaseReference? = null

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

    /**
     * Start the Sync workers and get db query for chatrooms for home feed
     *
     * @param context: Context of the Activity/Fragment
     * @param listener: [HomeFeedChangeListener] to get object of the chatrooms as per requirements
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     */
    suspend fun getChatrooms(
        context: Context,
        listener: HomeFeedChangeListener
    ) {
        //validates the client request
        RequestUtils.validate()

        //create realm object
        val realm = Realm.getDefaultInstance()

        //check whether db is empty or not
        val isFirstTime = ChatDBUtil.isEmpty()

        /**
         * if empty start first time chatroom worker else reopen
         */
        if (isFirstTime) {
            SyncSDK.startFirstHomeFeedSync(context)
        } else {
            SyncSDK.startReopenSyncForHomeFeed(context)
        }

        observeLiveHomeFeed(context)

        //[Flow] of the [CollectionChange] of the Chatrooms
        val flowOfChatrooms = homeFeedDB.getChatrooms(realm)

        //collect the flow
        flowOfChatrooms.collect { collectionChange ->
            val changeSet = collectionChange.changeset
            val result = collectionChange.collection
            when (collectionChange.changeset?.state) {
                //Initial chatrooms
                OrderedCollectionChangeSet.State.INITIAL -> {
                    result?.let {
                        collection = it
                        val chatrooms = it.mapNotNull { chatroomRO ->
                            ModelConverter.convertChatroomRO(chatroomRO)
                        }
                        listener.initialChatrooms(chatrooms)
                    }
                }

                //Updated chatrooms i.e. inserted or changed
                OrderedCollectionChangeSet.State.UPDATE -> {
                    changeSet?.let {
                        collection = result
                        val insertions = getIndexedChatrooms(it.insertions)
                        val changes = getIndexedChatrooms(it.changes)
                        listener.changedChatrooms(it.deletions.reversed(), insertions, changes)
                    }
                }

                //if any error
                OrderedCollectionChangeSet.State.ERROR -> {
                    listener.error(changeSet?.error ?: Throwable("Something went wrong"))
                }

                null -> {
                    Log.d(LMChatClient.TAG, "first emit")
                }
            }
        }
    }

    /**
     * returns list of [Pair] of [Int] and [Chatroom] as per indexes received in
     * @param indexArray
     */
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

    private fun observeLiveHomeFeed(context: Context) {
        val communityId = groupChatSDK.sdkPreferences.getCommunityId() ?: ""
        Log.d("SDK", "observeLiveHomeFeed: $communityId")
        val firebaseApp = FirebaseApp.getInstance("secondary")
        databaseReference = FirebaseDatabase.getInstance(firebaseApp).reference
            .child("community")
            .child(communityId)

        valueChangeListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //validates the client request
                RequestUtils.validate()

                //check whether db is empty or not
                val isFirstTime = ChatDBUtil.isEmpty()

                /**
                 * if empty start first time chatroom worker else reopen
                 */
                if (isFirstTime) {
                    SyncSDK.startFirstHomeFeedSync(context)
                } else {
                    SyncSDK.startReopenSyncForHomeFeed(context)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(GroupChatSDK.LOG_TAG, "cancelled: ${error.message}")
            }
        }

        databaseReference?.addValueEventListener(valueChangeListener)
    }
}