package com.likeminds.internalsdk.sync.worker

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.user.util.UserPreferences
import com.likeminds.internalsdk.utils.measureExecution
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking

/**
 * Worker to remove unwanted data stored in the local database
 * * Communities which I am not part of, except any community which have a guest chatroom
 * @param context Context object
 * @param workerParams Contains meta data and input data of this worker
 */
class DatabaseCleanupWorker(
    val context: Context,
    val workerParams: WorkerParameters
) : Worker(context, workerParams) {

    private val userPreferences = UserPreferences(context as Application)

    companion object {

        const val NAME = "Database Cleanup Worker"
    }

    override fun doWork(): Result {
        measureExecution(NAME) {
            runBlocking {
                deleteNonRequiredData()
            }
        }
        return Result.success()
    }

    private suspend fun deleteNonRequiredData() {
        val realm = Realm.open(GroupChatSDK.getRealmConfiguration())
        val appConfig = ChatDBUtil.getAppConfig(realm) ?: return
        val communityIds = appConfig.communities.toTypedArray()

        val removedMembers = realm.query(
            MemberRO::class,
            "${DbKey.ID} == $0 AND ${DbKey.CHATROOM_ID} == $1 AND ${DbKey.COMMUNITY_ID} !IN $2",
            userPreferences.getLMMemberId(),
            null,
            communityIds
        ).distinct(DbKey.COMMUNITY_ID)
            .find()

        val removedCommunityIds = removedMembers.mapNotNull { it.communityId }.toTypedArray()

        realm.write {
            val deleteMembers =
                this.query(MemberRO::class, "${DbKey.COMMUNITY_ID} IN $0", removedCommunityIds)
                    .find()
            delete(deleteMembers)

            val deleteConversations =
                this.query(
                    ConversationRO::class,
                    "${DbKey.COMMUNITY_ID} IN $0",
                    removedCommunityIds
                )
                    .find()
            delete(deleteConversations)

            val deleteChatrooms =
                this.query(ChatroomRO::class, "${DbKey.COMMUNITY_ID} IN $0", removedCommunityIds)
                    .find()
            delete(deleteChatrooms)

            val deleteCommunityRO =
                this.query(CommunityRO::class, "${DbKey.COMMUNITY_ID} IN $0", removedCommunityIds)
                    .find()
            delete(deleteCommunityRO)
        }
        realm.close()
    }

}