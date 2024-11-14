package com.likeminds.likemindschat.conversation.worker

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.conversation.model._PostConversationRequest_
import com.likeminds.chatinternalsdk.conversation.model._SavePostedConversationRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.conversation.model.PostConversationRequest
import com.likeminds.likemindschat.conversation.model.PostConversationResponse
import com.likeminds.likemindschat.sdk.ModelConverter
import java.util.concurrent.TimeUnit

class CreateConversationWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val chatSDK = LMChatSDK.getInstance()
    private val conversationApi = chatSDK.getConversationApi()
    private val conversationDB = chatSDK.getConversationDB()
    private val gson by lazy {
        Gson()
    }

    companion object {
        const val NAME = "Create Conversation Worker"

        const val INPUT_POST_CONVERSATION_REQUEST = "post_conversation_request"
        const val OUTPUT_POST_CONVERSATION_RESPONSE = "post_conversation_response"

        //All work manager will run only if internet connection is stable
        private val networkConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        fun getInstance(inputData: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<CreateConversationWorker>()
                .setInputData(
                    workDataOf(INPUT_POST_CONVERSATION_REQUEST to inputData)
                )
                .setConstraints(networkConstraint)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .addTag(NAME)
                .build()
        }
    }

    override suspend fun doWork(): Result {
        return createConversation()
    }

    private suspend fun createConversation(): Result {
        val inputString =
            inputData.getString(INPUT_POST_CONVERSATION_REQUEST) ?: return Result.failure()
        val postConversationRequest =
            gson.fromJson(inputString, PostConversationRequest::class.java)

        //create internal api request
        val requestBuilder = _PostConversationRequest_.Builder()
            .chatroomId(postConversationRequest.chatroomId)
            .text(postConversationRequest.text)
            .shareLink(postConversationRequest.shareLink)
            .ogTags(ModelConverter.createLinkOGTags(postConversationRequest.ogTags))
            .repliedConversationId(postConversationRequest.repliedConversationId)
            .temporaryId(postConversationRequest.temporaryId)
            .repliedChatroomId(postConversationRequest.repliedChatroomId)
            .attachments(ModelConverter.createAttachments(postConversationRequest.attachments))

        if (postConversationRequest.metadata != null) {
            requestBuilder.metadata(JsonParser.parseString(postConversationRequest.metadata.toString()).asJsonObject)
        }

        if (postConversationRequest.triggerBot) {
            requestBuilder.triggerBot(true)
        }

        val request = requestBuilder.build()

        //call api
        return when (val response = conversationApi.postConversation(request)) {
            is NetworkResponse.Error -> {
                val lmResponse = LMResponse<PostConversationResponse>(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )

                // Serialize response to JSON
                val outputJson = gson.toJson(lmResponse)

                // Pass the created conversation as output
                val outputData = workDataOf(OUTPUT_POST_CONVERSATION_RESPONSE to outputJson)

                Result.failure(outputData)
            }

            is NetworkResponse.Success -> {
                val body = response.body

                val data = body.data
                val conversation = data?.conversation
                if (conversation != null) {
                    //Get widget from widgetMap and add it to updatedConversation
                    val widgetId = conversation.widgetId
                    val widget = data.widgets[widgetId]

                    //update conversation with widget
                    val updatedConversation = conversation.toBuilder()
                        .widget(widget)
                        .build()

                    //create save conversation request
                    val saveConversationRequest = _SavePostedConversationRequest_.Builder()
                        .conversation(updatedConversation)
                        .isFromNotification(false)
                        .build()

                    //save conversation to db
                    conversationDB.savePostedConversation(saveConversationRequest)

                    //create lm response
                    val lmResponse = ModelConverter.convertPostConversationAPIResponse(body)

                    // Serialize response to JSON
                    val outputJson = gson.toJson(lmResponse)

                    // Pass the created conversation as output
                    val outputData = workDataOf(OUTPUT_POST_CONVERSATION_RESPONSE to outputJson)

                    Result.success(outputData)
                } else {
                    val lmResponse = LMResponse<PostConversationResponse>(
                        success = response.body.success,
                        errorMessage = response.body.errorMessage,
                    )

                    // Serialize response to JSON
                    val outputJson = gson.toJson(lmResponse)

                    // Pass the created conversation as output
                    val outputData = workDataOf(OUTPUT_POST_CONVERSATION_RESPONSE to outputJson)

                    Result.failure(outputData)
                }
            }
        }
    }
}