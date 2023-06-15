package com.likeminds.likemindschat.chatroom.model

import com.likeminds.likemindschat.community.model.Member
import com.likeminds.likemindschat.conversation.model.Conversation
import com.likeminds.likemindschat.conversation.model.Reaction

class Chatroom private constructor(
    val member: Member?,
    val id: String,
    val title: String,
    val createdAt: Long?,
    val answerText: String?,
    val state: Int,
    val unseenCount: Int?,
    val shareUrl: String?,
    val communityId: String?,
    val communityName: String?,
    val type: Int?,
    val about: String?,
    val header: String?,
    val showFollowTelescope: Boolean?,
    val showFollowAutoTag: Boolean?,
    val cardCreationTime: String?,
    val participantsCount: String?,
    val totalResponseCount: Int,
    val muteStatus: Boolean?,
    val followStatus: Boolean?,
    val hasBeenNamed: Boolean?,
    val hasReactions: Boolean?,
    val date: String?,
    val isTagged: Boolean?,
    val isPending: Boolean?,
    val isPinned: Boolean?,
    val isDeleted: Boolean?,
    val userId: String?,
    val deletedBy: String?,
    val deletedByMember: Member?,
    val updatedAt: Long?,
    val lastSeenConversationId: String?,
    val lastConversationId: String?,
    val dateEpoch: Long?,
    val isSecret: Boolean?,
    val secretChatroomParticipants: List<Int>?,
    val secretChatroomLeft: Boolean?,
    val reactions: List<Reaction>?,
    val topicId: String?,
    val topic: Conversation?,
    val autoFollowDone: Boolean?,
    val isEdited: Boolean?,
    val access: Int?,
    val memberCanMessage: Boolean?,
    val cohorts: List<Cohort>?,
    val externalSeen: Boolean?,
    val unreadConversationCount: Int?,
    val chatroomImageUrl: String?,
    val accessWithoutSubscription: Boolean?,
    val lastConversation: Conversation?,
    val lastSeenConversation: Conversation?,
    val draftConversation: String?,
    val isConversationStored: Boolean,
    val isDraft: Boolean?
) {
    class Builder {

        private var member: Member? = null
        private var id: String = ""
        private var title: String = ""
        private var createdAt: Long? = null
        private var answerText: String? = null
        private var state: Int = 0
        private var unseenCount: Int? = null
        private var shareUrl: String? = null
        private var communityId: String? = null
        private var communityName: String? = null
        private var type: Int? = null
        private var about: String? = null
        private var header: String? = null
        private var showFollowTelescope: Boolean? = null
        private var showFollowAutoTag: Boolean? = null
        private var cardCreationTime: String? = null
        private var participantsCount: String? = null
        private var totalResponseCount: Int = 0
        private var muteStatus: Boolean? = null
        private var followStatus: Boolean? = null
        private var hasBeenNamed: Boolean? = null
        private var hasReactions: Boolean? = null
        private var date: String? = null
        private var isTagged: Boolean? = null
        private var isPending: Boolean? = null
        private var isPinned: Boolean? = null
        private var isDeleted: Boolean? = null
        private var userId: String? = null
        private var deletedBy: String? = null
        private var deletedByMember: Member? = null
        private var updatedAt: Long? = null
        private var lastSeenConversationId: String? = null
        private var lastConversationId: String? = null
        private var dateEpoch: Long? = null
        private var isSecret: Boolean? = null
        private var secretChatroomParticipants: List<Int>? = null
        private var secretChatroomLeft: Boolean? = null
        private var reactions: List<Reaction>? = null
        private var topicId: String? = null
        private var topic: Conversation? = null
        private var autoFollowDone: Boolean? = null
        private var isEdited: Boolean? = null
        private var access: Int? = null
        private var memberCanMessage: Boolean? = null
        private var cohorts: List<Cohort>? = null
        private var externalSeen: Boolean? = null
        private var unreadConversationCount: Int? = null
        private var chatroomImageUrl: String? = null
        private var accessWithoutSubscription: Boolean? = null
        private var lastConversation: Conversation? = null
        private var lastSeenConversation: Conversation? = null
        private var draftConversation: String? = null
        private var isConversationStored: Boolean = false
        private var isDraft: Boolean? = null

        fun member(member: Member?) = apply { this.member = member }
        fun id(id: String) = apply { this.id = id }
        fun title(title: String) = apply { this.title = title }
        fun createdAt(createdAt: Long?) = apply { this.createdAt = createdAt }
        fun answerText(answerText: String?) = apply { this.answerText = answerText }
        fun state(state: Int) = apply { this.state = state }
        fun unseenCount(unseenCount: Int?) = apply { this.unseenCount = unseenCount }
        fun shareUrl(shareUrl: String?) = apply { this.shareUrl = shareUrl }
        fun communityId(communityId: String?) = apply { this.communityId = communityId }
        fun communityName(communityName: String?) = apply { this.communityName = communityName }
        fun type(type: Int?) = apply { this.type = type }
        fun about(about: String?) = apply { this.about = about }
        fun header(header: String?) = apply { this.header = header }
        fun showFollowTelescope(showFollowTelescope: Boolean?) =
            apply { this.showFollowTelescope = showFollowTelescope }

        fun showFollowAutoTag(showFollowAutoTag: Boolean?) =
            apply { this.showFollowAutoTag = showFollowAutoTag }

        fun cardCreationTime(cardCreationTime: String?) =
            apply { this.cardCreationTime = cardCreationTime }

        fun participantsCount(participantsCount: String?) =
            apply { this.participantsCount = participantsCount }

        fun totalResponseCount(totalResponseCount: Int) =
            apply { this.totalResponseCount = totalResponseCount }

        fun muteStatus(muteStatus: Boolean?) = apply { this.muteStatus = muteStatus }
        fun followStatus(followStatus: Boolean?) = apply { this.followStatus = followStatus }
        fun hasBeenNamed(hasBeenNamed: Boolean?) = apply { this.hasBeenNamed = hasBeenNamed }
        fun hasReactions(hasReactions: Boolean?) = apply { this.hasReactions = hasReactions }
        fun date(date: String?) = apply { this.date = date }
        fun isTagged(isTagged: Boolean?) = apply { this.isTagged = isTagged }
        fun isPending(isPending: Boolean?) = apply { this.isPending = isPending }
        fun isPinned(isPinned: Boolean?) = apply { this.isPinned = isPinned }
        fun isDeleted(isDeleted: Boolean?) = apply { this.isDeleted = isDeleted }
        fun userId(userId: String?) = apply { this.userId = userId }
        fun deletedBy(deletedBy: String?) = apply { this.deletedBy = deletedBy }
        fun deletedByMember(deletedByMember: Member?) =
            apply { this.deletedByMember = deletedByMember }

        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }
        fun lastSeenConversationId(lastSeenConversationId: String?) =
            apply { this.lastSeenConversationId = lastSeenConversationId }

        fun lastConversationId(lastConversationId: String?) =
            apply { this.lastConversationId = lastConversationId }

        fun dateEpoch(dateEpoch: Long?) = apply { this.dateEpoch = dateEpoch }
        fun isSecret(isSecret: Boolean?) = apply { this.isSecret = isSecret }
        fun secretChatroomParticipants(secretChatroomParticipants: List<Int>?) =
            apply { this.secretChatroomParticipants = secretChatroomParticipants }

        fun secretChatroomLeft(secretChatroomLeft: Boolean?) =
            apply { this.secretChatroomLeft = secretChatroomLeft }

        fun reactions(reactions: List<Reaction>?) = apply { this.reactions = reactions }
        fun topicId(topicId: String?) = apply { this.topicId = topicId }
        fun topic(topic: Conversation?) = apply { this.topic = topic }
        fun autoFollowDone(autoFollowDone: Boolean?) =
            apply { this.autoFollowDone = autoFollowDone }

        fun isEdited(isEdited: Boolean?) = apply { this.isEdited = isEdited }
        fun access(access: Int?) = apply { this.access = access }
        fun memberCanMessage(memberCanMessage: Boolean?) =
            apply { this.memberCanMessage = memberCanMessage }

        fun cohorts(cohorts: List<Cohort>?) = apply { this.cohorts = cohorts }
        fun externalSeen(externalSeen: Boolean?) = apply { this.externalSeen = externalSeen }
        fun unreadConversationCount(unreadConversationCount: Int?) =
            apply { this.unreadConversationCount = unreadConversationCount }

        fun chatroomImageUrl(chatroomImageUrl: String?) =
            apply { this.chatroomImageUrl = chatroomImageUrl }

        fun accessWithoutSubscription(accessWithoutSubscription: Boolean?) =
            apply { this.accessWithoutSubscription = accessWithoutSubscription }

        fun lastConversation(lastConversation: Conversation?) =
            apply { this.lastConversation = lastConversation }

        fun lastSeenConversation(lastSeenConversation: Conversation?) =
            apply { this.lastSeenConversation = lastSeenConversation }

        fun draftConversation(draftConversation: String?) =
            apply { this.draftConversation = draftConversation }

        fun isConversationStored(isConversationStored: Boolean) =
            apply { this.isConversationStored = isConversationStored }

        fun isDraft(isDraft: Boolean?) = apply { this.isDraft = isDraft }

        fun build() = Chatroom(
            member,
            id,
            title,
            createdAt,
            answerText,
            state,
            unseenCount,
            shareUrl,
            communityId,
            communityName,
            type,
            about,
            header,
            showFollowTelescope,
            showFollowAutoTag,
            cardCreationTime,
            participantsCount,
            totalResponseCount,
            muteStatus,
            followStatus,
            hasBeenNamed,
            hasReactions,
            date,
            isTagged,
            isPending,
            isPinned,
            isDeleted,
            userId,
            deletedBy,
            deletedByMember,
            updatedAt,
            lastSeenConversationId,
            lastConversationId,
            dateEpoch,
            isSecret,
            secretChatroomParticipants,
            secretChatroomLeft,
            reactions,
            topicId,
            topic,
            autoFollowDone,
            isEdited,
            access,
            memberCanMessage,
            cohorts,
            externalSeen,
            unreadConversationCount,
            chatroomImageUrl,
            accessWithoutSubscription,
            lastConversation,
            lastSeenConversation,
            draftConversation,
            isConversationStored,
            isDraft
        )
    }
}