package com.likeminds.internalsdk.dm.model

import com.google.gson.annotations.SerializedName

class CheckDMLimitRequest private constructor(
    @SerializedName("member_id")
    val memberId: String?,
    @SerializedName("uuid")
    val uuid: String?
) {
    class Builder {
        private var memberId: String? = null
        private var uuid: String? = null

        fun memberId(memberId: String?) = apply { this.memberId = memberId }
        fun uuid(uuid: String?) = apply { this.uuid = uuid }

        fun build() = CheckDMLimitRequest(memberId, uuid)
    }

    fun toBuilder(): Builder {
        return Builder().memberId(memberId)
            .uuid(uuid)
    }
}