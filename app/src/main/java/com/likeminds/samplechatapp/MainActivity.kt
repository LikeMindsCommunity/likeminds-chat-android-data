package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.chatroom.model.GetChatroomRequest
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {

        const val TAG = "test_client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val client = LMChatClient.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            val initiateUserRequest = InitiateUserRequest.Builder()
                .apiKey("c4570b5a-46a4-4bb1-b82b-c89f4ea386c5")
                .userId("1234")
                .userName("Siddharth Dubey")
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

            val getChatroomRequest = GetChatroomRequest.Builder()
                .chatroomId("82825")
                .build()
            val getChatroomResponse = client.getChatroom(getChatroomRequest)

            Log.d(TAG, "getChatroomResponse:${getChatroomResponse.data}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "chatroom participants: ${getChatroomResponse.data?.participantCount}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}