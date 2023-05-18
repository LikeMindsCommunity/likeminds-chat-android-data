package com.likeminds.likemindschat.base

import com.likeminds.internalsdk.CollabmatesChatSDK
import javax.inject.Inject

abstract class BaseClient {

    init {
        attachDagger()
    }


    @Inject
    lateinit var collabmatesChatSDK: CollabmatesChatSDK

    protected abstract fun attachDagger()
}