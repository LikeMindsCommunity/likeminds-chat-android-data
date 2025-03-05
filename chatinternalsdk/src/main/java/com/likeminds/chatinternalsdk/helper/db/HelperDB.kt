package com.likeminds.chatinternalsdk.helper.db

import com.likeminds.chatinternalsdk.db.models.LMLogRO
import com.likeminds.chatinternalsdk.helper.model._ClearLogsRequest_
import com.likeminds.chatinternalsdk.helper.model._InsertLogRequest_
import io.realm.Realm
import io.realm.RealmResults

interface HelperDB {

    fun insertLog(insertLogRequest: _InsertLogRequest_)

    fun getLogs(realm: Realm): RealmResults<LMLogRO>

    fun clearLogs(clearLogsRequest: _ClearLogsRequest_)
}