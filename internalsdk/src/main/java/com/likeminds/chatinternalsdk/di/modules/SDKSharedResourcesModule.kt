package com.likeminds.chatinternalsdk.di.modules

import android.app.Application
import android.content.Context
import com.likeminds.chatinternalsdk.di.SDKSharedResources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SDKSharedResourcesModule {
    @Provides
    @Singleton
    fun provideApplication(sdkSharedResources: SDKSharedResources): Application {
        return sdkSharedResources.getApplication()
    }

    @Provides
    @Singleton
    fun provideContext(sdkSharedResources: SDKSharedResources): Context {
        return sdkSharedResources.getApplication()
    }

}