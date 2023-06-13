package com.likeminds.likemindschat.homefeed.util

import com.likeminds.likemindschat.chatroom.model.Chatroom

interface HomeFeedChangeListener {

    fun initial(chatrooms: List<Chatroom>) {}
    fun onChanged(
        removedIndex: List<Int>,
        inserted: List<Pair<Int, Chatroom>>,
        changed: List<Pair<Int, Chatroom>>
    ) {
    }

    fun onError(throwable: Throwable) {}
}