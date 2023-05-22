package com.likeminds.likemindschat.base

import com.likeminds.internalsdk.GroupChatSDK
import javax.inject.Inject

abstract class BaseClient {
    init {
        attachDagger()
    }

    @Inject
    lateinit var groupChatSDK: GroupChatSDK

    protected abstract fun attachDagger()
}