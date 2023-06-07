package com.likeminds.internalsdk.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ConversationRO : RealmObject {

    @PrimaryKey
    var id: String = ""
    var chatroomId: String = ""
    var communityId: String = ""
    var member: MemberRO? = null
    var answer: String = ""
    var state: Int = 0
    var createdEpoch: Long = 0L
    var createdAt: String? = null
    var attachments: RealmList<AttachmentRO> = realmListOf()
    var link: LinkRO? = null
    var date: String? = null
    var isEdited: Boolean? = null
    var replyConversationId: String? = null
    var replyConversation: ConversationRO? = null
    var deletedBy: String? = null
    var attachmentCount: Int? = null
    var attachmentsUploaded: Boolean? = null
    var uploadWorkerUUID: String? = null
    var localSavedEpoch: Long = 0L
    var temporaryId: String? = null
    var reactions: RealmList<ReactionRO> = realmListOf()
    var isAnonymous: Boolean? = null
    var allowAddOption: Boolean? = null
    var pollType: Int? = null
    var pollTypeText: String? = null
    var submitTypeText: String? = null
    var expiryTime: Long? = null
    var multipleSelectNum: Int? = null
    var multipleSelectState: Int? = null
    var polls: RealmList<PollRO> = realmListOf()
    var pollAnswerText: String? = null
    var toShowResults: Boolean? = null
    var replyChatRoomId: String? = null
    var lastUpdatedAt: Long = 0L
}