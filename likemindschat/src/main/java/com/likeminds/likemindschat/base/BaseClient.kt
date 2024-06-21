package com.likeminds.likemindschat.base

import com.likeminds.internalsdk.LMChatSDK
import javax.inject.Inject

abstract class BaseClient {
    init {
        attachDagger()
    }

    @Inject
    lateinit var chatSDK: LMChatSDK

    protected abstract fun attachDagger()
}