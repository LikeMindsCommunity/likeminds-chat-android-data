package com.likeminds.internalsdk

import android.app.Application
import com.google.gson.Gson
import com.likeminds.internalsdk.community.CommunityApi
import com.likeminds.internalsdk.community.CommunityApiImpl
import com.likeminds.internalsdk.di.DaggerInternalSDKComponent
import com.likeminds.internalsdk.di.InternalSDKComponent
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.internalsdk.sdk.SDKApi
import com.likeminds.internalsdk.sdk.SDKApiImpl
import com.likeminds.internalsdk.user.UserApi
import com.likeminds.internalsdk.user.UserApiImpl
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
    lateinit var userApiImpl: UserApiImpl

    @Inject
    lateinit var communityApiImpl: CommunityApiImpl

    companion object {

        private var collabmatesChatSDK: CollabmatesChatSDK? = null
        const val LOG_TAG = "LikeMindsChat"

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

    fun getUserApi(): UserApi {
        return userApiImpl
    }

    fun communityApi(): CommunityApi {
        return communityApiImpl
    }
}