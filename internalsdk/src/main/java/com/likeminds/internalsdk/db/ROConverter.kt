package com.likeminds.internalsdk.db

import com.likeminds.internalsdk.chatroom.model._Chatroom_
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
import io.realm.Realm
import io.realm.RealmList

object ROConverter {

    /**--------------------------------
     * Internal Model -> Db Model
    --------------------------------*/

    /**
     * convert [_Community_] to [CommunityRO]
     */
    fun convertCommunity(community: _Community_?): CommunityRO? {
        if (community == null) return null
        return CommunityRO.build(community.id) {
            name = community.name
            imageUrl = community.imageUrl
            membersCount = community.membersCount
            updatedAt = community.updatedAt
        }
    }

    /**
     * convert [_Chatroom_] to [ChatroomRO]
     * @param realm: instance of realm
     * @param chatroom: Chatroom object to converted
     * @param chatroomCreatorRO: [MemberRO] object of chatroom's creator
     * @param lastConversationRO: [LastConversationRO] object of the last conversation
     * @param reactions: [List<ReactionMeta>] list of reactions
     * */
    fun convertChatroom(
        realm: Realm,
        chatroom: _Chatroom_?,
        chatroomCreatorRO: MemberRO,
        lastConversationRO: LastConversationRO? = null,
        reactions: List<_ReactionMeta_> = emptyList()
    ): ChatroomRO? {
        if (chatroom == null) return null

        val chatroomId = chatroom.id
        val communityId = chatroom.communityId ?: ""

        val savedChatroom = ChatDBUtil.getChatroom(realm, chatroomId)
        val reactionsRO = convertReactionsMeta(realm, communityId, reactions)

        return ChatroomRO.build(chatroomId, communityId, chatroom.title) {
            state = chatroom.state
            member = chatroomCreatorRO
            createdAt = chatroom.createdAt
            type = chatroom.type
            chatroomImageUrl = chatroom.chatroomImageUrl
            header = chatroom.header
            cardCreationTime = chatroom.cardCreationTime
            totalResponseCount = savedChatroom?.totalResponseCount ?: 0
            totalAllResponseCount = savedChatroom?.totalAllResponseCount ?: 0
            muteStatus = chatroom.muteStatus
            followStatus = chatroom.followStatus
            hasBeenNamed = chatroom.hasBeenNamed
            date = chatroom.date
            isTagged = chatroom.isTagged
            isPending = chatroom.isPending
            deletedBy = chatroom.deletedBy
            autoFollowDone = chatroom.autoFollowDone
            memberCanMessage = chatroom.memberCanMessage
            isEdited = chatroom.isEdited
            externalSeen = chatroom.externalSeen
            accessWithoutSubscription = chatroom.accessWithoutSubscription == true
            unreadConversationsCount = chatroom.unreadConversationCount

            this.reactions = reactionsRO

            val updatedAt = lastConversationRO?.createdEpoch
                ?: savedChatroom?.lastConversationRO?.createdEpoch
                ?: chatroom.createdAt

            this.updatedAt = updatedAt

            lastSeenConversationId = chatroom.lastSeenConversationId
            lastSeenConversation = savedChatroom?.lastSeenConversation

            lastConversationId = chatroom.lastConversationId
            lastConversation = savedChatroom?.lastConversation
            this.lastConversationRO = lastConversationRO ?: savedChatroom?.lastConversationRO

            unseenCount = chatroom.unseenCount ?: 0
            dateEpoch = chatroom.dateEpoch
            draftConversation = savedChatroom?.draftConversation //to maintain un-send conversation
            isSecret = chatroom.isSecret
            secretChatRoomParticipants = chatroom.secretChatroomParticipants.toRealmList()
            secretChatRoomLeft = chatroom.secretChatroomLeft
            topicId = chatroom.topicId ?: savedChatroom?.topicId
            topic = savedChatroom?.topic
            isConversationStored = savedChatroom?.isConversationStored ?: false
        }
    }

    /**
     * Use this function to convert for api response
     *
     * convert [_Conversation_] to [ConversationRO]
     * @param realm: instance of realm
     * @param conversation: Conversation object to converted
     * @param member: [MemberRO] object of conversation's creator
     * @param loggedInMember: Object of logged in member
     * */
    fun convertConversation(
        realm: Realm,
        conversation: _Conversation_?,
        member: MemberRO? = null,
        loggedInMember: UserRO?
    ): ConversationRO? {
        /**
         * Conversation is invalid without chatroomId, conversationId, Member object
         */
        if (conversation == null) return null
        val chatroomId = conversation.chatroomId ?: return null
        val communityId = conversation.communityId ?: return null
        val memberRO = member ?: ChatDBUtil.getConversationMember(
            realm,
            conversation
        ) ?: convertUserToMember(loggedInMember, communityId) ?: return null

        val savedAnswer = if (conversation.hasReactions == true ||
            _ConversationState_.isPoll(conversation.state) ||
            ((conversation.attachmentCount ?: 0) > 0) ||
            conversation.replyConversationId != null
        ) {
            ChatDBUtil.getConversation(realm, conversation.id)
        } else {
            null
        }

        val replyConversation = if (!conversation.replyConversationId.isNullOrEmpty()) {
            savedAnswer?.replyConversation ?: ChatDBUtil.getConversation(
                realm,
                conversation.replyConversationId
            )
        } else {
            null
        }

        val attachmentList = convertUpdatedAttachments(
            chatroomId,
            communityId,
            conversation.attachments,
            savedAnswer?.attachments
        )
        val reactionsList = convertReactions(
            realm,
            communityId,
            conversation.reactions
        )
        val pollsList = convertPolls(realm, conversation.polls as? MutableList<_Poll_>, communityId)

        //Clear embedded object list if already present else calling insertToRealmOrUpdate will duplicate it
        savedAnswer?.reactions?.deleteAllFromRealm()
        savedAnswer?.attachments?.deleteAllFromRealm()
        savedAnswer?.polls?.deleteAllFromRealm()

        var createdEpoch = conversation.createdEpoch ?: 0L
        createdEpoch = if (TimeUtil.isInMillis(createdEpoch)) {
            createdEpoch
        } else {
            createdEpoch * 1000
        }
        return ConversationRO.build(
            conversation.id ?: "",
            conversation.answer,
            conversation.state,
            createdEpoch
        ) {
            this.communityId = communityId
            this.member = memberRO
            this.chatroomId = chatroomId
            createdAt = conversation.createdAt
            attachments = attachmentList
            link = convertLink(chatroomId, communityId, conversation.ogTags)
            date = conversation.date
            isEdited = conversation.isEdited
            replyConversationId = conversation.replyConversationId
            this.replyConversation = replyConversation
            deletedBy = conversation.deletedBy
            attachmentCount = conversation.attachmentCount
            attachmentsUploaded = conversation.attachmentUploaded
            uploadWorkerUUID = savedAnswer?.uploadWorkerUUID ?: conversation.uploadWorkerUUID
            localSavedEpoch = conversation.localCreatedEpoch ?: 0L
            temporaryId = if (memberRO.id == loggedInMember?.id) {
                conversation.temporaryId
            } else {
                null
            }

            reactions = reactionsList
            isAnonymous = conversation.isAnonymous
            allowAddOption = conversation.allowAddOption
            pollType = conversation.pollType
            pollTypeText = conversation.pollTypeText
            submitTypeText = conversation.submitTypeText
            expiryTime = conversation.expiryTime
            multipleSelectNum = conversation.multipleSelectNum
            multipleSelectState = conversation.multipleSelectState
            polls = pollsList
            toShowResults = conversation.toShowResults
            pollAnswerText = conversation.pollAnswerText
            replyChatRoomId = conversation.replyChatroomId
        }
    }

    /**
     * Use this function to convert sync conversation/chatroom
     *
     * convert [_Conversation_] to [ConversationRO]
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
        loggedInUUID: String? = null,
        deletedByMemberRO: MemberRO? = null
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
        savedAnswer?.attachments?.deleteAllFromRealm()
        savedAnswer?.reactions?.deleteAllFromRealm()
        savedAnswer?.polls?.deleteAllFromRealm()

        return ConversationRO.build(
            conversation.id.toString(),
            conversation.answer,
            conversation.state,
            createdEpoch
        ) {
            this.chatroomId = chatroomId
            this.communityId = communityId
            member = creator

            this.createdAt = conversation.createdAt
            lastUpdatedAt = conversation.lastUpdated ?: 0L
            date = conversation.date
            isEdited = conversation.isEdited

            replyChatRoomId = conversation.replyChatroomId
            replyConversationId = conversation.replyConversationId
            this.replyConversation = replyConversation

            deletedBy = conversation.deletedBy
            this.deletedByMember = deletedByMemberRO
            attachmentCount = conversation.attachmentCount
            attachmentsUploaded = conversation.attachmentUploaded
            uploadWorkerUUID = savedAnswer?.uploadWorkerUUID
            this.attachments = updatedAttachments
            this.link = linkRO

            localSavedEpoch = conversation.localCreatedEpoch ?: 0L

            val creatorUUID = creator.sdkClientInfoRO?.uuid
            temporaryId = if (creatorUUID == loggedInUUID) {
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

    /**
     * convert [_User_] to [UserRO]
     * @param user: Object of user to be converted
     * */
    fun convertUser(user: _User_?): UserRO? {
        if (user == null) return null

        return UserRO.build(user.id, user.userUniqueId) {
            imageUrl = user.imageUrl
            name = user.name
            isGuest = user.isGuest
            organizationName = user.organisationName
            updatedAt = user.updatedAt ?: 0L
            sdkClientInfoRO = convertSDKClientInfo(user.sdkClientInfo)
            isDeleted = user.isDeleted
            customTitle = user.customTitle
        }
    }

    /**
     * convert [UserRO] to [MemberRO] and save it [MemberRO] table
     * @param userRO: object of [UserRO]
     * @param communityId: id of community
     *
     * @return [MemberRO]: object created
     */
    private fun convertUserToMember(userRO: UserRO?, communityId: String?): MemberRO? {
        if (userRO == null) return null
        val uuid = userRO.sdkClientInfoRO?.uuid ?: ""
        val uid = "$uuid#$communityId"
        val memberRO = MemberRO.build(uid, uuid) {
            name = userRO.name
            imageUrl = userRO.imageUrl
            customTitle = userRO.customTitle
            userUniqueId = userRO.userUniqueId
            isGuest = userRO.isGuest
        }
        ChatDBUtil.writeAsync({
            it.insertOrUpdate(memberRO)
        })

        return memberRO
    }

    /**
     * convert [_Member_] to [MemberRO] with creating [uid] from [communityId]
     * @param member: member to be converted
     * @param communityId: community id
     * */
    fun convertMember(member: _Member_?, communityId: String): MemberRO? {
        if (member == null) return null
        val uuid = member.sdkClientInfo?.uuid ?: ""
        val uid = "${uuid}#${communityId}"

        return MemberRO.build(uid, uuid) {
            this.communityId = communityId.toInt()
            name = member.name
            id = member.id
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
            sdkClientInfoRO = convertSDKClientInfo(member.sdkClientInfo)
        }
    }

    /**
     * convert [_SDKClientInfo_] to [_SDKClientInfo_]
     * @param sdkClientInfo: Object of sdkClientInfo to be converted
     * */
    private fun convertSDKClientInfo(sdkClientInfo: _SDKClientInfo_?): SDKClientInfoRO? {
        if (sdkClientInfo == null) return null
        return SDKClientInfoRO.build {
            community = sdkClientInfo.community
            user = sdkClientInfo.user
            userUniqueId = sdkClientInfo.userUniqueId
            uuid = sdkClientInfo.uuid
        }
    }

    /**
     * convert [ConversationRO] to [LastConversationRO]
     */
    fun convertConversationToLastConversation(
        conversation: ConversationRO?
    ): LastConversationRO? {
        if (conversation == null) return null
        val chatroomId = conversation.chatroomId
        val communityId = conversation.communityId

        var createdEpoch = conversation.createdEpoch
        createdEpoch = if (TimeUtil.isInMillis(createdEpoch)) {
            createdEpoch
        } else {
            createdEpoch * 1000
        }

        return LastConversationRO.build(
            conversation.id,
            conversation.answer,
            conversation.state,
            createdEpoch
        ) {
            this.communityId = communityId
            this.member = conversation.member
            this.chatroomId = chatroomId
            link = conversation.link
            createdAt = conversation.createdAt
            date = conversation.date
            deletedBy = conversation.deletedBy
            this.attachments = conversation.attachments
            attachmentCount = conversation.attachmentCount
            attachmentsUploaded = conversation.attachmentsUploaded
            uploadWorkerUUID =
                conversation.uploadWorkerUUID // to maintain the upload worker uuid in case of retry upload
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
        attachments: List<_Attachment_>?,
        deletedByMember: MemberRO? = null
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
        savedAnswer?.attachments?.deleteAllFromRealm()

        return LastConversationRO.build(
            conversation.id.toString(),
            conversation.answer,
            conversation.state,
            createdEpoch
        ) {
            member = creator
            createdAt = conversation.createdAt
            this.attachments = updatedAttachments
            attachmentCount = conversation.attachmentCount
            date = conversation.date
            deletedBy = conversation.deletedBy
            this.deletedByMember = deletedByMember
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
            oldAttachments.isNullOrEmpty() && attachments.isNullOrEmpty() -> {
                RealmList()
            }

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
                    convertAttachment(chatroomId, communityId, oldAttachment)
                }.toRealmList()
            }

            else -> {
                attachments.map { attachment ->
                    convertAttachment(chatroomId, communityId, attachment)
                }.toRealmList()
            }
        }
    }

    /**
     * convert [_Attachment_] to [AttachmentRO]
     * @param chatroomId: id of the chatroom
     * @param communityId: id of the community
     * @param attachment: [_Attachment_] to be converted
     * */
    private fun convertAttachment(
        chatroomId: String,
        communityId: String,
        attachment: _Attachment_
    ): AttachmentRO {
        return AttachmentRO.build(attachment.url, chatroomId, communityId) {
            id = attachment.id.toString()
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

    /**
     * convert [_AttachmentMeta_] to [AttachmentMetaRO]
     * @param meta: [_AttachmentMeta_] to be converted
     * */
    private fun convertAttachmentMeta(meta: _AttachmentMeta_?): AttachmentMetaRO {
        return AttachmentMetaRO.build {
            numberOfPage = meta?.numberOfPage
            size = meta?.size
            duration = meta?.duration
        }
    }

    /**
     * convert [AttachmentMetaRO] to [AttachmentMetaRO]
     * @param meta: [AttachmentMetaRO] to be converted
     * */
    private fun convertAttachmentMeta(meta: AttachmentMetaRO?): AttachmentMetaRO {
        return AttachmentMetaRO.build {
            numberOfPage = meta?.numberOfPage
            size = meta?.size
            duration = meta?.duration
        }
    }

    /**
     * convert [AttachmentRO] to [AttachmentRO]
     * @param attachment: [AttachmentRO] to be converted
     * */
    private fun convertAttachment(
        chatroomId: String,
        communityId: String,
        attachment: AttachmentRO
    ): AttachmentRO {
        return AttachmentRO.build(attachment.url, chatroomId, communityId) {
            id = attachment.id
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
     * convert [_Poll_] to [PollRO]
     * @param realm: Instance of realm
     * @param polls: List of [_Poll_] to converted
     * @param communityId: id of the community
     *
     * @return list of [PollRO]
     * */
    private fun convertPolls(
        realm: Realm,
        polls: MutableList<_Poll_>?,
        communityId: String?
    ): RealmList<PollRO> {
        return polls.orEmpty().mapNotNull { poll ->
            convertPoll(realm, communityId, poll, poll.member?.sdkClientInfo?.uuid)
        }.toRealmList()
    }

    /**
     * convert [_Poll_] to [PollRO]
     * @param realm: Instance of realm
     * @param poll: [_Poll_] to converted
     * @param communityId: id of the community
     * @param memberId: id of the member who created the poll
     *
     * @return list of [PollRO]
     * */
    fun convertPoll(
        realm: Realm,
        communityId: String?,
        poll: _Poll_?,
        uuid: String?
    ): PollRO? {
        if (poll == null || communityId == null) return null
        return PollRO.build(poll.id.toString(), poll.text) {
            subText = poll.subText
            isSelected = poll.isSelected
            percentage = poll.percentage
            noVotes = poll.noVotes
            member = ChatDBUtil.getMember(realm, communityId, uuid)
        }
    }

    /**
     * convert [_ReactionMeta_] to [ReactionRO]
     * @param realm: Instance of realm
     * @param reactions: List of [_ReactionMeta_] to converted
     * @param communityId: id of the community
     *
     * @return list of [ReactionRO]
     * */
    private fun convertReactionsMeta(
        realm: Realm,
        communityId: String?,
        reactions: List<_ReactionMeta_>?,
    ): RealmList<ReactionRO> {
        return reactions.orEmpty().reversed().mapNotNull { reaction ->
            convertReactionMeta(realm, reaction, communityId)
        }.toRealmList()
    }

    /**
     * convert [_ReactionMeta_] to [ReactionRO]
     * @param realm: Instance of realm
     * @param reaction: [_ReactionMeta_] to converted
     * @param communityId: id of the community
     *
     * @return [ReactionRO]
     * */
    private fun convertReactionMeta(
        realm: Realm,
        reaction: _ReactionMeta_,
        communityId: String?,
    ): ReactionRO? {
        val memberRO =
            convertMember(reaction.member, communityId.toString()) ?: ChatDBUtil.getMember(
                realm,
                communityId,
                reaction.member?.sdkClientInfo?.uuid
            ) ?: return null
        return ReactionRO.build {
            member = memberRO
            this.reaction = reaction.reaction
        }
    }

    private fun convertReactions(
        realm: Realm,
        communityId: String?,
        reactions: List<_Reaction_>?
    ): RealmList<ReactionRO> {
        return reactions.orEmpty().reversed().mapNotNull { reaction ->
            convertReaction(realm, reaction, communityId)
        }.toRealmList()
    }

    private fun convertReaction(
        realm: Realm,
        reaction: _Reaction_,
        communityId: String?,
    ): ReactionRO? {
        val memberRO = ChatDBUtil.getMember(
            realm,
            communityId,
            reaction.member?.sdkClientInfo?.uuid
        ) ?: return null
        return ReactionRO.build {
            this.reaction = reaction.reaction
            member = memberRO
        }
    }

    /**
     * convert [_LinkOGTags_] to [LinkRO]
     * @param chatroomId: id of the chatroom
     * @param communityId: id of the community
     * @param link: [_LinkOGTags_] to be converted
     *
     * @return list of [ReactionRO]
     * */
    fun convertLink(
        chatroomId: String,
        communityId: String,
        link: _LinkOGTags_?
    ): LinkRO? {
        if (link == null || link.url.isNullOrEmpty()) {
            return null
        }
        return LinkRO.build(link.url) {
            this.chatroomId = chatroomId
            this.communityId = communityId
            title = link.title
            image = link.image
            description = link.description
        }
    }
}