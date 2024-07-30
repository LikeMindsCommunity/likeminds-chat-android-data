package com.likeminds.likemindschat.base

import com.likeminds.chatinternalsdk.LMChatSDK
import javax.inject.Inject

abstract class BaseClient {
    init {
        attachDagger()
    }

    @Inject
    lateinit var chatSDK: LMChatSDK

    protected abstract fun attachDagger()
}