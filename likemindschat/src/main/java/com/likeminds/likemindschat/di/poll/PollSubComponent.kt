package com.likeminds.likemindschat.di.poll

import com.likeminds.likemindschat.poll.PollClient
import dagger.Subcomponent

@Subcomponent
interface PollSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PollSubComponent
    }

    fun inject(pollClient: PollClient)
}