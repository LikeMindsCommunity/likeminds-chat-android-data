package com.likeminds.likemindschat.sdk

import android.app.Application
import android.util.Base64
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.likeminds.chatinternalsdk.LMChatInternalCallback
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.di.SDKSharedResources
import com.likeminds.likemindschat.LMChatSDKCallback
import com.likeminds.likemindschat.conversation.model.ConversationState
import com.likeminds.likemindschat.di.DaggerLikeMindsChatComponent
import com.likeminds.likemindschat.di.LikeMindsChatComponent
import com.likeminds.likemindschat.di.chatroom.ChatroomSubComponent
import com.likeminds.likemindschat.di.community.CommunitySubComponent
import com.likeminds.likemindschat.di.conversation.ConversationSubComponent
import com.likeminds.likemindschat.di.dm.DMSubComponent
import com.likeminds.likemindschat.di.helper.HelperSubComponent
import com.likeminds.likemindschat.di.homefeed.HomeFeedSubComponent
import com.likeminds.likemindschat.di.moderation.ModerationSubComponent
import com.likeminds.likemindschat.di.notification.NotificationSubComponent
import com.likeminds.likemindschat.di.poll.PollSubComponent
import com.likeminds.likemindschat.di.search.SearchSubComponent
import com.likeminds.likemindschat.di.user.UserSubComponent
import com.likeminds.likemindschat.helper.LMChatLogger
import com.likeminds.likemindschat.helper.model.LMChatInitiateLoggerRequest
import com.likeminds.likemindschat.sdk.util.ApiKeys
import javax.inject.Inject

internal class LikeMindsChatApplication private constructor() : LMChatInternalCallback {

    @Inject
    lateinit var chatSDK: LMChatSDK

    @Inject
    lateinit var sdkSharedResources: SDKSharedResources
    var likeMindsChatComponent: LikeMindsChatComponent? = null
    private var homeFeedSubComponent: HomeFeedSubComponent? = null
    private var userSubComponent: UserSubComponent? = null
    private var communitySubComponent: CommunitySubComponent? = null
    private var chatroomSubComponent: ChatroomSubComponent? = null
    private var moderationSubComponent: ModerationSubComponent? = null
    private var pollSubComponent: PollSubComponent? = null
    private var helperSubComponent: HelperSubComponent? = null
    private var searchSubComponent: SearchSubComponent? = null
    private var conversationSubComponent: ConversationSubComponent? = null
    private var notificationSubComponent: NotificationSubComponent? = null
    private var dmSubComponent: DMSubComponent? = null
    private var lmChatSDKCallback: LMChatSDKCallback? = null

    var excludedConversationStates: List<Int> = emptyList()

    companion object {

        private var likeMindsChatApplicationInstance: LikeMindsChatApplication? = null

        @JvmStatic
        fun getInstance(): LikeMindsChatApplication {
            if (likeMindsChatApplicationInstance == null) {
                Log.d("PUI","likeMindsChatApplicationInstance instance created")
                likeMindsChatApplicationInstance = LikeMindsChatApplication()
            }

            return likeMindsChatApplicationInstance!!
        }
    }

    fun initChatSDKApplication(
        application: Application,
        lmChatSDKCallback: LMChatSDKCallback? = null,
        initiateLoggerRequest: LMChatInitiateLoggerRequest? = null,
        excludedConversationStates: List<ConversationState> = emptyList()
    ) {
        Log.d("PUI","likeMindsChatApplicationInstance -> initChatSDKApplication called")
        likeMindsChatApplicationInstance = this
        this.lmChatSDKCallback = lmChatSDKCallback

        if (initiateLoggerRequest != null) {
            Log.d("PUI","likeMindsChatApplicationInstance -> logger started")
            LMChatLogger.initiate(initiateLoggerRequest)
        }

        initLikeMindsChatComponent(application)
        initializeFirebase(application)

        //convert to int value of the states
        this.excludedConversationStates = excludedConversationStates.map {
            it.value
        }

        chatSDK.initialize(sdkSharedResources, this)
    }

    private fun initLikeMindsChatComponent(application: Application) {
        if (likeMindsChatComponent == null) {
            Log.d("PUI","initLikeMindsChatComponent called and likeMindsChatComponent is created")
            likeMindsChatComponent = DaggerLikeMindsChatComponent.builder()
                .application(application)
                .build()
        }
        likeMindsChatComponent?.inject(this)
    }

    private fun initializeFirebase(application: Application) {
        //For real time messaging initialize Collabmates project.
        val option = FirebaseOptions.Builder()
            .setProjectId(String(Base64.decode(ApiKeys.getProjectId(), Base64.DEFAULT)))
            .setApplicationId(String(Base64.decode(ApiKeys.getAppId(), Base64.DEFAULT)))
            .setDatabaseUrl(String(Base64.decode(ApiKeys.getDataBaseUrl(), Base64.DEFAULT)))
            .setApiKey(String(Base64.decode(ApiKeys.getApiKey(), Base64.DEFAULT)))
            .build()

        FirebaseApp.initializeApp(application, option, "lm-secondary")
    }

    fun homeFeedComponent(): HomeFeedSubComponent? {
        if (homeFeedSubComponent == null) {
            homeFeedSubComponent = likeMindsChatComponent?.homeFeedComponent()?.create()
        }

        return homeFeedSubComponent
    }

    fun userComponent(): UserSubComponent? {
        if (userSubComponent == null) {
            Log.d("PUI","userSubComponent is created")
            userSubComponent = likeMindsChatComponent?.userComponent()?.create()
        }
        return userSubComponent
    }

    fun communityComponent(): CommunitySubComponent? {
        if (communitySubComponent == null) {
            communitySubComponent = likeMindsChatComponent?.communitySubComponent()?.create()
        }
        return communitySubComponent
    }

    fun chatroomComponent(): ChatroomSubComponent? {
        if (chatroomSubComponent == null) {
            Log.d("PUI","chatroomSubComponent is created")
            chatroomSubComponent = likeMindsChatComponent?.chatroomSubComponent()?.create()
        }
        return chatroomSubComponent
    }

    fun moderationComponent(): ModerationSubComponent? {
        if (moderationSubComponent == null) {
            moderationSubComponent = likeMindsChatComponent?.moderationSubComponent()?.create()
        }
        return moderationSubComponent
    }

    fun pollComponent(): PollSubComponent? {
        if (pollSubComponent == null) {
            pollSubComponent = likeMindsChatComponent?.pollSubComponent()?.create()
        }
        return pollSubComponent
    }

    fun helperComponent(): HelperSubComponent? {
        if (helperSubComponent == null) {
            helperSubComponent = likeMindsChatComponent?.helperSubComponent()?.create()
        }
        return helperSubComponent
    }

    fun searchComponent(): SearchSubComponent? {
        if (searchSubComponent == null) {
            searchSubComponent = likeMindsChatComponent?.searchSubComponent()?.create()
        }
        return searchSubComponent
    }

    fun conversationComponent(): ConversationSubComponent? {
        if (conversationSubComponent == null) {
            Log.d("PUI","conversationSubComponent is created")
            conversationSubComponent = likeMindsChatComponent?.conversationSubComponent()?.create()
        }
        return conversationSubComponent
    }

    fun notificationSubComponent(): NotificationSubComponent? {
        if (notificationSubComponent == null) {
            Log.d("PUI","notificationSubComponent is created")
            notificationSubComponent = likeMindsChatComponent?.notificationSubComponent()?.create()
        }
        return notificationSubComponent
    }

    fun dmSubComponent(): DMSubComponent? {
        if (dmSubComponent == null) {
            dmSubComponent = likeMindsChatComponent?.dmSubComponent()?.create()
        }
        return dmSubComponent
    }

    override fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String) {
        lmChatSDKCallback?.onAccessTokenExpiredAndRefreshed(accessToken, refreshToken)
    }

    override fun onRefreshTokenExpired(): Pair<String?, String?> {
        return lmChatSDKCallback?.onRefreshTokenExpired() ?: Pair(null, null)
    }
}