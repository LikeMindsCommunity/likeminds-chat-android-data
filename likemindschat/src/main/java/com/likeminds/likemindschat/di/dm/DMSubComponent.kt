package com.likeminds.likemindschat.di.dm

import com.likeminds.likemindschat.dm.DMClient
import dagger.Subcomponent

@Subcomponent
interface DMSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DMSubComponent
    }

    fun inject(dmClient: DMClient)
}