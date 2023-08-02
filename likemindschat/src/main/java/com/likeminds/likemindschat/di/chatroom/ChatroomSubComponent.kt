package com.likeminds.likemindschat.di.chatroom

import com.likeminds.likemindschat.chatroom.ChatroomClient
import dagger.Subcomponent

@Subcomponent
interface ChatroomSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChatroomSubComponent
    }

    fun inject(chatroomClient: ChatroomClient)
}