package com.likeminds.likemindschat.di

import android.app.Application
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.di.chatroom.ChatroomModule
import com.likeminds.likemindschat.di.chatroom.ChatroomSubComponent
import com.likeminds.likemindschat.di.community.CommunityModule
import com.likeminds.likemindschat.di.community.CommunitySubComponent
import com.likeminds.likemindschat.di.conversation.ConversationModule
import com.likeminds.likemindschat.di.conversation.ConversationSubComponent
import com.likeminds.likemindschat.di.dm.DMModule
import com.likeminds.likemindschat.di.dm.DMSubComponent
import com.likeminds.likemindschat.di.helper.HelperModule
import com.likeminds.likemindschat.di.helper.HelperSubComponent
import com.likeminds.likemindschat.di.homefeed.HomeFeedModule
import com.likeminds.likemindschat.di.homefeed.HomeFeedSubComponent
import com.likeminds.likemindschat.di.initiateUser.InitiateUserModule
import com.likeminds.likemindschat.di.initiateUser.InitiateUserSubComponent
import com.likeminds.likemindschat.di.internalsdk.SDKModule
import com.likeminds.likemindschat.di.internalsdk.SharedModule
import com.likeminds.likemindschat.di.moderation.ModerationModule
import com.likeminds.likemindschat.di.moderation.ModerationSubComponent
import com.likeminds.likemindschat.di.notification.NotificationModule
import com.likeminds.likemindschat.di.notification.NotificationSubComponent
import com.likeminds.likemindschat.di.poll.PollModule
import com.likeminds.likemindschat.di.poll.PollSubComponent
import com.likeminds.likemindschat.di.search.SearchSubComponent
import com.likeminds.likemindschat.di.user.UserModule
import com.likeminds.likemindschat.di.user.UserSubComponent
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SDKModule::class,
        SharedModule::class,
        InitiateUserModule::class,
        UserModule::class,
        ChatroomModule::class,
        CommunityModule::class,
        ModerationModule::class,
        PollModule::class,
        HelperModule::class,
        HomeFeedModule::class,
        ConversationModule::class,
        NotificationModule::class,
        DMModule::class
    ]
)
internal interface LikeMindsChatComponent {

    fun inject(likeMindsChatApplication: LikeMindsChatApplication)

    fun inject(lmChatClient: LMChatClient)

    fun initiateUserComponent(): InitiateUserSubComponent.Factory
    fun userComponent(): UserSubComponent.Factory
    fun chatroomSubComponent(): ChatroomSubComponent.Factory
    fun communitySubComponent(): CommunitySubComponent.Factory
    fun moderationSubComponent(): ModerationSubComponent.Factory
    fun pollSubComponent(): PollSubComponent.Factory
    fun helperSubComponent(): HelperSubComponent.Factory
    fun homeFeedComponent(): HomeFeedSubComponent.Factory
    fun searchSubComponent(): SearchSubComponent.Factory
    fun conversationSubComponent(): ConversationSubComponent.Factory
    fun notificationSubComponent(): NotificationSubComponent.Factory
    fun dmSubComponent(): DMSubComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): LikeMindsChatComponent
    }
}