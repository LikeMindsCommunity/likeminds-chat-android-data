package com.likeminds.likemindschat.di.notification

import com.likeminds.likemindschat.notification.NotificationClient
import dagger.Subcomponent

@Subcomponent
interface NotificationSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): NotificationSubComponent
    }

    fun inject(notificationClient: NotificationClient)
}