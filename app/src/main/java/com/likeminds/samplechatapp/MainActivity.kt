package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.user.model.EditProfileRequest
import com.likeminds.likemindschat.user.model.InitiateUserRequest
import com.likeminds.samplechatapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

            if (initiateResponse.data != null) {
                client.editProfile(
                    EditProfileRequest.Builder()
                        .name("Hey")
                        .imageUrl("https://www.google.com")
                        .build()
                )
            }

            Log.d(
                TAG, """
                initiateResponse: ${initiateResponse.data?.user?.id}
            """.trimIndent()
            )
        }
    }
}