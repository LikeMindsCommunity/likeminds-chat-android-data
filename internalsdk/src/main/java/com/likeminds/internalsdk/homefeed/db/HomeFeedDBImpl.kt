package com.likeminds.internalsdk.homefeed.db

import com.likeminds.internalsdk.homefeed.HomeFeedReceiver
import com.likeminds.internalsdk.homefeed.util._HomeFeedChangeListener_
import javax.inject.Inject

class HomeFeedDBImpl @Inject constructor(private val homeFeedReceiver: HomeFeedReceiver) :
    HomeFeedDB {

    override fun getChatrooms(
        homeFeedChangeListener_: _HomeFeedChangeListener_
    ) {
        homeFeedReceiver.getChatrooms(homeFeedChangeListener_)
    }
}