package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.poll.model._Poll_

data class _SyncChatroomResponse_(
    @SerializedName("user_meta")
    var userMeta: Map<String, _Member_>,
    @SerializedName("conversation_meta")
    var conversationMeta: Map<String, _Conversation_>,
    @SerializedName("community_meta")
    var communityMeta: Map<String, _Community_>,
    @SerializedName("chatrooms_data")
    var chatrooms: List<_Chatroom_>,
    @SerializedName("conv_attachments_meta")
    var attachmentMeta: Map<String, List<_Attachment_>>,
    @SerializedName("conv_polls_meta")
    var pollsMeta: Map<String, List<_Poll_>>
)