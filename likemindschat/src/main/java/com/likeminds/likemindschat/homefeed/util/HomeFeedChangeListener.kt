package com.likeminds.likemindschat.homefeed.util

import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.sdk.ModelConverter
import io.realm.OrderedCollectionChangeSet
import io.realm.RealmResults

abstract class HomeChatroomListener {

    private var collection: RealmResults<ChatroomRO>? = null

    /**
     * Provides the initial chatrooms which contains [collection]
     */
    abstract fun initial(chatrooms: List<Chatroom>)

    /**
     * Invokes when data set changes
     * @param removedIndex Chatroom id of all the removed chatrooms from the collection
     * @param inserted List of all newly added chatrooms with index and chatroom object in a [Pair], Reversed with index
     * @param changed List of all updated chatrooms with index and chatroom object in a [Pair]
     */
    abstract fun onChanged(
        removedIndex: List<Int>,
        inserted: List<Pair<Int, Chatroom>>,
        changed: List<Pair<Int, Chatroom>>
    )

    /**
     * Realm change listener gave some error
     */
    abstract fun onError(throwable: Throwable)

    fun onChange(
        results: RealmResults<ChatroomRO>,
        changeSet: OrderedCollectionChangeSet
    ) {
        when (changeSet.state) {
            OrderedCollectionChangeSet.State.INITIAL -> {
                collection = results
                val chatrooms = results.mapNotNull {
                    ModelConverter.convertChatroomRO(it)
                }
                initial(chatrooms)
            }
            OrderedCollectionChangeSet.State.UPDATE -> {
                collection = results
                val insertions = getIndexedChatrooms(changeSet.insertions)
                val changes = getIndexedChatrooms(changeSet.changes)
                onChanged(changeSet.deletions.reversed(), insertions, changes)
            }
            else -> {
                //Some error occurred
                val throwable = changeSet.error ?: Throwable("Unknown error occurred")
                onError(throwable)
            }
        }
    }

    private fun getIndexedChatrooms(indexArray: IntArray): List<Pair<Int, Chatroom>> {
        return indexArray.toList().mapNotNull { index ->
            val chatroomRO = collection?.get(index)
            val chatroom = ModelConverter.convertChatroomRO(chatroomRO)
            return@mapNotNull if (chatroom != null) {
                Pair(index, chatroom)
            } else {
                null
            }
        }
    }

    /**
     * Clears the realm results and is ready to be garbage collected
     */
    fun clear() {
        collection?.removeAllChangeListeners()
        collection = null
    }

}