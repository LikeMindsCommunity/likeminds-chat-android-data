package com.likeminds.internalsdk.db

import android.util.Log
import com.likeminds.internalsdk.db.models.*
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.types.RealmObject

object ChatDBUtil {

    suspend fun insertOrUpdate(realm: Realm, roObject: RealmObject) {
        realm.write {
            copyToRealm(roObject, updatePolicy = UpdatePolicy.ALL)
        }
    }

    fun getChatroom(realm: Realm, chatroomId: String?): ChatroomRO? {
        if (chatroomId == null) {
            return null
        }
        return realm.query(ChatroomRO::class, "id == $0", chatroomId).first().find()
    }

    fun getConversation(realm: Realm, id: String?): ConversationRO? {
        if (id.isNullOrEmpty()) {
            return null
        }
        return realm.query(ConversationRO::class, "id == $0", id).first().find()
    }

    fun getMember(
        realm: Realm,
        communityId: String?,
        memberId: String?
    ): MemberRO? {
        val uid1 = "$memberId#${communityId}"
        val member = getMemberByUid(realm, uid1)
        if (member == null) {
            Log.e("Member not found", uid1)
        }
        return member
    }

    fun getMemberByUid(realm: Realm, uid: String): MemberRO? {
        return realm.query(MemberRO::class, "uid == $0", uid).first().find()
    }
}