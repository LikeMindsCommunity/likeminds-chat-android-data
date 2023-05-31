package com.likeminds.likemindschat.community

import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import javax.inject.Inject

class CommunityClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().communityComponent()?.inject(this)
    }
}