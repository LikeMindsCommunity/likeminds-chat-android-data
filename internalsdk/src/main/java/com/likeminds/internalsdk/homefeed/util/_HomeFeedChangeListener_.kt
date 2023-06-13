package com.likeminds.internalsdk.homefeed.util

import android.util.Log
import com.likeminds.internalsdk.db.models.ChatroomRO
import io.realm.*

abstract class _HomeFeedChangeListener_ :
    OrderedRealmCollectionChangeListener<RealmResults<ChatroomRO>> {

    private var collection: RealmResults<ChatroomRO>? = null

    init {
        Log.d("PUI", "abstract init")
    }

    abstract fun initial(chatrooms: RealmResults<ChatroomRO>)

    /**
     * Invokes when data set changes
     * @param removedIndex Chatroom id of all the removed chatrooms from the collection
     * @param inserted List of all newly added chatrooms with index and chatroom object in a [Pair], Reversed with index
     * @param changed List of all updated chatrooms with index and chatroom object in a [Pair]
     */
    abstract fun onChanged(
        removedIndex: List<Int>,
        inserted: List<Pair<Int, ChatroomRO>>,
        changed: List<Pair<Int, ChatroomRO>>
    )

    /**
     * Realm change listener gave some error
     */
    abstract fun onError(throwable: Throwable)

    override fun onChange(
        results: RealmResults<ChatroomRO>,
        changeSet: OrderedCollectionChangeSet
    ) {
        when (changeSet.state) {
            OrderedCollectionChangeSet.State.INITIAL -> {
                Log.d("PUI", " OrderedCollectionChangeSet.State.INITIAL: ${results.size}")
                collection = results
                initial(collection!!)
            }

            OrderedCollectionChangeSet.State.UPDATE -> {
                Log.d("PUI", "OrderedCollectionChangeSet.State.UPDATE: ${results.size}")
                collection = results
                val insertions = getIndexedChatrooms(changeSet.insertions)
                val changes = getIndexedChatrooms(changeSet.changes)
                onChanged(changeSet.deletions.reversed(), insertions, changes)
            }

            else -> {
                //Some error occurred
                val throwable = changeSet.error ?: Throwable("Unknown error occurred")
                Log.d("PUI", "error in abstract: ${throwable.message}")
                onError(throwable)
            }
        }
    }

    private fun getIndexedChatrooms(indexArray: IntArray): List<Pair<Int, ChatroomRO>> {
        return indexArray.toList().mapNotNull { index ->
            val chatroom = collection?.get(index)
            return@mapNotNull if (chatroom != null) {
                Pair(index, chatroom)
            } else {
                null
            }
        }
    }
}