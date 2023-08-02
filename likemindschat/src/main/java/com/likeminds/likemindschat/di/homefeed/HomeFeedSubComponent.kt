package com.likeminds.likemindschat.di.homefeed

import com.likeminds.likemindschat.homefeed.HomeFeedClient
import dagger.Subcomponent

@Subcomponent
interface HomeFeedSubComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): HomeFeedSubComponent
    }

    fun inject(homeFeedClient: HomeFeedClient)
}