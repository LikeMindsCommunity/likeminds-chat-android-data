package com.likeminds.chatinternalsdk.helper.db

import com.likeminds.chatinternalsdk.db.models.LMLogRO
import com.likeminds.chatinternalsdk.helper.api.HelperReceiver
import com.likeminds.chatinternalsdk.helper.model._ClearLogsRequest_
import com.likeminds.chatinternalsdk.helper.model._InsertLogRequest_
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class HelperDBImpl @Inject constructor(
    private val helperReceiver: HelperReceiver
) : HelperDB {

    override fun insertLog(insertLogRequest: _InsertLogRequest_) {
        helperReceiver.insertLog(insertLogRequest)
    }

    override fun getLogs(realm: Realm): RealmResults<LMLogRO> {
        return helperReceiver.getLogs(realm)
    }

    override fun clearLogs(clearLogsRequest: _ClearLogsRequest_) {
        helperReceiver.clearLogs(clearLogsRequest)
    }
}