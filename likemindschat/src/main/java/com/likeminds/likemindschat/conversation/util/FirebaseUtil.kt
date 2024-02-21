package com.likeminds.likemindschat.conversation.util

import android.util.Log
import com.google.firebase.database.*
import com.likeminds.likemindschat.chatroom.model.ChatroomFirebaseEntity
import com.likeminds.likemindschat.conversation.model.LiveConversationResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object FirebaseUtil {
    const val TAG = "FirebaseUtil"
    suspend fun DatabaseReference.childEventListener(): Flow<LiveConversationResponse> =
        callbackFlow {
            val childEventListener = object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(TAG, " --> onChildAdded:" + snapshot.key!! + snapshot.value.toString())
                    trySendBlocking(
                        LiveConversationResponse.ChildAdded(
                            snapshot.getValue(
                                ChatroomFirebaseEntity::class.java
                            )
                        )
                    )
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(TAG, " --> onChildChanged:" + snapshot.key!! + snapshot.value.toString())
                    trySendBlocking(
                        LiveConversationResponse.ChildChanged(
                            snapshot.getValue(
                                ChatroomFirebaseEntity::class.java
                            )
                        )
                    )
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    Log.d(TAG, " --> onChildRemoved:" + snapshot.key!! + snapshot.value.toString())
                    trySendBlocking(
                        LiveConversationResponse.ChildRemoved(
                            snapshot.getValue(
                                ChatroomFirebaseEntity::class.java
                            )
                        )
                    )
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.d(TAG, " --> onChildMoved:" + snapshot.key!! + snapshot.value.toString())
                    trySendBlocking(
                        LiveConversationResponse.ChildMoved(
                            snapshot.getValue(
                                ChatroomFirebaseEntity::class.java
                            )
                        )
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, " --> onCancelled:" + error.message)
                    trySendBlocking(
                        LiveConversationResponse.OnCancelled(error.message)
                    )
                }
            }
            addChildEventListener(childEventListener)
            awaitClose {
                removeEventListener(childEventListener)
            }
        }
}