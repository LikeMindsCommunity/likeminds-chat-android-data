package com.likeminds.internalsdk.sync.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model._Attachment_
import com.likeminds.internalsdk.conversation.model._Conversation_
import com.likeminds.internalsdk.poll.model._Poll_

data class _SyncConversationResponse_(
    @SerializedName("user_meta")
    var userMeta: Map<String, _Member_>,
    @SerializedName("community_meta")
    var communityMeta: Map<String, _Community_>,
    @SerializedName("chatroom_meta")
    var chatroomMeta: Map<String, _Chatroom_>,
    @SerializedName("conversations_data")
    var conversations: List<_Conversation_>,
    @SerializedName("chatroom_reactions_meta")
    var chatroomReactionsMeta: Map<String, List<_ReactionMeta_>>,
    @SerializedName("conv_reactions_meta")
    var conversationReactionMeta: Map<String, List<_ReactionMeta_>>,
    @SerializedName("conv_attachments_meta")
    var conversationAttachmentsMeta: Map<String, List<_Attachment_>>,
    @SerializedName("conv_polls_meta")
    var conversationPollMeta: Map<String, List<_Poll_>>
)