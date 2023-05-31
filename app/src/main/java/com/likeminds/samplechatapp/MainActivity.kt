package com.likeminds.samplechatapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.chatroom.model.*
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

            val leaveSecretChatroomRequest = LeaveSecretChatroomRequest.Builder()
                .chatroomId(82910)
                .isSecret(true)
                .build()
            val leaveSecretChatroomResponse = client.leaveSecretChatroom(leaveSecretChatroomRequest)

            Log.d(TAG, "leaveSecretChatroomResponse:${leaveSecretChatroomResponse}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "leave: ${leaveSecretChatroomResponse.success}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val muteChatroomRequest = MuteChatroomRequest.Builder()
                .chatroomId(82825)
                .value(true)
                .build()
            val muteChatroomResponse = client.muteChatroom(muteChatroomRequest)

            Log.d(TAG, "muteChatroomResponse:${muteChatroomResponse}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "muteChatroom: ${muteChatroomResponse.success}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val markReadChatroomRequest = MarkReadChatroomRequest.Builder()
                .chatroomId(82825)
                .build()
            val markReadChatroomResponse = client.markReadChatroom(markReadChatroomRequest)

            Log.d(TAG, "markReadChatroomResponse:${markReadChatroomResponse}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "markReadChatroom: ${markReadChatroomResponse.success}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val shareChatroomUrlRequest = ShareChatroomUrlRequest.Builder()
                .chatroomId("82825")
                .domain("https://www.sample.com")
                .build()
            val shareChatroomUrlResponse = client.shareChatroomUrl(shareChatroomUrlRequest)

            Log.d(TAG, "shareChatroomUrlResponse:${shareChatroomUrlResponse}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "markReadChatroom: ${shareChatroomUrlResponse.data?.shareChatroomUrl?.shareUrl}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val getChatroomParticipantsRequest = GetChatroomParticipantsRequest.Builder()
                .isChatroomSecret(false)
                .chatroomId("82825")
                .page(1)
                .pageSize(1)
                .build()
            val getChatroomParticipantsResponse =
                client.getChatroomParticipants(getChatroomParticipantsRequest)

            Log.d(TAG, "getChatroomParticipantsResponse:${getChatroomParticipantsResponse}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "markReadChatroom: ${getChatroomParticipantsResponse.data?.totalParticipantsCount}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}