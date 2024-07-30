package com.likeminds.likemindschat.homefeed

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.sync.SyncSDK
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeChatroomListener
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class HomeFeedClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().homeFeedComponent()?.inject(this)
    }

    private val homeFeedApi by lazy {
        chatSDK.getHomeFeedApi()
    }

    private val homeFeedDB by lazy {
        chatSDK.getHomeFeedDb()
    }

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
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @param context - Context required to run workers
     * @return Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? -
     * Worker result
     */
    fun syncChatrooms(
        context: Context
    ): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        //validates the client request
        RequestUtils.validate()

        //check whether db is empty or not
        val isFirstTime = ChatDBUtil.isEmpty()

        /**
         * if empty start first time chatroom worker else reopen
         */
        return if (isFirstTime) {
            SyncSDK.startFirstHomeFeedSync(context)
        } else {
            SyncSDK.startReopenSyncForHomeFeed(context)
        }
    }

    /**
     * Start the Sync workers and get db query for chatrooms for home feed
     *
     * @param listener: [HomeChatroomListener] to get object of the chatrooms as per requirements
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     */
    fun getChatrooms(listener: HomeChatroomListener): Observable<Unit>? {
        //validates the client request
        RequestUtils.validate()

        //create realm object
        val realm = Realm.getDefaultInstance()

        //[Flow] of the [CollectionChange] of the Chatrooms
        return homeFeedDB.getChatrooms(realm)?.map {
            listener.onChange(it.collection, it.changeset!!)
        }?.doOnDispose {
            listener.clear()
        }?.doOnTerminate {
            listener.clear()
        }
    }

    /**
     * Observes the live home feed and gives real-time updates
     *
     * @param context: Context of the Activity/Fragment
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     */
    fun observeLiveHomeFeed(context: Context) {
        RequestUtils.validate()

        val communityId = chatSDK.sdkPreferences.getCommunityId() ?: ""
        val firebaseApp = FirebaseApp.getInstance("lm-secondary")
        databaseReference = FirebaseDatabase.getInstance(firebaseApp).reference
            .child("community")
            .child(communityId)

        valueChangeListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
                Log.d(LMChatSDK.LOG_TAG, "cancelled: ${error.message}")
            }
        }

        databaseReference?.addValueEventListener(valueChangeListener)
    }

    /**
     * Removes the live home feed observer
     */
    fun removeLiveHomeFeedListener() {
        if (this::valueChangeListener.isInitialized) {
            databaseReference?.removeEventListener(valueChangeListener)
        }
    }
}