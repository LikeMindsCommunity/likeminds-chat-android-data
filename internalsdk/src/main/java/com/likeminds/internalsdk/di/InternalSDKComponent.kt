package com.likeminds.internalsdk.di

import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.di.modules.CommunityModule
import com.likeminds.internalsdk.di.modules.GsonModule
import com.likeminds.internalsdk.di.modules.NetworkModule
import com.likeminds.internalsdk.di.modules.SDKModule
import com.likeminds.internalsdk.di.modules.SDKSharedResourcesModule
import com.likeminds.internalsdk.di.modules.UserModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [SDKSharedResourcesModule::class,
        GsonModule::class,
        NetworkModule::class,
        SDKModule::class,
        UserModule::class,
        CommunityModule::class
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