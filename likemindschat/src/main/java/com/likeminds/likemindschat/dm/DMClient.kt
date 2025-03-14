package com.likeminds.likemindschat.dm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.chatroom.model.TYPE_DIRECT_MESSAGE
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.dm.model.*
import com.likeminds.chatinternalsdk.sync.SyncSDK
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.chatroom.model.ChatRequestState
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.dm.model.*
import com.likeminds.likemindschat.homefeed.model.ChatroomEntity
import com.likeminds.likemindschat.homefeed.util.HomeChatroomListener
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.MemberBlockState
import com.likeminds.likemindschat.util.RequestUtils
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class DMClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().dmSubComponent()?.inject(this)
    }

    private val dmApi by lazy {
        chatSDK.getDMApi()
    }

    private val chatroomDB by lazy {
        chatSDK.getChatroomDb()
    }

    private val userDB by lazy {
        chatSDK.getUserDb()
    }

    private val conversationDB by lazy {
        chatSDK.getConversationDB()
    }

    private val syncPreferences by lazy {
        chatSDK.getSyncPreference()
    }

    private val sdkPreferences by lazy {
        chatSDK.getSDKPreferences()
    }

    private val userPreferences by lazy {
        chatSDK.getUserPreference()
    }

    private lateinit var valueChangeListener: ValueEventListener
    private var databaseReference: DatabaseReference? = null

    /**
     * Converts client request model to internal model and calls the api
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return CheckDMTabResponse - CheckDMTabResponse model
     */
    suspend fun checkDMTab(): LMResponse<CheckDMTabResponse> {
        // validates the client request
        RequestUtils.validate()

        return when (val response = dmApi.checkDMTab()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMTabResponse(body)
            }
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param sendDMRequest - client request model to send a dm request
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return SendDMResponse - SendDMResponse model for sendDMRequest
     */
    suspend fun sendDMRequest(sendDMRequest: SendDMRequest): LMResponse<SendDMResponse> {
        // validates the client request
        RequestUtils.validate()
        validateSendDMRequest(sendDMRequest)

        // builds internal request model
        val request = _SendDMRequest_.Builder()
            .chatroomId(sendDMRequest.chatroomId)
            .chatRequestState(sendDMRequest.chatRequestState.value ?: 0)
            .text(sendDMRequest.text)
            .build()

        // calls api and processes the response accordingly
        return when (val response = dmApi.sendDMRequest(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                val chatRequestState = sendDMRequest.chatRequestState

                val realm = Realm.getDefaultInstance()
                val userRO = userDB.getUser(realm)

                val chatRequestedById =
                    if (chatRequestState.value == ChatRequestState.INITIATED.value) {
                        userRO?.id
                    } else {
                        null
                    }

                // updates chat request state in local DB
                chatroomDB.updateChatRequestState(
                    sendDMRequest.chatroomId,
                    chatRequestState.value,
                    chatRequestedById
                )

                // save the conversation in DB
                val conversation = body.data?.conversation
                conversation?.let { finalConversation ->
                    conversationDB.saveNewConversation(realm, finalConversation)
                }

                realm.close()

                ModelConverter.convertSendDMRequestResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSendDMRequest(sendDMRequest: SendDMRequest) {
        if (sendDMRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (sendDMRequest.chatRequestState.value == ChatRequestState.NOTHING.value) {
            RequestUtils.throwException("chatRequestState")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param checkDMStatusRequest - client request model to check whether dm is enabled or not
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CheckDMStatusResponse - CheckDMStatusResponse model for checkDMStatusRequest
     */
    suspend fun checkDMStatus(checkDMStatusRequest: CheckDMStatusRequest): LMResponse<CheckDMStatusResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCheckDMStatusRequest(checkDMStatusRequest)

        // builds internal request model
        val request = _CheckDMStatusRequest_.Builder()
            .requestFrom(checkDMStatusRequest.requestFrom.value)
            .chatroomId(checkDMStatusRequest.chatroomId)
            .uuid(checkDMStatusRequest.uuid)
            .build()

        // calls api and processes the response accordingly
        return when (val response = dmApi.checkDMStatus(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMStatusResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCheckDMStatusRequest(checkDMStatusRequest: CheckDMStatusRequest) {
        if (checkDMStatusRequest.requestFrom.value.isEmpty()) {
            RequestUtils.throwException("requestFrom")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param blockMemberRequest - client request model to block a member
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return BlockMemberResponse - BlockMemberResponse model for blockMemberRequest
     */
    suspend fun blockMember(blockMemberRequest: BlockMemberRequest): LMResponse<BlockMemberResponse> {
        // validates the client request
        RequestUtils.validate()
        validateBlockMemberRequest(blockMemberRequest)

        // builds internal request model
        val request = _BlockMemberRequest_.Builder()
            .chatroomId(blockMemberRequest.chatroomId)
            .status(blockMemberRequest.status.value)
            .build()

        return when (val response = dmApi.blockMember(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                // save the conversation in DB
                val realm = Realm.getDefaultInstance()

                //get the chat request state as the block/unblock
                val chatRequestState =
                    if (blockMemberRequest.status == MemberBlockState.MEMBER_BLOCKED) {
                        ChatRequestState.REJECTED
                    } else {
                        ChatRequestState.ACCEPTED
                    }

                val userRO = userDB.getUser(realm)

                // updates chat request state in local DB
                chatroomDB.updateChatRequestState(
                    blockMemberRequest.chatroomId,
                    chatRequestState.value,
                    userRO?.id
                )

                val conversation = body.data?.conversation
                conversation?.let { finalConversation ->
                    conversationDB.saveNewConversation(realm, finalConversation)
                }

                realm.close()
                ModelConverter.convertBlockMemberResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateBlockMemberRequest(blockMemberRequest: BlockMemberRequest) {
        if (blockMemberRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param checkDMLimitRequest - client request model to check dm limit
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CheckDMLimitResponse - CheckDMLimitResponse model for checkDMLimitRequest
     */
    suspend fun checkDMLimit(checkDMLimitRequest: CheckDMLimitRequest): LMResponse<CheckDMLimitResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCheckDMLimitRequest(checkDMLimitRequest)

        // builds internal request model
        val request = _CheckDMLimitRequest_.Builder()
            .uuid(checkDMLimitRequest.uuid)
            .build()

        return when (val response = dmApi.checkDMLimit(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertCheckDMLimitResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCheckDMLimitRequest(checkDMLimitRequest: CheckDMLimitRequest) {
        if (checkDMLimitRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param createDMChatroomRequest - client request model to create a dm chatroom
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return CreateDMChatroomResponse - CreateDMChatroomResponse model for createDMChatroomRequest
     */
    suspend fun createDMChatroom(createDMChatroomRequest: CreateDMChatroomRequest): LMResponse<CreateDMChatroomResponse> {
        // validates the client request
        RequestUtils.validate()
        validateCreateDMChatroomRequest(createDMChatroomRequest)

        val request = _CreateDMChatroomRequest_.Builder()
            .uuid(createDMChatroomRequest.uuid)
            .build()

        return when (val response = dmApi.createDMChatroom(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                //save the dm chatroom object in local db
                body.data?.chatroom?.let { chatroomDB.saveChatroom(it) }

                ModelConverter.convertCreateDMChatroomResponse(body)
            }
        }
    }

    /**
     * validates [sendDMRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateCreateDMChatroomRequest(createDMChatroomRequest: CreateDMChatroomRequest) {
        if (createDMChatroomRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }

    /**
     * Loads all DM Chatrooms in Local DB
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @param context - Context required to run workers
     * @return Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? -
     * Worker result
     */
    fun loadDMChatrooms(
        context: Context
    ): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        //validates the client request
        RequestUtils.validate()

        val doesDMChatroomExists = ChatDBUtil.doesDMChatroomExists()
        val syncTimestamp = syncPreferences.getTimestampForSyncDM()

        return if (!doesDMChatroomExists && syncTimestamp == 0L) {
            SyncSDK.startFirstTimeDMFeedSync(context)
        } else {
            SyncSDK.startReopenSyncForDMFeed(context)
        }
    }

    /**
     * runs the query for observing dm chatrooms and returns the data in listener
     * @param listener: [HomeChatroomListener] to get object of the dm chatrooms as per requirements
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     */
    fun observeDMChatrooms(
        listener: HomeChatroomListener
    ): Observable<Unit>? {
        //create realm object
        val realm = Realm.getDefaultInstance()
        return chatroomDB.observeDMChatrooms(realm)?.map {
            listener.onChange(it.collection, it.changeset!!)
        }?.doOnDispose {
            listener.clear()
        }?.doOnTerminate {
            listener.clear()
        }
    }


    /**
     * observes dm chatroom, in real time
     *
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     */
    fun observeLiveDMChatrooms(context: Context) {
        RequestUtils.validate()

        val communityId = sdkPreferences.getCommunityId() ?: ""
        val firebaseApp = FirebaseApp.getInstance("lm-secondary")
        databaseReference = FirebaseDatabase.getInstance(firebaseApp).reference
            .child("community")
            .child(communityId)

        valueChangeListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(ChatroomEntity::class.java)

                val chatroomId = data?.chatroomId ?: return

                val realm = Realm.getDefaultInstance()
                val chatroomRO = ChatDBUtil.getChatroom(realm, chatroomId)

                if (chatroomRO != null) {
                    val isDMChatroom =
                        chatroomRO.type == TYPE_DIRECT_MESSAGE

                    if (isDMChatroom) {
                        SyncSDK.startReopenSyncForDMFeed(context)
                    }
                } else {
                    //check whether db is empty or not
                    val doesDMChatroomExists = ChatDBUtil.doesDMChatroomExists()
                    val syncTimestamp = syncPreferences.getTimestampForSyncDM()

                    if (!doesDMChatroomExists && syncTimestamp == 0L) {
                        SyncSDK.startFirstTimeDMFeedSync(context)
                    } else {
                        SyncSDK.startReopenSyncForDMFeed(context)
                    }
                }
                realm.close()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(LMChatSDK.LOG_TAG, "cancelled: ${error.message}")
            }
        }

        databaseReference?.addValueEventListener(valueChangeListener)
    }

    /**
     * remove real time listener for dm feed
     */
    fun removeLiveDMChatroomListener() {
        if (this::valueChangeListener.isInitialized) {
            databaseReference?.removeEventListener(valueChangeListener)
        }
    }

    fun getExistingDMChatroom(getExistingDMChatroomRequest: GetExistingDMChatroomRequest): LMResponse<Chatroom> {
        //validates the client request
        RequestUtils.validate()
        validateGetExistingDMChatroomRequest(getExistingDMChatroomRequest)

        val loggedInUserUUID = userPreferences.getClientUUID()
        if (loggedInUserUUID == getExistingDMChatroomRequest.userUUID) {
            return LMResponse(
                success = false,
                errorMessage = "You can't create a DM with yourself."
            )
        }


        val realm = Realm.getDefaultInstance()
        val dmChatroomRO =
            chatroomDB.getExistingDMChatroom(realm, getExistingDMChatroomRequest.userUUID)

        val dmChatroom = ModelConverter.convertChatroomRO(dmChatroomRO)
        realm.close()

        return if (dmChatroom != null) {
            LMResponse(success = true, data = dmChatroom)
        } else {
            LMResponse(
                success = false,
                errorMessage = "DM Chatroom with this user doesn't exist. Please create one."
            )
        }
    }

    private fun validateGetExistingDMChatroomRequest(getExistingDMChatroomRequest: GetExistingDMChatroomRequest) {
        if (getExistingDMChatroomRequest.userUUID.isEmpty()) {
            RequestUtils.throwException("userUUID")
        }
    }
}