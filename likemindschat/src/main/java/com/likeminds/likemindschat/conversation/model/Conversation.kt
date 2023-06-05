package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.community.model.Member
import com.likeminds.likemindschat.poll.model.Poll

class Conversation private constructor(
    val id: String?,
    val chatroomId: String?,
    val communityId: String?,
    val member: Member?,
    val answer: String,
    val createdAt: String?,
    val state: Int,
    val attachments: List<Attachment>?,
    val lastSeen: Boolean?,
    val ogTags: LinkOGTags?,
    val date: String?,
    val isEdited: Boolean?,
    val memberId: String?,
    val replyConversationId: String?,
    val deletedBy: String?,
    val createdEpoch: Long?,
    val attachmentCount: Int?,
    val attachmentUploaded: Boolean?,
    val uploadWorkerUUID: String?,
    val temporaryId: String?,
    val localCreatedEpoch: Long?,
    val reactions: List<Reaction>?,
    val isAnonymous: Boolean?,
    val allowAddOption: Boolean?,
    val pollType: Int?,
    val pollTypeText: String?,
    val submitTypeText: String?,
    val expiryTime: Long?,
    val multipleSelectNum: Int?,
    val multipleSelectState: Int?,
    val polls: List<Poll>?,
    val toShowResults: Boolean?,
    val pollAnswerText: String?,
    val replyChatroomId: String?,
    val deviceId: String?,
    val hasFiles: Boolean?,
    val hasReactions: Boolean?,
    val lastUpdated: Long?,
) {

    class Builder {
        private var id: String? = ""
        private var chatroomId: String? = null
        private var communityId: String? = null
        private var member: Member? = null
        private var answer: String = ""
        private var createdAt: String? = null
        private var state: Int = 0
        private var attachments: List<Attachment>? = null
        private var lastSeen: Boolean? = null
        private var ogTags: LinkOGTags? = null
        private var date: String? = null
        private var isEdited: Boolean? = null
        private var memberId: String? = null
        private var replyConversationId: String? = null
        private var deletedBy: String? = null
        private var createdEpoch: Long? = null
        private var attachmentCount: Int? = null
        private var attachmentUploaded: Boolean? = null
        private var uploadWorkerUUID: String? = null
        private var temporaryId: String? = null
        private var localCreatedEpoch: Long? = null
        private var reactions: List<Reaction>? = null
        private var isAnonymous: Boolean? = null
        private var allowAddOption: Boolean? = null
        private var pollType: Int? = null
        private var pollTypeText: String? = null
        private var submitTypeText: String? = null
        private var expiryTime: Long? = null
        private var multipleSelectNum: Int? = null
        private var multipleSelectState: Int? = null
        private var polls: List<Poll>? = null
        private var toShowResults: Boolean? = null
        private var pollAnswerText: String? = null
        private var replyChatroomId: String? = null
        private var deviceId: String? = null
        private var hasFiles: Boolean? = false
        private var hasReactions: Boolean? = false
        private var lastUpdated: Long? = null

        fun id(id: String?) = apply { this.id = id }
        fun chatroomId(chatroomId: String?) = apply { this.chatroomId = chatroomId }
        fun communityId(communityId: String?) = apply { this.communityId = communityId }
        fun member(member: Member?) = apply { this.member = member }
        fun answer(answer: String) = apply { this.answer = answer }
        fun createdAt(createdAt: String?) = apply { this.createdAt = createdAt }
        fun state(state: Int) = apply { this.state = state }
        fun attachments(attachments: List<Attachment>?) = apply { this.attachments = attachments }
        fun lastSeen(lastSeen: Boolean?) = apply { this.lastSeen = lastSeen }
        fun ogTags(ogTags: LinkOGTags?) = apply { this.ogTags = ogTags }
        fun date(date: String?) = apply { this.date = date }
        fun isEdited(isEdited: Boolean?) = apply { this.isEdited = isEdited }
        fun memberId(memberId: String?) = apply { this.memberId = memberId }
        fun replyConversationId(replyConversationId: String?) =
            apply { this.replyConversationId = replyConversationId }

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

        fun reactions(reactions: List<Reaction>?) = apply { this.reactions = reactions }
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

        fun polls(polls: List<Poll>?) = apply { this.polls = polls }
        fun toShowResults(toShowResults: Boolean?) = apply { this.toShowResults = toShowResults }
        fun pollAnswerText(pollAnswerText: String?) = apply { this.pollAnswerText = pollAnswerText }
        fun replyChatroomId(replyChatroomId: String?) =
            apply { this.replyChatroomId = replyChatroomId }

        fun deviceId(deviceId: String?) = apply { this.deviceId = deviceId }
        fun hasFiles(hasFiles: Boolean?) = apply { this.hasFiles = hasFiles }
        fun hasReactions(hasReactions: Boolean?) = apply { this.hasReactions = hasReactions }
        fun lastUpdated(lastUpdated: Long?) = apply { this.lastUpdated = lastUpdated }
        fun build() = Conversation(
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
            replyConversationId,
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
            replyChatroomId,
            deviceId,
            hasFiles,
            hasReactions,
            lastUpdated
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
            .replyConversationId(replyConversationId)
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
            .deviceId(deviceId)
            .hasFiles(hasFiles)
            .hasReactions(hasReactions)
            .lastUpdated(lastUpdated)
    }
}