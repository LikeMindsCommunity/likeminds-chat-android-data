package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model._Conversation_
import com.likeminds.internalsdk.conversation.model._Reaction_

class _Chatroom_ private constructor(
    @SerializedName("member")
    val member: _Member_?,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val createdAt: Long?,
    @SerializedName("answer_text")
    val answerText: String?,
    @SerializedName("state")
    val state: Int,
    @SerializedName("unseen_count")
    val unseenCount: Int?,
    @SerializedName("share_url")
    val shareUrl: String?,
    @SerializedName("community_id")
    val communityId: String?,
    @SerializedName("community_name")
    val communityName: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("about")
    val about: String?,
    @SerializedName("header")
    val header: String?,
    @SerializedName("show_follow_telescope")
    val showFollowTelescope: Boolean?,
    @SerializedName("show_follow_auto_tag")
    val showFollowAutoTag: Boolean?,
    @SerializedName("card_creation_time")
    val cardCreationTime: String?,
    @SerializedName("participants_count")
    val participantsCount: String?,
    @SerializedName("total_response_count")
    val totalResponseCount: String?,
    @SerializedName("mute_status")
    val muteStatus: Boolean?,
    @SerializedName("follow_status")
    val followStatus: Boolean?,
    @SerializedName("has_been_named")
    val hasBeenNamed: Boolean?,
    @SerializedName("has_reactions")
    val hasReactions: Boolean?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("is_tagged")
    val isTagged: Boolean?,
    @SerializedName("is_pending")
    val isPending: Boolean?,
    @SerializedName("is_pinned")
    val isPinned: Boolean?,
    @SerializedName("is_deleted")
    val isDeleted: Boolean?,
    @SerializedName("member_id", alternate = ["user_id"])
    val userId: String?,
    @SerializedName("deleted_by", alternate = ["deleted_by_user_id"])
    val deletedBy: String?,
    @SerializedName("deleted_by_member")
    val deletedByMember: _Member_?,
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @SerializedName("last_seen_conversation", alternate = ["last_seen_conversation_id"])
    val lastSeenConversationId: String?,
    @SerializedName("last_conversation_id")
    val lastConversationId: String?,
    @SerializedName("date_epoch")
    val dateEpoch: Long?,
    @SerializedName("is_secret")
    val isSecret: Boolean?,
    @SerializedName("secret_chatroom_participants")
    val secretChatroomParticipants: List<Int>?,
    @SerializedName("secret_chatroom_left")
    val secretChatroomLeft: Boolean?,
    @SerializedName("reactions")
    val reactions: List<_Reaction_>?,
    @SerializedName("topic_id")
    val topicId: String?,
    @SerializedName("topic")
    val topic: _Conversation_?,
    @SerializedName("auto_follow_done")
    val autoFollowDone: Boolean?,
    @SerializedName("is_edited")
    val isEdited: Boolean?,
    @SerializedName("access")
    val access: Int?,
    @SerializedName("member_can_message")
    val memberCanMessage: Boolean?,
    @SerializedName("cohorts")
    val cohorts: List<_Cohort_>?,
    @SerializedName("external_seen")
    val externalSeen: Boolean?,
    @SerializedName(value = "unread_messages", alternate = ["conversations_unread"])
    val unreadConversationCount: Int?,
    @SerializedName("chatroom_image_url")
    val chatroomImageUrl: String?,
    @SerializedName("access_without_subscription")
    val accessWithoutSubscription: Boolean?
) {

    class Builder {

        private var member: _Member_? = null
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
        private var totalResponseCount: String? = null
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
        private var deletedByMember: _Member_? = null
        private var updatedAt: Long? = null
        private var lastSeenConversationId: String? = null
        private var lastConversationId: String? = null
        private var dateEpoch: Long? = null
        private var isSecret: Boolean? = null
        private var secretChatroomParticipants: List<Int>? = null
        private var secretChatroomLeft: Boolean? = null
        private var reactions: List<_Reaction_>? = null
        private var topicId: String? = null
        private var topic: _Conversation_? = null
        private var autoFollowDone: Boolean? = null
        private var isEdited: Boolean? = null
        private var access: Int? = null
        private var memberCanMessage: Boolean? = null
        private var cohorts: List<_Cohort_>? = null
        private var externalSeen: Boolean? = null
        private var unreadConversationCount: Int? = null
        private var chatroomImageUrl: String? = null
        private var accessWithoutSubscription: Boolean? = null

        fun member(member: _Member_?) = apply { this.member = member }
        fun id(id: String) = apply { this.id = id }
        fun title(title: String) = apply { this.title = title }
        fun createdAt(createdAt: Long?) = apply { this.createdAt = createdAt }
        fun answerText(answerText: String?) = apply { this.answerText = answerText }
        fun state(state: Int) = apply { this.state = state }
        fun unseenCount(unseenCount: Int) = apply { this.unseenCount = unseenCount }
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

        fun totalResponseCount(totalResponseCount: String?) =
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
        fun deletedByMember(deletedByMember: _Member_?) =
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

        fun reactions(reactions: List<_Reaction_>?) = apply { this.reactions = reactions }
        fun topicId(topicId: String?) = apply { this.topicId = topicId }
        fun topic(topic: _Conversation_?) = apply { this.topic = topic }
        fun autoFollowDone(autoFollowDone: Boolean?) =
            apply { this.autoFollowDone = autoFollowDone }

        fun isEdited(isEdited: Boolean?) = apply { this.isEdited = isEdited }
        fun access(access: Int?) = apply { this.access = access }
        fun memberCanMessage(memberCanMessage: Boolean?) =
            apply { this.memberCanMessage = memberCanMessage }

        fun cohorts(cohorts: List<_Cohort_>?) = apply { this.cohorts = cohorts }
        fun externalSeen(externalSeen: Boolean?) = apply { this.externalSeen = externalSeen }
        fun unreadConversationCount(unreadConversationCount: Int?) =
            apply { this.unreadConversationCount = unreadConversationCount }

        fun chatroomImageUrl(chatroomImageUrl: String?) =
            apply { this.chatroomImageUrl = chatroomImageUrl }

        fun accessWithoutSubscription(accessWithoutSubscription: Boolean?) =
            apply { this.accessWithoutSubscription = accessWithoutSubscription }

        fun build() = _Chatroom_(
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
            accessWithoutSubscription
        )
    }
}