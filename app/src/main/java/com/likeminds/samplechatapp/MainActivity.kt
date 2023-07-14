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
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.chatroom.model.GetChatroomRequest
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.likemindschat.poll.model.GetPollUsersRequest
import com.likeminds.likemindschat.search.model.SearchChatroomRequest
import com.likeminds.likemindschat.search.model.SearchConversationRequest
import com.likeminds.samplechatapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {

        const val TAG = "test_client"
    }

    private val TIME_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("HH:mm")
        }
    }

    private val DATE_SDF_1 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM yyyy")
        }
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
                TAG,
                "MainActivity onChanged, inserted: ${inserted.map { it.second.id }}  changed: ${changed.map { it.second.id }}"
            )
        }

        override fun error(throwable: Throwable) {
            super.error(throwable)
            Log.d(TAG, "MainActivity onError")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            val getChatroom = client.getChatroom(
                GetChatroomRequest.Builder()
                    .chatroomId("74936")
                    .build()
            )
            Log.d(TAG, "getChatroom: ${getChatroom.data?.chatroom}")

            withContext(Dispatchers.Main) {
                client.getChatrooms(this@MainActivity, listener)
            }
        }

        binding.tvClick.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val tempConv = Conversation.Builder()
                    .id("-${System.currentTimeMillis()}")
                    .temporaryId("-${System.currentTimeMillis()}")
                    .answer("Hey 123444")
                    .chatroomId("74936")
                    .communityId("89898989")
                    .createdAt(TIME_SDF.get()?.format(Date()) ?: "")
                    .state(0)
                    .createdEpoch(System.currentTimeMillis())
                    .memberId("siddharth-4")
                    .lastSeen(true)
                    .attachmentCount(0)
                    .attachments(emptyList())
                    .attachmentUploaded(false)
                    .date(DATE_SDF_1.get()?.format(Date()) ?: "")
                    .build()

                client.saveTemporaryConversation(
                    SaveConversationRequest.Builder().conversation(tempConv).build()
                )

                val postConversationResponse =
                    client.postConversation(
                        PostConversationRequest.Builder()
                            .chatroomId("74936")
                            .text("99000000 uyi")
                            .temporaryId("-${System.currentTimeMillis()}")
                            .build()
                    )

                Log.d(
                    TAG, """
                postConversationResponse: ${postConversationResponse.data?.id}
            """.trimIndent()
                )
            }
        }
    }
}