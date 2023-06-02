package com.likeminds.likemindschat.di.moderation

import com.likeminds.likemindschat.moderation.ModerationClient
import dagger.Subcomponent

@Subcomponent
interface ModerationSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ModerationSubComponent
    }

    fun inject(moderationClient: ModerationClient)
}