package com.likeminds.likemindschat.user.model

class User private constructor(
    val id: String,
    val imageUrl: String,
    val isGuest: Boolean,
    val name: String,
    val organisationName: String?,
    val sdkClientInfo: SDKClientInfo?,
    val isDeleted: Boolean?,
    val customTitle: String?,
    val updatedAt: Long?,
    val userUniqueId: String,
    val uuid: String,
    val roles: List<UserRole>
) {
    class Builder {
        private var id: String = ""
        private var imageUrl: String = ""
        private var isGuest: Boolean = false
        private var name: String = ""
        private var organisationName: String? = null
        private var sdkClientInfo: SDKClientInfo? = null
        private var isDeleted: Boolean? = null
        private var customTitle: String? = null
        private var updatedAt: Long? = null
        private var userUniqueId: String = ""
        private var uuid: String = ""
        private var roles: List<UserRole> = emptyList()

        fun id(id: String) = apply { this.id = id }
        fun imageUrl(imageUrl: String) = apply { this.imageUrl = imageUrl }
        fun isGuest(isGuest: Boolean) = apply { this.isGuest = isGuest }
        fun name(name: String) = apply { this.name = name }
        fun organisationName(organisationName: String?) =
            apply { this.organisationName = organisationName }

        fun sdkClientInfo(sdkClientInfo: SDKClientInfo?) =
            apply { this.sdkClientInfo = sdkClientInfo }

        fun isDeleted(isDeleted: Boolean?) = apply { this.isDeleted = isDeleted }
        fun customTitle(customTitle: String?) = apply { this.customTitle = customTitle }
        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }
        fun userUniqueId(userUniqueId: String) = apply { this.userUniqueId = userUniqueId }
        fun uuid(uuid: String) = apply { this.uuid = uuid }
        fun roles(roles: List<UserRole>) = apply { this.roles = roles }

        fun build() = User(
            id,
            imageUrl,
            isGuest,
            name,
            organisationName,
            sdkClientInfo,
            isDeleted,
            customTitle,
            updatedAt,
            userUniqueId,
            uuid,
            roles
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .imageUrl(imageUrl)
            .isGuest(isGuest)
            .name(name)
            .organisationName(organisationName)
            .sdkClientInfo(sdkClientInfo)
            .isDeleted(isDeleted)
            .customTitle(customTitle)
            .updatedAt(updatedAt)
            .userUniqueId(userUniqueId)
            .uuid(uuid)
            .roles(roles)
    }
}

data class SDKClientInfo(
    val community: Int,
    val user: String,
    val userUniqueId: String,
    val uuid: String
)
