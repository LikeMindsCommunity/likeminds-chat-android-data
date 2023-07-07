package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.chatroom.model.GetChatroomRequest
import com.likeminds.likemindschat.conversation.util.LoadConversationType
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.samplechatapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {

        const val TAG = "test_client"
    }

    private val listener = object : HomeFeedChangeListener {
        override fun initialChatrooms(chatrooms: List<Chatroom>) {
            super.initialChatrooms(chatrooms)
            Log.d(TAG, "MainActivity initial")
        }

        override fun changedChatrooms(
            removedIndex: List<Int>,
            inserted: List<Pair<Int, Chatroom>>,
            changed: List<Pair<Int, Chatroom>>
        ) {
            super.changedChatrooms(removedIndex, inserted, changed)
            Log.d(TAG, "MainActivity onChanged")
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
                .apiKey("62723803-8577-4314-b3bd-c65dce56c1df")
                .userId("siddharth-4")
                .userName("Sid")
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
        }

        binding.tvClick.setOnClickListener {
            val worker = client.loadConversations(
                this@MainActivity,
                LoadConversationType.FIRST_TIME,
                "74936"
            )

            worker.observe(this) { state ->
                Log.d(TAG, "loadConversation: $state")
            }
        }
    }
}