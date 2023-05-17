package com.likeminds.likemindschat.di.modules

import com.google.gson.Gson
import com.likeminds.internalsdk.CollabmatesChatSDK
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SDKModule {

    @Provides
    @Singleton
    fun provideInternalSDK(): CollabmatesChatSDK {
        return CollabmatesChatSDK.getInstance()
    }

    @Provides
    @Singleton
    fun provideGson(collabmatesChatSDK: CollabmatesChatSDK): Gson {
        return collabmatesChatSDK.gson
    }
}