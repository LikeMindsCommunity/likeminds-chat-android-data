package com.likeminds.chatinternalsdk.db.util

object DbKey {

    const val ID = "id"
    const val TEMPORARY_ID = "temporaryId"
    const val UID = "uid"
    const val CHATROOM_ID = "chatroomId"
    const val COMMUNITY_ID = "communityId"
    const val CHATROOM = "chatroom"
    const val COMMUNITY = "community"
    const val LAST_SEEN = "lastSeen"
    const val REMOVE_STATE = "removeState"
    const val DELETED_BY = "deletedBy"
    const val DELETED_BY_MEMBER = "deletedByMember"
    const val IS_DRAFT = "isDraft"
    const val IS_OWNER = "isOwner"
    const val IS_GUEST = "isGuest"
    const val UPDATED_AT = "updatedAt"
    const val CREATED_EPOCH = "createdEpoch"
    const val LOCAL_SAVED_EPOCH = "localSavedEpoch"
    const val TOPIC_ID = "topicId"

    const val RELATIONSHIP_NEEDED = "relationshipNeeded"

    const val CHATROOM_OBJECT_ID = "chatroom.id"
    const val COMMUNITY_OBJECT_ID = "community.id"
    const val MEMBER_OBJECT_ID = "member.id"
    const val MEMBER_OBJECT_UUID = "member.uuid"
    const val MEMBER_OBJECT_UID = "member.uid"
    const val STATE = "state"

    const val CHATROOM_EXPIRY_TIME = "chatroomExpiryTime"
    const val CHATROOM_WITH_USER_ID = "chatroomWithUserId"
    const val FOLLOW_STATUS = "followStatus"

    const val CONVERSATIONS_LIMIT = 200L
    const val INACTIVE_CHATROOM_HOME_LIMIT = 10L

    const val REPLY_CONVERSATION = "replyConversation"
    const val REPLY_CONVERSATION_ID = "replyConversationId"

    const val CHATROOM_HEADER = "header"

    const val ANSWER = "answer"

    const val NAME = "name"

    const val ATTENDING_STATUS = "attendingStatus"
    const val DATE_TIME = "dateTime"
    const val END_DATE = "endDate"
    const val TYPE = "type"
    const val POLL_TYPE_TEXT = "pollTypeText"
    const val UPLOAD_WORKER_UUID = "uploadWorkerUUID"
    const val WORKER_UUID = "workerUUID"
    const val ATTACHMENTS_UPLOADED = "attachmentsUploaded"
    const val ATTACHMENTS_COUNT = "attachmentCount"
    const val HAS_BEEN_NAMED = "hasBeenNamed"
    const val HEADER = "header"
    const val TITLE = "title"
    const val ANSWER_TEXT = "answerText"
    const val POLLS_COUNT = "pollsCount"
    const val DOWNLOADABLE_CONTENT_TYPES = "downloadableContentTypes"
    const val TOTAL_RESPONSE_COUNT = "totalResponseCount"
    const val MUTE_STATUS = "muteStatus"
    const val UNSEEN_COUNT = "unseenCount"
    const val LAST_CONVERSATION_CREATED_EPOCH = "lastConversationRO.createdEpoch"
    const val TIMESTAMP = "timestamp"
}