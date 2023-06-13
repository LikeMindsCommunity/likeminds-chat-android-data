package com.likeminds.internalsdk.homefeed.db

import com.likeminds.internalsdk.homefeed.util._HomeFeedChangeListener_

interface HomeFeedDB {

    fun getChatrooms(
        homeFeedChangeListener_: _HomeFeedChangeListener_
    )
}