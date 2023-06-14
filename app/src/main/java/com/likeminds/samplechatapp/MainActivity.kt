package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)
        val client = LMChatClient.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            val initiateUserRequest = InitiateUserRequest.Builder()
                .apiKey("c4570b5a-46a4-4bb1-b82b-c89f4ea386c5")
                .userId("123456789")
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

            val config = client.getConfig()
            Log.d(
                TAG, """
                config response:
                microPoll: ${config.data?.enableMicroPolls}
                audio: ${config.data?.enableAudio}
            """.trimIndent()
            )


            withContext(Dispatchers.Main) {
                client.getChatrooms(this@MainActivity, listener)
            }
        }
    }
}