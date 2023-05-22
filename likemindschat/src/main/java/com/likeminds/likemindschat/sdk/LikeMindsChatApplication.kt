package com.likeminds.likemindschat.sdk

import android.app.Application
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.likemindschat.di.DaggerLikeMindsChatComponent
import com.likeminds.likemindschat.di.LikeMindsChatComponent
import com.likeminds.likemindschat.di.initiateUser.InitiateUserSubComponent
import javax.inject.Inject

internal class LikeMindsChatApplication private constructor() {

    @Inject
    lateinit var groupChatSDK: GroupChatSDK


    @Inject
    lateinit var sdkSharedResources: SDKSharedResources

    var likeMindsChatComponent: LikeMindsChatComponent? = null
    private var initiateUserSubComponent: InitiateUserSubComponent? = null

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
        groupChatSDK.initialize(sdkSharedResources)
    }

    private fun initLikeMindsChatComponent(application: Application) {
        if (likeMindsChatComponent == null) {
            likeMindsChatComponent = DaggerLikeMindsChatComponent.builder()
                .application(application)
                .build()
        }
        likeMindsChatComponent?.inject(this)
    }

    fun initiateUserComponent(): InitiateUserSubComponent? {
        if (initiateUserSubComponent == null) {
            initiateUserSubComponent = likeMindsChatComponent?.initiateUserComponent()?.create()
        }
        return initiateUserSubComponent
    }
}