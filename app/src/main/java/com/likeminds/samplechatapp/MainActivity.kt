package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.conversation.model.PostConversationRequest
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.samplechatapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "test_chat_data"
    }

    private lateinit var binding: ActivityMainBinding
    private val client by lazy { LMChatClient.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            val initiateResponse = client.initiateUser(
                InitiateUserRequest.Builder()
                    .apiKey("5793da15-d18d-43f3-a817-466813b675ea")
                    .userId("a")
                    .deviceId("adadad")
                    .userName("Ishaan")
                    .isGuest(false)
                    .build()
            )

            Log.d(
                TAG, """
                initiateResponse: ${initiateResponse.data?.user?.id}
            """.trimIndent()
            )
        }

        binding.tvClick.setOnClickListener {
            createConversation()
        }
    }

    private fun createConversation() {
        CoroutineScope(Dispatchers.IO).launch {
            val metadata = JSONObject().apply {
                put("key_1", "900")
                put("key_2", 80)
                put("key_3", "ishaan")
            }

            val postConversationResponse = client.postConversation(
                PostConversationRequest.Builder()
                    .chatroomId("96567")
                    .text("Hey")
                    .metadata(metadata)
                    .build()
            )

            val widgetId = postConversationResponse.data?.conversation?.widgetId

            Log.d(
                TAG, """
                postConversationResponse
                errorMessage:${postConversationResponse.errorMessage}
                id: ${postConversationResponse.data?.id}
                widgetId: $widgetId
                widget: ${postConversationResponse.data?.widgets?.get(widgetId).toString()}
            """.trimIndent()
            )
        }
    }
}