package com.likeminds.internalsdk.di

import com.likeminds.internalsdk.CollabmatesChatSDK
import dagger.BindsInstance
import dagger.Component

@Component()
interface InternalSDKComponent {
    fun inject(collabmatesChatSDK: CollabmatesChatSDK)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun sdkSharedResources(sdkSharedResources: SDKSharedResources): Builder

        fun build(): InternalSDKComponent
    }
}