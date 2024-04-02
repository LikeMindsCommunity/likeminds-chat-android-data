package com.likeminds.likemindschat.dm.model

class CreateDMChatroomRequest private constructor(
    val uuid: String
) {
    class Builder {
        private var uuid: String = ""

        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = CreateDMChatroomRequest(uuid)
    }

    fun toBuilder(): Builder {
        return Builder().uuid(uuid)
    }
}