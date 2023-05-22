package com.likeminds.likemindschat.sdk.util

import android.app.Application
import com.likeminds.internalsdk.di.SDKSharedResources
import javax.inject.Inject

internal class SDKSharedResourceImpl @Inject constructor(private val application: Application) :
    SDKSharedResources {
    override fun getApplication(): Application {
        return application
    }
}