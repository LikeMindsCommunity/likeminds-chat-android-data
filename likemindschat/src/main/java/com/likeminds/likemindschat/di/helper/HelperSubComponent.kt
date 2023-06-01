package com.likeminds.likemindschat.di.helper

import com.likeminds.likemindschat.helper.HelperClient
import dagger.Subcomponent

@Subcomponent
interface HelperSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HelperSubComponent
    }

    fun inject(helperClient: HelperClient)
}