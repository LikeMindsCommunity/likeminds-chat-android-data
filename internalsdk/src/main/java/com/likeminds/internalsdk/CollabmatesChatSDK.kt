package com.likeminds.internalsdk

import com.likeminds.internalsdk.di.DaggerInternalSDKComponent
import com.likeminds.internalsdk.di.InternalSDKComponent
import com.likeminds.internalsdk.di.SDKSharedResources
import javax.inject.Singleton

@Singleton
class CollabmatesChatSDK {
    private var sdkComponent: InternalSDKComponent? = null

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
}