package com.likeminds.internalsdk.sync.worker

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.user.util.UserPreferences
import com.likeminds.internalsdk.utils.measureExecution
import io.realm.Realm

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
            deleteNonRequiredData()
        }
        return Result.success()
    }

    private fun deleteNonRequiredData() {
        val realm = Realm.getDefaultInstance()
        ChatDBUtil.write(realm) { realmWrite ->
            val appConfig = ChatDBUtil.getAppConfig(realm) ?: return@write
            val communityIds = appConfig.communities.toTypedArray()

            val removedMembers = realmWrite.where(MemberRO::class.java)
                .equalTo(DbKey.ID, userPreferences.getLMMemberId())
                .isNull(DbKey.CHATROOM_ID)
                .not()
                .`in`(DbKey.COMMUNITY_ID, communityIds)
                .distinct(DbKey.COMMUNITY_ID)
                .findAll()

            val removedCommunityIds = removedMembers.mapNotNull { it.communityId }.toTypedArray()

            //Delete everything
            realmWrite.where(MemberRO::class.java)
                .`in`(DbKey.COMMUNITY_ID, removedCommunityIds)
                .findAll()
                .deleteAllFromRealm()
            realmWrite.where(ConversationRO::class.java)
                .`in`(DbKey.COMMUNITY_ID, removedCommunityIds)
                .findAll()
                .deleteAllFromRealm()
            realmWrite.where(ChatroomRO::class.java)
                .`in`(DbKey.COMMUNITY_ID, removedCommunityIds)
                .findAll()
                .deleteAllFromRealm()
            realmWrite.where(CommunityRO::class.java)
                .`in`(DbKey.ID, removedCommunityIds)
                .findAll()
                .deleteAllFromRealm()
        }
        realm.close()
    }

}