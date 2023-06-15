package com.likeminds.likemindschat.sdk

import android.app.Application
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.di.SDKSharedResources
import com.likeminds.likemindschat.di.DaggerLikeMindsChatComponent
import com.likeminds.likemindschat.di.LikeMindsChatComponent
import com.likeminds.likemindschat.di.chatroom.ChatroomSubComponent
import com.likeminds.likemindschat.di.community.CommunitySubComponent
import com.likeminds.likemindschat.di.helper.HelperSubComponent
import com.likeminds.likemindschat.di.homefeed.HomeFeedSubComponent
import com.likeminds.likemindschat.di.initiateUser.InitiateUserSubComponent
import com.likeminds.likemindschat.di.moderation.ModerationSubComponent
import com.likeminds.likemindschat.di.poll.PollSubComponent
import com.likeminds.likemindschat.di.search.SearchSubComponent
import com.likeminds.likemindschat.di.user.UserSubComponent
import javax.inject.Inject

internal class LikeMindsChatApplication private constructor() {

    @Inject
    lateinit var groupChatSDK: GroupChatSDK

    @Inject
    lateinit var sdkSharedResources: SDKSharedResources
    var likeMindsChatComponent: LikeMindsChatComponent? = null
    private var initiateUserSubComponent: InitiateUserSubComponent? = null
    private var homeFeedSubComponent: HomeFeedSubComponent? = null
    private var userSubComponent: UserSubComponent? = null
    private var communitySubComponent: CommunitySubComponent? = null
    private var chatroomSubComponent: ChatroomSubComponent? = null
    private var moderationSubComponent: ModerationSubComponent? = null
    private var pollSubComponent: PollSubComponent? = null
    private var helperSubComponent: HelperSubComponent? = null
    private var searchSubComponent: SearchSubComponent? = null

    companion object {

        private var likeMindsChatApplicationInstance: LikeMindsChatApplication? = null

        @JvmStatic
        fun getInstance(): LikeMindsChatApplication {
            if (likeMindsChatApplicationInstance == null) {
                likeMindsChatApplicationInstance = LikeMindsChatApplication()
            }

            return likeMindsChatApplicationInstance!!
        }
    }

    fun initChatSDKApplication(application: Application) {
        likeMindsChatApplicationInstance = this

        initLikeMindsChatComponent(application)
        groupChatSDK.initialize(sdkSharedResources)
    }

    private fun initLikeMindsChatComponent(application: Application) {
        if (likeMindsChatComponent == null) {
            likeMindsChatComponent = DaggerLikeMindsChatComponent.builder()
                .application(application)
                .build()
        }
        likeMindsChatComponent?.inject(this)
    }

    fun initiateUserComponent(): InitiateUserSubComponent? {
        if (initiateUserSubComponent == null) {
            initiateUserSubComponent = likeMindsChatComponent?.initiateUserComponent()?.create()
        }
        return initiateUserSubComponent
    }

    fun homeFeedComponent(): HomeFeedSubComponent? {
        if (homeFeedSubComponent == null) {
            homeFeedSubComponent = likeMindsChatComponent?.homeFeedComponent()?.create()
        }

        return homeFeedSubComponent
    }

    fun userComponent(): UserSubComponent? {
        if (userSubComponent == null) {
            userSubComponent = likeMindsChatComponent?.userComponent()?.create()
        }
        return userSubComponent
    }

    fun communityComponent(): CommunitySubComponent? {
        if (communitySubComponent == null) {
            communitySubComponent = likeMindsChatComponent?.communitySubComponent()?.create()
        }
        return communitySubComponent
    }

    fun chatroomComponent(): ChatroomSubComponent? {
        if (chatroomSubComponent == null) {
            chatroomSubComponent = likeMindsChatComponent?.chatroomSubComponent()?.create()
        }
        return chatroomSubComponent
    }

    fun moderationComponent(): ModerationSubComponent? {
        if (moderationSubComponent == null) {
            moderationSubComponent = likeMindsChatComponent?.moderationSubComponent()?.create()
        }
        return moderationSubComponent
    }

    fun pollComponent(): PollSubComponent? {
        if (pollSubComponent == null) {
            pollSubComponent = likeMindsChatComponent?.pollSubComponent()?.create()
        }
        return pollSubComponent
    }

    fun helperComponent(): HelperSubComponent? {
        if (helperSubComponent == null) {
            helperSubComponent = likeMindsChatComponent?.helperSubComponent()?.create()
        }
        return helperSubComponent
    }

    fun searchComponent(): SearchSubComponent? {
        if (searchSubComponent == null) {
            searchSubComponent = likeMindsChatComponent?.searchSubComponent()?.create()
        }
        return searchSubComponent
    }
}