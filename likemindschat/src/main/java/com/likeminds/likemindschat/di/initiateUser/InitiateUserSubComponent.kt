package com.likeminds.likemindschat.di.initiateUser

import com.likeminds.likemindschat.initiateUser.InitiateUserClient
import dagger.Subcomponent

@Subcomponent
interface InitiateUserSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): InitiateUserSubComponent
    }

    fun inject(initiateUserClient: InitiateUserClient)
}