package com.likeminds.internalsdk.conversation

import android.os.Build
import com.likeminds.internalsdk.conversation.api.ConversationNetworkApi
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.ROConverter
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.poll.model._Poll_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import javax.inject.Inject

class ConversationReceiver @Inject constructor(
    private val conversationNetworkApi: ConversationNetworkApi
) {

    /**
     * API Functions
     */

    suspend fun createConversation(
        request: _CreateConversationRequest_
    ): NetworkResponse<APIResponse<_CreateConversationResponse_>> {
        return conversationNetworkApi.createConversation(request)
    }

    suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>> {
        return conversationNetworkApi.editConversation(request)
    }

    suspend fun deleteConversation(
        request: _DeleteConversationRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationResponse_>> {
        return conversationNetworkApi.deleteConversation(request)
    }

    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.putReaction(request)
    }

    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.deleteReaction(request)
    }

    /**
     * Db Functions
     */

    fun getConversation(conversationId: String): ConversationRO? {
        val realm = Realm.getDefaultInstance()
        val conversationRO = ChatDBUtil.getConversation(realm, conversationId)
        realm.close()
        return conversationRO
    }

    fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    ) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getConversation(it, conversationId)?.let { conversation ->
                conversation.answer = conversationText
                conversation.isEdited = true
                conversation.link = ROConverter.convertLink(
                    conversation.chatroomId,
                    conversation.communityId,
                    linkOgTags
                )
            }
        })
    }

    fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversation ->
                val containsAnyVote = conversation.polls.count { (it.noVotes ?: 0) > 0 } > 0
                allPollItems.forEachIndexed { index, poll ->
                    val pollFromDb = conversation.polls[index]
                    if (pollFromDb?.isSelected != poll.isSelected
                        || pollFromDb?.noVotes != poll.noVotes
                        || pollFromDb?.percentage != poll.percentage
                    ) {
                        pollFromDb?.apply {
                            isSelected = poll.isSelected
                            noVotes = poll.noVotes
                            percentage = poll.percentage
                        }
                    }
                }

                val singleMemberVotes = allPollItems.count { poll -> poll.noVotes == 1 }
                if (singleMemberVotes >= 1 && !containsAnyVote) {
                    //This means the current user has voted as a first user.
                    conversation.pollAnswerText = "1 member voted on this poll"
                }

                conversation.getChatroom()?.let { chatroomRO ->
                    val currentMillis = System.currentTimeMillis()
                    chatroomRO.followStatus = true
                    chatroomRO.updatedAt = currentMillis
                }
            }
        })
    }

    fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversationRO ->
                ROConverter.convertPoll(
                    realm,
                    conversationRO.communityId,
                    newPollItem,
                    newPollItem.userId
                )?.let { pollRO ->
                    conversationRO.polls.add(pollRO)
                }
            }
        })
    }

    fun updateDeletedConversations(conversationsId: List<String>) {
        ChatDBUtil.writeAsync({ realm ->
            val conversations = realm.where(ConversationRO::class.java)
                .`in`(DbKey.ID, conversationsId.toTypedArray())
                .findAll()

            //get logged in member
            val userRO = realm.where(UserRO::class.java).findFirst()

            conversations.setString(DbKey.DELETED_BY, userRO?.id)
        })
    }

    fun updateConversationReaction(reaction: String, conversationId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)
                ?.let { conversationRO ->

                    //get logged in member
                    val userRO = realm.where(UserRO::class.java).findFirst()

                    //Remove member previous reactions if any
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        conversationRO.reactions.removeIf { reaction ->
                            reaction.member?.id == userRO?.id
                        }
                    } else {
                        val reactionRO = conversationRO.reactions.find { reaction ->
                            reaction.member?.id == userRO?.id
                        }
                        conversationRO.reactions.remove(reactionRO)
                    }

                    //Add new member reaction
                    val memberObj =
                        ChatDBUtil.getMember(realm, conversationRO.communityId, userRO?.id)
                            ?: return@let
                    val messageReaction = ReactionRO.build {
                        this.reaction = reaction
                        member = memberObj
                    }
                    val addIndex = conversationRO.reactions.lastIndex + 1
                    conversationRO.reactions.add(addIndex, messageReaction)
                }
        })
    }

    fun removeConversationReaction(conversationId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversationRO ->

                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                //Remove member previous reactions if any
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    conversationRO.reactions.removeIf { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                } else {
                    val reactionRO = conversationRO.reactions.find { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                    conversationRO.reactions.remove(reactionRO)
                }
            }
        })
    }
}