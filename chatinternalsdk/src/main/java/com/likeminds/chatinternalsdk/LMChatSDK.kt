package com.likeminds.chatinternalsdk

import android.app.Application
import com.google.gson.Gson
import com.likeminds.chatinternalsdk.chatroom.api.ChatroomApi
import com.likeminds.chatinternalsdk.chatroom.api.ChatroomApiImpl
import com.likeminds.chatinternalsdk.chatroom.db.ChatroomDB
import com.likeminds.chatinternalsdk.chatroom.db.ChatroomDBImpl
import com.likeminds.chatinternalsdk.community.api.CommunityApi
import com.likeminds.chatinternalsdk.community.api.CommunityApiImpl
import com.likeminds.chatinternalsdk.community.db.CommunityDB
import com.likeminds.chatinternalsdk.community.db.CommunityDBImpl
import com.likeminds.chatinternalsdk.conversation.api.ConversationApi
import com.likeminds.chatinternalsdk.conversation.api.ConversationApiImpl
import com.likeminds.chatinternalsdk.conversation.db.ConversationDB
import com.likeminds.chatinternalsdk.conversation.db.ConversationDbImpl
import com.likeminds.chatinternalsdk.db.*
import com.likeminds.chatinternalsdk.db.util.DbCompactOnLaunchCallback
import com.likeminds.chatinternalsdk.di.*
import com.likeminds.chatinternalsdk.dm.DMApi
import com.likeminds.chatinternalsdk.dm.DMApiImpl
import com.likeminds.chatinternalsdk.helper.api.HelperApi
import com.likeminds.chatinternalsdk.helper.api.HelperApiImpl
import com.likeminds.chatinternalsdk.helper.db.HelperDB
import com.likeminds.chatinternalsdk.helper.db.HelperDBImpl
import com.likeminds.chatinternalsdk.homefeed.api.HomeFeedApi
import com.likeminds.chatinternalsdk.homefeed.api.HomeFeedApiImpl
import com.likeminds.chatinternalsdk.homefeed.db.HomeFeedDB
import com.likeminds.chatinternalsdk.homefeed.db.HomeFeedDBImpl
import com.likeminds.chatinternalsdk.moderation.ModerationApi
import com.likeminds.chatinternalsdk.moderation.ModerationApiImpl
import com.likeminds.chatinternalsdk.notification.NotificationDB
import com.likeminds.chatinternalsdk.notification.NotificationDBImpl
import com.likeminds.chatinternalsdk.poll.PollApi
import com.likeminds.chatinternalsdk.poll.PollApiImpl
import com.likeminds.chatinternalsdk.refreshtoken.RefreshTokenApi
import com.likeminds.chatinternalsdk.refreshtoken.RefreshTokenApiImpl
import com.likeminds.chatinternalsdk.sdk.SDKApi
import com.likeminds.chatinternalsdk.sdk.SDKApiImpl
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.search.SearchApi
import com.likeminds.chatinternalsdk.search.SearchApiImpl
import com.likeminds.chatinternalsdk.sync.api.chatroom.ChatroomSyncApi
import com.likeminds.chatinternalsdk.sync.api.chatroom.ChatroomSyncApiImpl
import com.likeminds.chatinternalsdk.sync.api.conversation.ConversationSyncApi
import com.likeminds.chatinternalsdk.sync.api.conversation.ConversationSyncApiImpl
import com.likeminds.chatinternalsdk.sync.util.SyncPreferences
import com.likeminds.chatinternalsdk.user.api.UserApi
import com.likeminds.chatinternalsdk.user.api.UserApiImpl
import com.likeminds.chatinternalsdk.user.db.UserDB
import com.likeminds.chatinternalsdk.user.db.UserDbImpl
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.websocket.LMChatWebSocketManager
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatSDK {

    private var sdkComponent: InternalSDKComponent? = null

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var sdkApiImpl: SDKApiImpl

    @Inject
    lateinit var refreshTokenApiImpl: RefreshTokenApiImpl

    @Inject
    lateinit var userApiImpl: UserApiImpl

    @Inject
    lateinit var userDbImpl: UserDbImpl

    @Inject
    lateinit var communityApiImpl: CommunityApiImpl

    @Inject
    lateinit var communityDBImpl: CommunityDBImpl

    @Inject
    lateinit var homeFeedApi: HomeFeedApiImpl

    @Inject
    lateinit var homeFeedDB: HomeFeedDBImpl

    @Inject
    lateinit var chatroomApiImpl: ChatroomApiImpl

    @Inject
    lateinit var chatroomDbImpl: ChatroomDBImpl

    @Inject
    lateinit var moderationApiImpl: ModerationApiImpl

    @Inject
    lateinit var pollApiImpl: PollApiImpl

    @Inject
    lateinit var helperApiImpl: HelperApiImpl

    @Inject
    lateinit var searchApiImpl: SearchApiImpl

    @Inject
    lateinit var chatroomSyncApiImpl: ChatroomSyncApiImpl

    @Inject
    lateinit var conversationSyncApiImpl: ConversationSyncApiImpl

    @Inject
    lateinit var conversationApiImpl: ConversationApiImpl

    @Inject
    lateinit var conversationDBImpl: ConversationDbImpl

    @Inject
    lateinit var notificationDBImpl: NotificationDBImpl

    @Inject
    lateinit var dmApi: DMApiImpl

    @Inject
    lateinit var sdkPreferences: SDKPreferences

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var syncPreferences: SyncPreferences

    @Inject
    lateinit var helperDBImpl: HelperDBImpl

    @Inject
    lateinit var lmChatWebSocketManager: LMChatWebSocketManager

    var lmChatInternalCallback: LMChatInternalCallback? = null

    companion object {

        private var chatSDK: LMChatSDK? = null
        const val LOG_TAG = "LikeMindsChat"

        @JvmStatic
        fun getInstance(): LMChatSDK {
            if (chatSDK == null) {
                chatSDK = LMChatSDK()
            }

            return chatSDK!!
        }
    }

    fun initialize(
        sdkSharedResources: SDKSharedResources,
        lmChatInternalCallback: LMChatInternalCallback?,
    ) {
        initSDKComponent(sdkSharedResources)
        initRealm()
        this.lmChatInternalCallback = lmChatInternalCallback
    }

    private fun initRealm() {
        Realm.init(application)

        Realm.setDefaultConfiguration(getNewDbConfig())

        migrate()
    }

    private fun migrate() {
        CoroutineScope(Dispatchers.IO).launch {
            Realm.getDefaultInstance()
        }
    }

    private fun getNewDbConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(DB_SCHEMA_NAME)
            .schemaVersion(DB_SCHEMA_VERSION)
            .migration(RealmDBMigration())
            .compactOnLaunch(DbCompactOnLaunchCallback())
            .allowWritesOnUiThread(true)
            .build()
    }

    private fun initSDKComponent(sdkSharedResources: SDKSharedResources) {
        if (sdkComponent == null) {
            sdkComponent = DaggerInternalSDKComponent.builder()
                .sdkSharedResources(sdkSharedResources)
                .build()
            sdkComponent?.inject(this)
        }
    }

    fun getSDKPreferences(): SDKPreferences {
        return sdkPreferences
    }

    fun getUserPreference(): UserPreferences {
        return userPreferences
    }

    fun getSyncPreference(): SyncPreferences {
        return syncPreferences
    }

    fun getSDKApi(): SDKApi {
        return sdkApiImpl
    }

    fun getRefreshTokenApi(): RefreshTokenApi {
        return refreshTokenApiImpl
    }

    fun getUserApi(): UserApi {
        return userApiImpl
    }

    fun getUserDb(): UserDB {
        return userDbImpl
    }

    fun getCommunityApi(): CommunityApi {
        return communityApiImpl
    }

    fun getCommunityDB(): CommunityDB {
        return communityDBImpl
    }

    fun getHomeFeedApi(): HomeFeedApi {
        return homeFeedApi
    }

    fun getHomeFeedDb(): HomeFeedDB {
        return homeFeedDB
    }

    fun getChatroomApi(): ChatroomApi {
        return chatroomApiImpl
    }

    fun getChatroomDb(): ChatroomDB {
        return chatroomDbImpl
    }

    fun getModerationApi(): ModerationApi {
        return moderationApiImpl
    }

    fun getPollApi(): PollApi {
        return pollApiImpl
    }

    fun getHelperApi(): HelperApi {
        return helperApiImpl
    }

    fun getSearchApi(): SearchApi {
        return searchApiImpl
    }

    fun getChatroomSyncApi(): ChatroomSyncApi {
        return chatroomSyncApiImpl
    }

    fun getConversationSyncApi(): ConversationSyncApi {
        return conversationSyncApiImpl
    }

    fun getConversationApi(): ConversationApi {
        return conversationApiImpl
    }

    fun getConversationDB(): ConversationDB {
        return conversationDBImpl
    }

    fun getNotificationDB(): NotificationDB {
        return notificationDBImpl
    }

    fun getDMApi(): DMApi {
        return dmApi
    }

    fun getHelperDB(): HelperDB {
        return helperDBImpl
    }

    fun getWebSocketManager(): LMChatWebSocketManager {
        return lmChatWebSocketManager
    }

    fun getGsonObject(): Gson {
        return gson
    }
}