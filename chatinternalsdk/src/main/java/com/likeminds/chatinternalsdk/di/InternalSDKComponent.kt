package com.likeminds.chatinternalsdk.di

import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.di.modules.*
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
        HomeFeedModule::class,
        ChatroomModule::class,
        ModerationModule::class,
        PollModule::class,
        HelperModule::class,
        SearchModule::class,
        ConversationModule::class,
        NotificationModule::class,
        DMModule::class
    ]
)
interface InternalSDKComponent {

    fun inject(chatSDK: LMChatSDK)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun sdkSharedResources(sdkSharedResources: SDKSharedResources): Builder

        fun build(): InternalSDKComponent
    }
}