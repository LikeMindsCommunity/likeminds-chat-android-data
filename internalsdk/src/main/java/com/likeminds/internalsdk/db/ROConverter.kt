package com.likeminds.internalsdk.db

import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.poll.model._Poll_
import com.likeminds.internalsdk.sync.model._ReactionMeta_
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_
import com.likeminds.internalsdk.utils.TimeUtil
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

object ROConverter {

    /**--------------------------------
     * Internal Model -> Db Model
    --------------------------------*/

    //convert _User_ -> UserRO
    fun convertUser(user: _User_?): UserRO? {
        if (user == null) return null
        return UserRO().apply {
            id = user.id
            userUniqueId = user.userUniqueId
            imageUrl = user.imageUrl
            name = user.name
            isGuest = user.isGuest
            organizationName = user.organisationName
            updatedAt = user.updatedAt
            sdkClientInfoRO = convertSDKClientInfo(user.sdkClientInfo)
            isDeleted = user.isDeleted
            customTitle = user.customTitle
        }
    }

    //convert _SDKClientInfo_ -> SDKClientInfoRO
    private fun convertSDKClientInfo(sdkClientInfo: _SDKClientInfo_?): SDKClientInfoRO? {
        if (sdkClientInfo == null) return null
        return SDKClientInfoRO().apply {
            community = sdkClientInfo.community
            user = sdkClientInfo.user
            userUniqueId = sdkClientInfo.userUniqueId
        }
    }

    /**
     * convert [_Community_] to [CommunityRO]
     */
    fun convertCommunity(community: _Community_?): CommunityRO? {
        if (community == null) return null
        return CommunityRO().apply {
            id = community.id
            name = community.name
            imageUrl = community.imageUrl
            membersCount = community.membersCount
            updatedAt = community.updatedAt
        }
    }

    /**
     * convert [_Member_] to [MemberRO] with creating [uid] from [communityId]
     * @param member: member to be converted
     * @param communityId: community id
     * */
    fun convertMember(member: _Member_?, communityId: String): MemberRO? {
        if (member == null) return null
        val uid = "${member.id}#${communityId}"
        return MemberRO().apply {
            this.uid = uid
            this.id = member.id
            this.communityId = communityId.toInt()
            name = member.name ?: ""
            imageUrl = member.imageUrl ?: ""
            state = member.state ?: 0
            customIntroText = member.customIntroText
            customClickText = member.customClickText

            //if customTitle == "Member" then save null else member.customTitle()
            val customTitle = if (member.customTitle?.equals("Member") == true) {
                null
            } else {
                member.customTitle
            }
            this.customTitle = customTitle
            isOwner = member.isOwner
            isGuest = member.isGuest
            userUniqueId = member.userUniqueId
        }
    }

    /**
     * convert [_Conversation_] to [LastConversationRO] from new sync workers
     * @param realm: instance of realm
     * @param conversation: Conversation object to converted
     * @param creator: [MemberRO] object of conversation's creator
     * @param attachments: [List<_Attachment_>] list of attachments
     * */
    fun convertLastConversation(
        realm: Realm,
        conversation: _Conversation_?,
        creator: MemberRO?,
        attachments: List<_Attachment_>?
    ): LastConversationRO? {
        if (conversation == null || creator == null) return null
        val chatroomId = conversation.chatroomId ?: return null
        val communityId = conversation.communityId ?: return null

        var createdEpoch = conversation.createdEpoch ?: 0L
        createdEpoch = if (TimeUtil.isInMillis(createdEpoch)) {
            createdEpoch
        } else {
            createdEpoch * 1000
        }

        //get existing conversation from db to update the existing values
        val savedAnswer =
            if (conversation.hasReactions == true ||
                _ConversationState_.isPoll(conversation.state) ||
                ((conversation.attachmentCount ?: 0) > 0)
            ) {
                ChatDBUtil.getConversation(realm, conversation.id.toString())
            } else {
                null
            }
        //get attachments as per saved and new conversation
        val updatedAttachments = convertUpdatedAttachments(
            chatroomId,
            communityId,
            attachments,
            savedAnswer?.attachments
        )

        //Clear embedded object list if already present else calling insertToRealmOrUpdate will duplicate it
        savedAnswer?.attachments?.clear()

        return LastConversationRO().apply {
            id = conversation.id.toString()
            member = creator
            createdAt = conversation.createdAt
            answer = conversation.answer
            state = conversation.state
            this.attachments = updatedAttachments
            attachmentCount = conversation.attachmentCount
            date = conversation.date
            deletedBy = conversation.deletedBy
            attachmentsUploaded = conversation.attachmentUploaded
            uploadWorkerUUID = savedAnswer?.uploadWorkerUUID
            this.createdEpoch = createdEpoch
            this.chatroomId = chatroomId
            this.communityId = communityId
        }
    }

    /**
     * Update only those conversation attachments(image,video) whose urls are uploaded successfully on server
     * */
    private fun convertUpdatedAttachments(
        chatroomId: String,
        communityId: String,
        attachments: List<_Attachment_>?,
        oldAttachments: RealmList<AttachmentRO>?
    ): RealmList<AttachmentRO> {
        return when {
            oldAttachments.isNullOrEmpty() && attachments.isNullOrEmpty() -> realmListOf()

            oldAttachments.isNullOrEmpty() && !attachments.isNullOrEmpty() -> {
                attachments.map { attachment ->
                    convertAttachment(chatroomId, communityId, attachment)
                }.toRealmList()
            }

            !oldAttachments.isNullOrEmpty() && attachments.isNullOrEmpty() -> {
                oldAttachments.map { attachment ->
                    convertAttachment(chatroomId, communityId, attachment)
                }.toRealmList()
            }

            oldAttachments!!.size > attachments!!.size -> {
                oldAttachments.map { oldAttachment ->
                    val attachment = attachments.find { attachment ->
                        attachment.index == oldAttachment.index
                    }
                    return@map if (attachment != null) {
                        oldAttachment.apply {
                            url = attachment.url
                            awsFolderPath = ""
                            thumbnailUrl = attachment.thumbnailUrl
                            thumbnailAWSFolderPath = ""
                        }
                    } else {
                        convertAttachment(chatroomId, communityId, oldAttachment)
                    }
                }.toRealmList()
            }

            else -> {
                attachments.map { attachment ->
                    val oldAttachment = oldAttachments.find { oldAttachment ->
                        oldAttachment.index == attachment.index
                    }
                    return@map oldAttachment?.apply {
                        url = attachment.url
                        awsFolderPath = ""
                        thumbnailUrl = attachment.thumbnailUrl
                        thumbnailAWSFolderPath = ""
                    } ?: convertAttachment(chatroomId, communityId, attachment)
                }.toRealmList()
            }
        }
    }

    private fun convertAttachments(
        chatroomId: String,
        communityId: String,
        attachments: List<_Attachment_>?
    ): RealmList<AttachmentRO> {
        return (attachments ?: emptyList()).map { attachment ->
            convertAttachment(chatroomId, communityId, attachment)
        }.toRealmList()
    }

    private fun convertAttachment(
        chatroomId: String,
        communityId: String,
        attachment: _Attachment_
    ): AttachmentRO {
        return AttachmentRO().apply {
            id = attachment.id.toString()
            url = attachment.url
            this.chatroomId = chatroomId
            this.communityId = communityId
            name = attachment.name
            type = attachment.type
            index = attachment.index
            width = attachment.width
            height = attachment.height
            awsFolderPath = attachment.awsFolderPath
            localFilePath = attachment.localFilePath
            thumbnailUrl = attachment.thumbnailUrl
            thumbnailAWSFolderPath = attachment.thumbnailAWSFolderPath
            thumbnailLocalFilePath = attachment.thumbnailLocalFilePath
            metaRO = convertAttachmentMeta(attachment.meta)
            createdAt = attachment.createdAt
            updatedAt = attachment.updatedAt
        }
    }

    private fun convertAttachmentMeta(meta: _AttachmentMeta_?): AttachmentMetaRO {
        return AttachmentMetaRO().apply {
            numberOfPage = meta?.numberOfPage
            size = meta?.size
            duration = meta?.duration
        }
    }

    private fun convertAttachmentMeta(meta: AttachmentMetaRO?): AttachmentMetaRO {
        return AttachmentMetaRO().apply {
            numberOfPage = meta?.numberOfPage
            size = meta?.size
            duration = meta?.duration
        }
    }

    private fun convertAttachment(
        chatroomId: String,
        communityId: String,
        attachment: AttachmentRO
    ): AttachmentRO {
        return AttachmentRO().apply {
            id = attachment.id
            url = attachment.url
            this.chatroomId = chatroomId
            this.communityId = communityId
            name = attachment.name
            type = attachment.type
            index = attachment.index
            width = attachment.width
            height = attachment.height
            awsFolderPath = attachment.awsFolderPath
            localFilePath = attachment.localFilePath
            thumbnailUrl = attachment.thumbnailUrl
            thumbnailAWSFolderPath = attachment.thumbnailAWSFolderPath
            thumbnailLocalFilePath = attachment.thumbnailLocalFilePath
            metaRO = convertAttachmentMeta(attachment.metaRO)
            createdAt = attachment.createdAt
            updatedAt = attachment.updatedAt
        }
    }

    /**
     * convert [Conversation] to [CollabcardAnswerRO] from new sync workers
     * @param realm: instance of realm
     * @param conversation: Conversation object to converted
     * @param creator: [MemberRO] object of conversation's creator
     * @param polls: [List<Poll>] list of polls
     * @param attachments: [List<CollabcardAttachment>] list of attachments
     * @param reactions: [List<ReactionMeta>] list of reactions
     * */
    fun convertConversation(
        realm: Realm,
        conversation: _Conversation_?,
        creator: MemberRO?,
        polls: List<_Poll_>?,
        attachments: List<_Attachment_>?,
        reactions: List<_ReactionMeta_>? = null,
        loggedInMemberId: String? = null
    ): ConversationRO? {
        if (conversation == null || creator == null) return null
        val chatroomId = conversation.chatroomId ?: return null
        val communityId = conversation.communityId ?: return null

        var createdEpoch = conversation.createdEpoch ?: 0L
        createdEpoch = if (TimeUtil.isInMillis(createdEpoch)) {
            createdEpoch
        } else {
            createdEpoch * 1000
        }

        //get existing conversation from db to update the existing values
        val savedAnswer =
            if (conversation.hasReactions == true ||
                _ConversationState_.isPoll(conversation.state) ||
                ((conversation.attachmentCount ?: 0) > 0) ||
                conversation.replyConversationId != null
            ) {
                ChatDBUtil.getConversation(realm, conversation.id.toString())
            } else {
                null
            }
        //get attachments as per saved and new conversation
        val updatedAttachments = convertUpdatedAttachments(
            chatroomId,
            communityId,
            attachments,
            savedAnswer?.attachments
        )

        //get replied conversation
        val replyConversation = if (conversation.replyConversationId != null) {
            savedAnswer?.replyConversation ?: ChatDBUtil.getConversation(
                realm,
                conversation.replyConversationId
            )
        } else {
            null
        }

        //convert polls to pollsRO
        val pollsRO = convertPolls(realm, polls?.toMutableList(), communityId)

        //convert reaction to reactionRO
        val reactionsRO = convertReactionsMeta(realm, communityId, reactions)

        val linkRO = convertLink(chatroomId, communityId, conversation.ogTags)

        //Clear embedded object list if already present else calling insertToRealmOrUpdate will duplicate it
        savedAnswer?.attachments?.clear()
        savedAnswer?.reactions?.clear()
        savedAnswer?.polls?.clear()

        return ConversationRO().apply {
            this.id = conversation.id.toString()
            this.chatroomId = chatroomId
            this.communityId = communityId
            member = creator
            answer = conversation.answer
            state = conversation.state
            this.createdEpoch = createdEpoch
            this.createdAt = conversation.createdAt
            lastUpdatedAt = conversation.lastUpdated ?: 0L
            date = conversation.date
            isEdited = conversation.isEdited

            replyChatRoomId = conversation.replyChatroomId
            replyConversationId = conversation.replyConversationId
            this.replyConversation = replyConversation

            deletedBy = conversation.deletedBy
            attachmentCount = conversation.attachmentCount
            attachmentsUploaded = conversation.attachmentUploaded
            uploadWorkerUUID = savedAnswer?.uploadWorkerUUID
            this.attachments = updatedAttachments
            this.link = linkRO

            localSavedEpoch = conversation.localCreatedEpoch ?: 0L
            temporaryId = if (creator.id == loggedInMemberId) {
                conversation.temporaryId
            } else {
                null
            }

            this.reactions = reactionsRO

            isAnonymous = conversation.isAnonymous
            allowAddOption = conversation.allowAddOption
            pollType = conversation.pollType
            pollTypeText = conversation.pollTypeText
            submitTypeText = conversation.submitTypeText
            expiryTime = conversation.expiryTime
            multipleSelectNum = conversation.multipleSelectNum
            multipleSelectState = conversation.multipleSelectState
            this.polls = pollsRO
            toShowResults = conversation.toShowResults
            pollAnswerText = conversation.pollAnswerText
        }
    }

    private fun convertPolls(
        realm: Realm,
        polls: MutableList<_Poll_>?,
        communityId: String?
    ): RealmList<PollRO> {
        return polls.orEmpty().mapNotNull { poll ->
            convertPoll(realm, communityId, poll, poll.member?.id)
        }.toRealmList()
    }

    fun convertPoll(
        realm: Realm,
        communityId: String?,
        poll: _Poll_?,
        memberId: String?
    ): PollRO? {
        if (poll == null || communityId == null) return null
        return PollRO().apply {
            id = poll.id.toString()
            text = poll.text
            subText = poll.subText
            isSelected = poll.isSelected
            percentage = poll.percentage
            noVotes = poll.noVotes
            member = ChatDBUtil.getMember(realm, communityId, memberId)
        }
    }

    private fun convertReactionsMeta(
        realm: Realm,
        communityId: String?,
        reactions: List<_ReactionMeta_>?,
    ): RealmList<ReactionRO> {
        return reactions.orEmpty().reversed().mapNotNull { reaction ->
            convertReactionMeta(realm, reaction, communityId)
        }.toRealmList()
    }

    private fun convertReactionMeta(
        realm: Realm,
        reaction: _ReactionMeta_,
        communityId: String?,
    ): ReactionRO? {
        val memberRO =
            convertMember(reaction.member, communityId.toString()) ?: ChatDBUtil.getMember(
                realm,
                communityId,
                reaction.userId.toString()
            ) ?: return null
        return ReactionRO().apply {
            member = memberRO
            this.reaction = reaction.reaction
        }
    }

    fun convertLink(
        chatroomId: String,
        communityId: String,
        link: _LinkOGTags_?
    ): LinkRO? {
        if (link == null || link.url.isNullOrEmpty()) {
            return null
        }
        return LinkRO().apply {
            url = link.url
            this.chatroomId = chatroomId
            this.communityId = communityId
            title = link.title
            image = link.image
            description = link.description
        }
    }
}