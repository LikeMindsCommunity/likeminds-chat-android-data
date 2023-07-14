package com.likeminds.internalsdk

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.likeminds.internalsdk.chatroom.api.ChatroomApi
import com.likeminds.internalsdk.chatroom.api.ChatroomApiImpl
import com.likeminds.internalsdk.chatroom.db.ChatroomDB
import com.likeminds.internalsdk.chatroom.db.ChatroomDBImpl
import com.likeminds.internalsdk.community.CommunityApi
import com.likeminds.internalsdk.community.CommunityApiImpl
import com.likeminds.internalsdk.conversation.api.ConversationApi
import com.likeminds.internalsdk.conversation.api.ConversationApiImpl
import com.likeminds.internalsdk.conversation.db.ConversationDB
import com.likeminds.internalsdk.conversation.db.ConversationDbImpl
import com.likeminds.internalsdk.db.*
import com.likeminds.internalsdk.db.util.DbCompactOnLaunchCallback
import com.likeminds.internalsdk.di.*
import com.likeminds.internalsdk.helper.HelperApi
import com.likeminds.internalsdk.helper.HelperApiImpl
import com.likeminds.internalsdk.homefeed.api.HomeFeedApi
import com.likeminds.internalsdk.homefeed.api.HomeFeedApiImpl
import com.likeminds.internalsdk.homefeed.db.HomeFeedDB
import com.likeminds.internalsdk.homefeed.db.HomeFeedDBImpl
import com.likeminds.internalsdk.moderation.ModerationApi
import com.likeminds.internalsdk.moderation.ModerationApiImpl
import com.likeminds.internalsdk.notification.NotificationApi
import com.likeminds.internalsdk.notification.NotificationApiImpl
import com.likeminds.internalsdk.poll.PollApi
import com.likeminds.internalsdk.poll.PollApiImpl
import com.likeminds.internalsdk.refreshtoken.RefreshTokenApi
import com.likeminds.internalsdk.refreshtoken.RefreshTokenApiImpl
import com.likeminds.internalsdk.sdk.SDKApi
import com.likeminds.internalsdk.sdk.SDKApiImpl
import com.likeminds.internalsdk.sdk.util.SDKPreferences
import com.likeminds.internalsdk.search.SearchApi
import com.likeminds.internalsdk.search.SearchApiImpl
import com.likeminds.internalsdk.sync.api.chatroom.ChatroomSyncApi
import com.likeminds.internalsdk.sync.api.chatroom.ChatroomSyncApiImpl
import com.likeminds.internalsdk.sync.api.conversation.ConversationSyncApi
import com.likeminds.internalsdk.sync.api.conversation.ConversationSyncApiImpl
import com.likeminds.internalsdk.user.api.UserApi
import com.likeminds.internalsdk.user.api.UserApiImpl
import com.likeminds.internalsdk.user.db.UserDB
import com.likeminds.internalsdk.user.db.UserDbImpl
import com.likeminds.internalsdk.user.util.UserPreferences
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupChatSDK {

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
    lateinit var notificationApiImpl: NotificationApiImpl

    @Inject
    lateinit var sdkPreferences: SDKPreferences

    @Inject
    lateinit var userPreferences: UserPreferences

    companion object {

        private var groupChatSDK: GroupChatSDK? = null
        const val LOG_TAG = "LikeMindsChat"

        @JvmStatic
        fun getInstance(): GroupChatSDK {
            if (groupChatSDK == null) {
                groupChatSDK = GroupChatSDK()
            }

            return groupChatSDK!!
        }
    }

    fun initialize(sdkSharedResources: SDKSharedResources) {
        initSDKComponent(sdkSharedResources)
        initRealmAndMigrateAsync()
    }

    private fun initRealmAndMigrateAsync() {
        Realm.init(application)

        Realm.setDefaultConfiguration(getNewDbConfig())

        migrateDbAsync { }
    }

    private fun migrateDbAsync(cb: (Boolean) -> Unit) {
        val config = Realm.getDefaultConfiguration()
        if (config == null) {
            cb(false)
            return
        }
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                cb(true)
            }

            override fun onError(exception: Throwable) {
                super.onError(exception)
                Log.e(LOG_TAG, "migration occurred with", exception)
                cb(false)
            }
        })
    }

    private fun getNewDbConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(DB_SCHEMA_NAME)
            .schemaVersion(DB_SCHEMA_VERSION)
            .migration(RealmDBMigration())
            .compactOnLaunch(DbCompactOnLaunchCallback())
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

    fun getNotificationApi(): NotificationApi {
        return notificationApiImpl
    }
}