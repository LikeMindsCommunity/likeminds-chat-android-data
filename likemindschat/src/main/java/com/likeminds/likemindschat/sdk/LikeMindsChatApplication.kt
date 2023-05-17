package com.likeminds.likemindschat.sdk

import android.app.Application
import com.likeminds.internalsdk.CollabmatesChatSDK
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.likemindschat.di.DaggerLikeMindsChatComponent
import com.likeminds.likemindschat.di.LikeMindsChatComponent
import javax.inject.Inject

internal class LikeMindsChatApplication private constructor() {

    @Inject
    lateinit var collabmatesChatSDK: CollabmatesChatSDK


    @Inject
    lateinit var sdkSharedResources: SDKSharedResources

    var likeMindsChatComponent: LikeMindsChatComponent? = null

    companion object {
        private var likeMindsChatApplicationInstance: LikeMindsChatApplication? = null


        @JvmStatic
        fun getInstance(): LikeMindsChatApplication {
            if (likeMindsChatApplicationInstance == null) {
                likeMindsChatApplicationInstance = LikeMindsChatApplication()
            }

            return likeMindsChatApplicationInstance!!
        }
    }

    fun initChatSDKApplication(application: Application) {
        likeMindsChatApplicationInstance = this

        initLikeMindsChatComponent(application)
        collabmatesChatSDK.initialize(sdkSharedResources)
    }

    private fun initLikeMindsChatComponent(application: Application) {
        if (likeMindsChatComponent == null) {
            likeMindsChatComponent = DaggerLikeMindsChatComponent.builder()
                .application(application)
                .build()
        }
        likeMindsChatComponent?.inject(this)
    }
}