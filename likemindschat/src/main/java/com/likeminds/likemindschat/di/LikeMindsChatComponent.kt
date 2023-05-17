package com.likeminds.likemindschat.di

import android.app.Application
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.di.modules.SDKModule
import com.likeminds.likemindschat.di.modules.SharedModule
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SDKModule::class, SharedModule::class])
internal interface LikeMindsChatComponent {

    fun inject(likeMindsChatApplication: LikeMindsChatApplication)

    fun inject(lmChatClient: LMChatClient)


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): LikeMindsChatComponent
    }
}