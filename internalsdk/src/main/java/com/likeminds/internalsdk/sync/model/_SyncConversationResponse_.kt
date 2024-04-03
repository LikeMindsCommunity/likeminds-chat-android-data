package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model._Attachment_
import com.likeminds.internalsdk.conversation.model._Conversation_
import com.likeminds.internalsdk.poll.model._Poll_
import com.likeminds.internalsdk.widget.model._Widget_

data class _SyncConversationResponse_(
    @SerializedName("user_meta")
    val userMeta: Map<String, _Member_>,
    @SerializedName("community_meta")
    val communityMeta: Map<String, _Community_>,
    @SerializedName("chatroom_meta")
    val chatroomMeta: Map<String, _Chatroom_>,
    @SerializedName("conversations_data")
    val conversations: List<_Conversation_>,
    @SerializedName("chatroom_reactions_meta")
    val chatroomReactionsMeta: Map<String, List<_ReactionMeta_>>,
    @SerializedName("conv_reactions_meta")
    val conversationReactionMeta: Map<String, List<_ReactionMeta_>>,
    @SerializedName("conv_attachments_meta")
    val conversationAttachmentsMeta: Map<String, List<_Attachment_>>,
    @SerializedName("conv_polls_meta")
    val conversationPollMeta: Map<String, List<_Poll_>>,
    @SerializedName("widgets")
    val widgets: Map<String, _Widget_>
)