package com.likeminds.likemindschat.di.internalsdk

import com.google.gson.Gson
import com.likeminds.internalsdk.GroupChatSDK
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SDKModule {

    @Provides
    @Singleton
    fun provideInternalSDK(): GroupChatSDK {
        return GroupChatSDK.getInstance()
    }

    @Provides
    @Singleton
    fun provideGson(groupChatSDK: GroupChatSDK): Gson {
        return groupChatSDK.gson
    }
}