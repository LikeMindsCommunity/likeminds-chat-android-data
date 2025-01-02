package com.likeminds.chatinternalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.community.model._Community_
import com.likeminds.chatinternalsdk.community.model._Member_
import com.likeminds.chatinternalsdk.conversation.model._Attachment_
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.poll.model._Poll_
import com.likeminds.chatinternalsdk.widget.model._Widget_

data class _SyncConversationResponse_(
    @SerializedName("user_meta")
    val userMeta: Map<String, _Member_>?,
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
    @SerializedName("conversation_meta")
    val conversationMeta: Map<String, _Conversation_>,
    @SerializedName("conv_polls_meta")
    val conversationPollMeta: Map<String, List<_Poll_>>,
    @SerializedName("widgets")
    val widgets: Map<String, _Widget_>
)