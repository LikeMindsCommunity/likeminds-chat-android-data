package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.model.GetExploreFeedRequest
import com.likeminds.likemindschat.conversation.model.PostConversationRequest
import com.likeminds.likemindschat.helper.model.GetTaggingListRequest
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.likemindschat.search.model.SearchChatroomRequest
import com.likeminds.likemindschat.search.model.SearchConversationRequest
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {

        const val TAG = "test_client"
    }

    private val listener = object : HomeFeedChangeListener {
        override fun initialChatrooms(chatrooms: List<Chatroom>) {
            super.initialChatrooms(chatrooms)
            Log.d(TAG, "MainActivity initial" +
                    "${
                        chatrooms.map {
                            it.member?.sdkClientInfo?.uuid
                        }
                    }"
            )
        }

        override fun changedChatrooms(
            removedIndex: List<Int>,
            inserted: List<Pair<Int, Chatroom>>,
            changed: List<Pair<Int, Chatroom>>
        ) {
            super.changedChatrooms(removedIndex, inserted, changed)
            Log.d(
                TAG, "MainActivity onChanged" +
                        """
                        inserted: ${
                            inserted.map {
                                it.second.member?.sdkClientInfo?.uuid
                            }
                        }
                        
                        changed: ${
                            changed.map {
                                it.second.member?.sdkClientInfo?.uuid
                            }
                        }
                    """.trimIndent()
            )
        }

        override fun error(throwable: Throwable) {
            super.error(throwable)
            Log.d(TAG, "MainActivity onError")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = LMChatClient.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            val initiateUserRequest = InitiateUserRequest.Builder()
                .apiKey("bc7017d0-8d17-4ce6-8951-5d580755fb68")
                .userId("785555")
                .userName("Ishaan")
                .deviceId("123333")
                .isGuest(false)
                .build()
            val initiateResponse = client.initiateUser(initiateUserRequest)

            Log.d(TAG, "initiateResponse:${initiateResponse.data?.user?.id}")
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "User id: ${initiateResponse.data?.user?.id}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val uuid = initiateResponse.data?.user?.sdkClientInfo?.uuid ?: ""

            val followResponse = client.followChatroom(
                FollowChatroomRequest.Builder()
                    .value(true)
                    .chatroomId("82318")
                    .uuid(uuid)
                    .build()
            )

            Log.d(TAG, "followResponse :${followResponse.success}")

            val participantsResponse =
                client.getParticipants(
                    GetParticipantsRequest.Builder()
                        .chatroomId("82318")
                        .page(1)
                        .pageSize(10)
                        .isChatroomSecret(false)
                        .build()
                )

            Log.d(
                TAG, "participants response: ${
                    participantsResponse.data?.participants?.map {
                        it.sdkClientInfo?.uuid
                    }
                }"
            )

            val communityFeedResponse = client.getExploreFeed(
                GetExploreFeedRequest.Builder()
                    .page(1)
                    .isPinned(false)
                    .orderType(0)
                    .build()
            )

            Log.d(
                TAG, """
                communityFeedResponse: ${
                    communityFeedResponse.data?.chatrooms?.map {
                        it.member?.sdkClientInfo?.uuid
                    }
                }
            """.trimIndent()
            )

            val configResponse = client.getConfig()
            Log.d(
                TAG, """
                configResponse: ${configResponse.data?.userDetails?.user?.sdkClientInfo?.uuid}
            """.trimIndent()
            )

            val searchChatroomResponse =
                client.searchChatroom(
                    SearchChatroomRequest.Builder()
                        .search("chat")
                        .searchType("title")
                        .page(1)
                        .followStatus(true)
                        .pageSize(10)
                        .build()
                )

            Log.d(
                TAG, """
                searchChatroomResponse: ${searchChatroomResponse.data?.chatrooms}
            """.trimIndent()
            )

            val searchConversationResponse =
                client.searchConversation(
                    SearchConversationRequest.Builder()
                        .page(1)
                        .pageSize(10)
                        .followStatus(true)
                        .search("Hey")
                        .build()
                )

            Log.d(
                TAG, """
                searchConversationResponse: ${searchConversationResponse.data?.conversations}
            """.trimIndent()
            )

            val taggingListResponse = client.getTaggingList(
                GetTaggingListRequest.Builder()
                    .page(1)
                    .pageSize(10)
                    .chatroomId("82318")
                    .build()
            )

            Log.d(
                TAG, """
                taggingListResponse:${
                    taggingListResponse.data?.communityMembers?.map {
                        it.sdkClientInfo?.uuid
                    }
                }
            """.trimIndent()
            )

            val memberStateResponse = client.getMemberState()

            Log.d(
                TAG, """
                memberStateResponse: ${memberStateResponse.data?.state}
                memberStateResponse: ${
                    memberStateResponse.data?.memberRights?.map {
                        it.title
                    }
                }
            """.trimIndent()
            )

            val postConversationResponse =
                client.postConversation(
                    PostConversationRequest.Builder()
                        .chatroomId("82318")
                        .temporaryId("-${System.currentTimeMillis()}")
                        .text("Hey From test data layer")
                        .build()
                )

            Log.d(
                TAG, """
                postConversationResponse: ${postConversationResponse.data?.conversation?.member?.sdkClientInfo?.uuid}
            """.trimIndent()
            )
        }
    }
}