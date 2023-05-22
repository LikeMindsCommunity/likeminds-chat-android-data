package com.likeminds.internalsdk.community

import javax.inject.Inject

class CommunityApiImpl @Inject constructor(private val communityReceiver: CommunityReceiver) :
    CommunityApi {
}