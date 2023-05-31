package com.likeminds.likemindschat.di.community

import com.likeminds.likemindschat.community.CommunityClient
import dagger.Subcomponent

@Subcomponent
interface CommunitySubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CommunitySubComponent
    }

    fun inject(communityClient: CommunityClient)
}