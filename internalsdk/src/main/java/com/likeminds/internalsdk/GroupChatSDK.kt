package com.likeminds.internalsdk

import android.app.Application
import com.google.gson.Gson
import com.likeminds.internalsdk.chatroom.ChatroomApi
import com.likeminds.internalsdk.chatroom.ChatroomApiImpl
import com.likeminds.internalsdk.community.CommunityApi
import com.likeminds.internalsdk.community.CommunityApiImpl
import com.likeminds.internalsdk.conversation.ConversationApi
import com.likeminds.internalsdk.conversation.ConversationApiImpl
import com.likeminds.internalsdk.db.DB_SCHEMA_NAME
import com.likeminds.internalsdk.db.DB_SCHEMA_VERSION
import com.likeminds.internalsdk.db.RealmDBMigration
import com.likeminds.internalsdk.db.models.AppConfigRO
import com.likeminds.internalsdk.db.models.SDKClientInfoRO
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.di.DaggerInternalSDKComponent
import com.likeminds.internalsdk.di.InternalSDKComponent
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.internalsdk.helper.HelperApi
import com.likeminds.internalsdk.helper.HelperApiImpl
import com.likeminds.internalsdk.homefeed.HomeFeedApi
import com.likeminds.internalsdk.homefeed.HomeFeedApiImpl
import com.likeminds.internalsdk.moderation.ModerationApi
import com.likeminds.internalsdk.moderation.ModerationApiImpl
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
import com.likeminds.internalsdk.user.api.UserApi
import com.likeminds.internalsdk.user.api.UserApiImpl
import com.likeminds.internalsdk.user.db.UserDB
import com.likeminds.internalsdk.user.db.UserDbImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    lateinit var chatroomApiImpl: ChatroomApiImpl

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
    lateinit var conversationApiImpl: ConversationApiImpl

    @Inject
    lateinit var sdkPreferences: SDKPreferences

    companion object {

        private var groupChatSDK: GroupChatSDK? = null
        const val LOG_TAG = "LikeMindsChat"

        fun getRealmConfiguration(): RealmConfiguration {
            val schema = setOf(AppConfigRO::class, UserRO::class, SDKClientInfoRO::class)
            return RealmConfiguration.Builder(schema)
                .name(DB_SCHEMA_NAME)
                .schemaVersion(DB_SCHEMA_VERSION)
                .migration(RealmDBMigration())
                .build()
        }

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
        CoroutineScope(Dispatchers.IO).launch {
            val realm = Realm.open(getRealmConfiguration())
            realm.close()
        }
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

    fun homeFeedApi(): HomeFeedApi {
        return homeFeedApi
    }

    fun getChatroomApi(): ChatroomApi {
        return chatroomApiImpl
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

    fun getConversationApi(): ConversationApi {
        return conversationApiImpl
    }
}