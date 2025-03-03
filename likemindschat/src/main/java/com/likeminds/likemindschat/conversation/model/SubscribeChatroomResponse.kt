package com.likeminds.likemindschat.conversation.model

import com.google.gson.annotations.SerializedName

data class SubscribeChatroomResponse(
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("topic_message_type")
    val topicMessageType: String,
    @SerializedName("raw_data")
    val rawData: String
)
