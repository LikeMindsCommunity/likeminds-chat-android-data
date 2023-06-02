package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Community_

data class _SyncChatroomResponse_(
    @SerializedName("user_meta")
    var userMeta: Map<String, Member>,
    @SerializedName("conversation_meta")
    var conversationMeta: Map<String, Conversation>,
    @SerializedName("community_meta")
    var communityMeta: Map<String, _Community_>,
    @SerializedName("chatrooms_data")
    var chatrooms: List<Chatroom>,
    @SerializedName("conv_attachments_meta")
    var attachmentMeta: Map<String, List<CollabcardAttachment>>,
    @SerializedName("conv_polls_meta")
    var pollsMeta: Map<String, List<Poll>>
)