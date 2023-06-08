package com.likeminds.internalsdk.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.AppConfigRO
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.user.model._UserMetaResponse_
import com.likeminds.internalsdk.utils.MAX_RETRY_COUNT
import com.likeminds.internalsdk.utils.measureExecution
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking

class AppConfigWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val api = GroupChatSDK.getInstance().getUserApi()

    private val isFirstTime = workerParameters.inputData.getBoolean(INPUT_DATA_IS_FIRST_TIME, false)

    companion object {

        const val NAME = "App Config Worker"
        const val INPUT_DATA_IS_FIRST_TIME = "is_first_time"
    }

    override fun doWork(): Result {
        return measureExecution("$NAME, params: isFirstTime: $isFirstTime") {
            val appConfig = runBlocking {
                fetchAppConfig()
            }
            when {
                appConfig != null -> {
                    runBlocking {
                        val realm = Realm.open(GroupChatSDK.getRealmConfiguration())
                        saveAppConfig(realm, appConfig)
                        realm.close()
                    }
                    Result.success()
                }

                runAttemptCount <= MAX_RETRY_COUNT -> {
                    Result.retry()
                }

                else -> {
                    //Actually result is failed but returning failure will stop the work manager to proceed to next chain
                    //So returning success
                    Result.retry()
                }
            }
        }
    }

    /**
     * Fetches app meta from network using a suspendable function
     */
    private suspend fun fetchAppConfig(): _UserMetaResponse_? {
        return when (val response = api.getUserMeta()) {
            is NetworkResponse.Error -> {
                Log.d("LikeMinds", "user/meta failed,${response.body.errorMessage}")
                null
            }

            is NetworkResponse.Success -> {
                response.body.data
            }
        }
    }

    /**
     * Stores app meta in Realm DB.
     */
    private suspend fun saveAppConfig(realm: Realm, appConfigResponse: _UserMetaResponse_) {
        val appConfig = ChatDBUtil.getAppConfig(realm)

        if (appConfig != null) {
            appConfig.communities = appConfigResponse.communityIds.map { it.id }.toRealmList()
        } else {
            val appConfigRO = AppConfigRO().apply {
                communities = appConfigResponse.communityIds.map {
                    it.id
                }.toRealmList()
            }
            ChatDBUtil.insertOrUpdate(realm, appConfigRO)
        }
    }
}