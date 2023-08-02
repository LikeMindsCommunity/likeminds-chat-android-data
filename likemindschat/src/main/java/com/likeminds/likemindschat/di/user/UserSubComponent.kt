package com.likeminds.likemindschat.di.user

import com.likeminds.likemindschat.user.UserClient
import dagger.Subcomponent

@Subcomponent
interface UserSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserSubComponent
    }

    fun inject(userClient: UserClient)
}