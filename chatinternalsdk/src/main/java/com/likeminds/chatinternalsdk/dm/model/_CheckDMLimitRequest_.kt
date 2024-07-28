package com.likeminds.chatinternalsdk.dm.model

import com.google.gson.annotations.SerializedName

class _CheckDMLimitRequest_ private constructor(
    @SerializedName("uuid")
    val uuid: String
) {
    class Builder {
        private var uuid: String = ""

        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = _CheckDMLimitRequest_(uuid)
    }

    fun toBuilder(): Builder {
        return Builder().uuid(uuid)
    }
}