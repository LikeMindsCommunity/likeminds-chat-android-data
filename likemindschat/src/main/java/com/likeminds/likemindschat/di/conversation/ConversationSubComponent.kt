package com.likeminds.likemindschat.di.conversation

import com.likeminds.likemindschat.conversation.ConversationClient
import dagger.Subcomponent

@Subcomponent
interface ConversationSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ConversationSubComponent
    }

    fun inject(conversationClient: ConversationClient)
}