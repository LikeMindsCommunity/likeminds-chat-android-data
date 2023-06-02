package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.poll.model._Poll_

class _Conversation_ private constructor(
    @SerializedName("id")
    val id: String?,
    @SerializedName("chatroom_id")
    val chatroomId: String?,
    @SerializedName("community_id")
    val communityId: String?,
    @SerializedName("member")
    val member: _Member_?,
    @SerializedName("answer")
    val answer: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("state")
    val state: Int,
    @SerializedName("attachments")
    val attachments: List<_Attachment_>?,
    @SerializedName("last_seen")
    val lastSeen: Boolean?,
    @SerializedName("og_tags")
    val ogTags: _LinkOGTags_?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("is_edited")
    val isEdited: Boolean?,
    @SerializedName("member_id")
    val memberId: String?,
    @SerializedName("reply_conversation")
    val replyConversation: String?,
    @SerializedName("delete_by")
    val deletedBy: String?,
    @SerializedName("created_epoch")
    val createdEpoch: Long?,
    @SerializedName("attachment_count")
    val attachmentCount: Int?,
    @SerializedName("attachments_uploaded")
    val attachmentUploaded: Boolean?,
    @SerializedName("upload_worker_uuid")
    val uploadWorkerUUID: String?,
    @SerializedName("temporary_id")
    val temporaryId: String?,
    @SerializedName("local_created_epoch")
    val localCreatedEpoch: Long?,
    @SerializedName("reactions")
    val reactions: List<_Reaction_>?,
    @SerializedName("is_anonymous")
    val isAnonymous: Boolean?,
    @SerializedName("allow_add_option")
    val allowAddOption: Boolean?,
    @SerializedName("poll_type")
    val pollType: Int?,
    @SerializedName("poll_type_text")
    val pollTypeText: String?,
    @SerializedName("submit_type_text")
    val submitTypeText: String?,
    @SerializedName("expiry_time")
    val expiryTime: Long?,
    @SerializedName("multiple_select_no")
    val multipleSelectNum: Int?,
    @SerializedName("multiple_select_state")
    val multipleSelectState: Int?,
    @SerializedName("polls")
    val polls: List<_Poll_>?,
    @SerializedName("to_show_results")
    val toShowResults: Boolean?,
    @SerializedName("poll_answer_text")
    val pollAnswerText: String?,
    @SerializedName("reply_chatroom_id")
    val replyChatroomId: String?
) {

    fun hasAttachments(): Boolean {
        return attachmentCount != null && attachmentCount > 0
    }

    fun hasReactions(): Boolean {
        return !reactions.isNullOrEmpty()
    }

    class Builder {

        private var id: String? = ""
        private var chatroomId: String? = null
        private var communityId: String? = null
        private var member: _Member_? = null
        private var answer: String = ""
        private var createdAt: String? = null
        private var state: Int = 0
        private var attachments: List<_Attachment_>? = null
        private var lastSeen: Boolean? = null
        private var ogTags: _LinkOGTags_? = null
        private var date: String? = null
        private var isEdited: Boolean? = null
        private var memberId: String? = null
        private var replyConversation: String? = null
        private var deletedBy: String? = null
        private var createdEpoch: Long? = null
        private var attachmentCount: Int? = null
        private var attachmentUploaded: Boolean? = null
        private var uploadWorkerUUID: String? = null
        private var temporaryId: String? = null
        private var localCreatedEpoch: Long? = null
        private var reactions: List<_Reaction_>? = null
        private var isAnonymous: Boolean? = null
        private var allowAddOption: Boolean? = null
        private var pollType: Int? = null
        private var pollTypeText: String? = null
        private var submitTypeText: String? = null
        private var expiryTime: Long? = null
        private var multipleSelectNum: Int? = null
        private var multipleSelectState: Int? = null
        private var polls: List<_Poll_>? = null
        private var toShowResults: Boolean? = null
        private var pollAnswerText: String? = null
        private var replyChatroomId: String? = null

        fun id(id: String?) = apply { this.id = id }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun communityId(communityId: String?) = apply { this.communityId = communityId }
        fun member(member: _Member_?) = apply { this.member = member }
        fun answer(answer: String) = apply { this.answer = answer }
        fun createdAt(createdAt: String?) = apply { this.createdAt = createdAt }
        fun state(state: Int) = apply { this.state = state }
        fun attachments(attachments: List<_Attachment_>?) = apply { this.attachments = attachments }
        fun lastSeen(lastSeen: Boolean?) = apply { this.lastSeen = lastSeen }
        fun ogTags(ogTags: _LinkOGTags_?) = apply { this.ogTags = ogTags }
        fun date(date: String?) = apply { this.date = date }
        fun isEdited(isEdited: Boolean?) = apply { this.isEdited = isEdited }
        fun memberId(memberId: String?) = apply { this.memberId = memberId }
        fun replyConversation(replyConversation: String?) =
            apply { this.replyConversation = replyConversation }

        fun deletedBy(deletedBy: String?) = apply { this.deletedBy = deletedBy }
        fun createdEpoch(createdEpoch: Long?) = apply { this.createdEpoch = createdEpoch }
        fun attachmentCount(attachmentCount: Int?) =
            apply { this.attachmentCount = attachmentCount }

        fun attachmentUploaded(attachmentUploaded: Boolean?) =
            apply { this.attachmentUploaded = attachmentUploaded }

        fun uploadWorkerUUID(uploadWorkerUUID: String?) =
            apply { this.uploadWorkerUUID = uploadWorkerUUID }

        fun temporaryId(temporaryId: String?) = apply { this.temporaryId = temporaryId }
        fun localCreatedEpoch(localCreatedEpoch: Long?) =
            apply { this.localCreatedEpoch = localCreatedEpoch }

        fun reactions(reactions: List<_Reaction_>?) = apply { this.reactions = reactions }
        fun isAnonymous(isAnonymous: Boolean?) = apply { this.isAnonymous = isAnonymous }
        fun allowAddOption(allowAddOption: Boolean?) =
            apply { this.allowAddOption = allowAddOption }

        fun pollType(pollType: Int?) = apply { this.pollType = pollType }
        fun pollTypeText(pollTypeText: String?) = apply { this.pollTypeText = pollTypeText }
        fun submitTypeText(submitTypeText: String?) = apply { this.submitTypeText = submitTypeText }
        fun expiryTime(expiryTime: Long?) = apply { this.expiryTime = expiryTime }
        fun multipleSelectNum(multipleSelectNum: Int?) =
            apply { this.multipleSelectNum = multipleSelectNum }

        fun multipleSelectState(multipleSelectState: Int?) =
            apply { this.multipleSelectState = multipleSelectState }

        fun polls(polls: List<_Poll_>?) = apply { this.polls = polls }
        fun toShowResults(toShowResults: Boolean?) = apply { this.toShowResults = toShowResults }
        fun pollAnswerText(pollAnswerText: String?) = apply { this.pollAnswerText = pollAnswerText }
        fun replyChatroomId(replyChatroomId: String?) =
            apply { this.replyChatroomId = replyChatroomId }

        fun build() = _Conversation_(
            id,
            chatroomId,
            communityId,
            member,
            answer,
            createdAt,
            state,
            attachments,
            lastSeen,
            ogTags,
            date,
            isEdited,
            memberId,
            replyConversation,
            deletedBy,
            createdEpoch,
            attachmentCount,
            attachmentUploaded,
            uploadWorkerUUID,
            temporaryId,
            localCreatedEpoch,
            reactions,
            isAnonymous,
            allowAddOption,
            pollType,
            pollTypeText,
            submitTypeText,
            expiryTime,
            multipleSelectNum,
            multipleSelectState,
            polls,
            toShowResults,
            pollAnswerText,
            replyChatroomId
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .chatroomId(chatroomId)
            .communityId(communityId)
            .member(member)
            .answer(answer)
            .createdAt(createdAt)
            .state(state)
            .attachments(attachments)
            .lastSeen(lastSeen)
            .ogTags(ogTags)
            .date(date)
            .isEdited(isEdited)
            .memberId(memberId)
            .replyConversation(replyConversation)
            .deletedBy(deletedBy)
            .createdEpoch(createdEpoch)
            .attachmentCount(attachmentCount)
            .attachmentUploaded(attachmentUploaded)
            .uploadWorkerUUID(uploadWorkerUUID)
            .temporaryId(temporaryId)
            .localCreatedEpoch(localCreatedEpoch)
            .reactions(reactions)
            .isAnonymous(isAnonymous)
            .allowAddOption(allowAddOption)
            .pollType(pollType)
            .pollTypeText(pollTypeText)
            .submitTypeText(submitTypeText)
            .expiryTime(expiryTime)
            .multipleSelectNum(multipleSelectNum)
            .multipleSelectState(multipleSelectState)
            .polls(polls)
            .toShowResults(toShowResults)
            .pollAnswerText(pollAnswerText)
            .replyChatroomId(replyChatroomId)
    }
}