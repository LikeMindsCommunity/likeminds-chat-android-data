package com.likeminds.likemindschat.homefeed.util

import com.likeminds.likemindschat.chatroom.model.Chatroom

interface HomeFeedChangeListener {

    fun initialChatrooms(chatrooms: List<Chatroom>) {}
    fun changedChatrooms(
        removedIndex: List<Int>,
        inserted: List<Pair<Int, Chatroom>>,
        changed: List<Pair<Int, Chatroom>>
    ) {
    }

    fun error(throwable: Throwable) {}
}