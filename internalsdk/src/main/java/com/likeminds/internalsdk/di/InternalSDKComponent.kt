package com.likeminds.internalsdk.di

import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SDKSharedResourcesModule::class,
        GsonModule::class,
        NetworkModule::class,
        SDKModule::class,
        UserModule::class,
        CommunityModule::class,
        ChatroomModule::class,
        ModerationModule::class,
        PollModule::class,
        HelperModule::class,
        SearchModule::class
    ]
)
interface InternalSDKComponent {

    fun inject(groupChatSDK: GroupChatSDK)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun sdkSharedResources(sdkSharedResources: SDKSharedResources): Builder

        fun build(): InternalSDKComponent
    }
}