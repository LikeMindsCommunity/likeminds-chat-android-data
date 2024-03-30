package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.poll.model._Poll_
import com.likeminds.internalsdk.widget.model._Widget_

data class _SyncChatroomResponse_(
    @SerializedName("user_meta")
    val userMeta: Map<String, _Member_>,
    @SerializedName("conversation_meta")
    val conversationMeta: Map<String, _Conversation_>,
    @SerializedName("community_meta")
    val communityMeta: Map<String, _Community_>,
    @SerializedName("chatrooms_data")
    val chatrooms: List<_Chatroom_>,
    @SerializedName("conv_attachments_meta")
    val attachmentMeta: Map<String, List<_Attachment_>>,
    @SerializedName("conv_polls_meta")
    val pollsMeta: Map<String, List<_Poll_>>,
    @SerializedName("widgets")
    val widgets: Map<String, _Widget_>
)