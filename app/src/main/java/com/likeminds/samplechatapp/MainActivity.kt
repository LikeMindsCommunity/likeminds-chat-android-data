package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import kotlinx.coroutines.*

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
                .apiKey("69edd43f-4a5e-4077-9c50-2b7aa740acce")
                .userId("10003")
                .userName("Ishaan Jain")
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
            val count = client.getExploreTabCount()

            Log.d(
                TAG, """
                count total: ${count.data?.totalChatroomCount}
                count new: ${count.data?.unseenChatroomCount}
            """.trimIndent()
            )
            val config = client.getConfig()
            Log.d(
                TAG, """
                config audio: ${config.data?.enableAudio}
                config polls: ${config.data?.enableMicroPolls}
            """.trimIndent()
            )
        }
    }
}