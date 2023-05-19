package com.likeminds.internalsdk

import android.app.Application
import com.google.gson.Gson
import com.likeminds.internalsdk.community.CommunityApi
import com.likeminds.internalsdk.community.CommunityApiImpl
import com.likeminds.internalsdk.db.*
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.di.DaggerInternalSDKComponent
import com.likeminds.internalsdk.di.InternalSDKComponent
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.internalsdk.refreshtoken.RefreshTokenApi
import com.likeminds.internalsdk.refreshtoken.RefreshTokenApiImpl
import com.likeminds.internalsdk.sdk.SDKApi
import com.likeminds.internalsdk.sdk.SDKApiImpl
import com.likeminds.internalsdk.user.api.UserApi
import com.likeminds.internalsdk.user.api.UserApiImpl
import com.likeminds.internalsdk.user.db.UserDB
import com.likeminds.internalsdk.user.db.UserDbImpl
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollabmatesChatSDK {

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

    companion object {

        private var collabmatesChatSDK: CollabmatesChatSDK? = null
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
        fun getInstance(): CollabmatesChatSDK {
            if (collabmatesChatSDK == null) {
                collabmatesChatSDK = CollabmatesChatSDK()
            }

            return collabmatesChatSDK!!
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

    fun communityApi(): CommunityApi {
        return communityApiImpl
    }
}