package com.likeminds.likemindschat.di.modules

import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.likemindschat.sdk.util.SDKSharedResourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SharedModule {

    @Provides
    @Singleton
    fun provideSDKSharedResource(sdkSharedResources: SDKSharedResourceImpl): SDKSharedResources {
        return sdkSharedResources
    }
}