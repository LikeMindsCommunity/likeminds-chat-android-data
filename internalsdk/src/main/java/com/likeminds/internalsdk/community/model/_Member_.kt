package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.user.model._SDKClientInfo_

class _Member_ private constructor(
    @SerializedName("id")
    val id: String,
    @SerializedName("user_unique_id")
    val userUniqueId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("question_answers")
    val questionAnswers: List<_Question_>?,
    @SerializedName("state")
    val state: Int?,
    @SerializedName("is_guest")
    val isGuest: Boolean,
    @SerializedName("custom_intro_text")
    val customIntroText: String?,
    @SerializedName("custom_click_text")
    val customClickText: String?,
    @SerializedName("member_since")
    val memberSince: String?,
    @SerializedName("community_name")
    val communityName: String?,
    @SerializedName("is_owner")
    val isOwner: Boolean?,
    @SerializedName("custom_title")
    val customTitle: String?,
    @SerializedName("menu")
    val menu: List<_MemberAction_>?,
    @SerializedName("community_id")
    val communityId: Int?,
    @SerializedName("chatroom_id")
    val chatroomId: Int?,
    @SerializedName("route")
    val route: String?,
    @SerializedName("attending_status")
    val attendingStatus: Boolean?,
    @SerializedName("has_profile_image")
    val hasProfileImage: Boolean?,
    @SerializedName("updated_at")
    val updatedAt: Long?,
    @SerializedName("sdk_client_info")
    val sdkClientInfo: _SDKClientInfo_?,
    @SerializedName("uuid")
    val uuid: String,
) {

    class Builder {

        private var id: String = ""
        private var userUniqueId: String = ""
        private var name: String = ""
        private var imageUrl: String? = null
        private var questionAnswers: List<_Question_>? = null
        private var state: Int? = null
        private var isGuest: Boolean = false
        private var customIntroText: String? = null
        private var customClickText: String? = null
        private var memberSince: String? = null
        private var communityName: String? = null
        private var isOwner: Boolean? = null
        private var customTitle: String? = null
        private var menu: List<_MemberAction_>? = null
        private var communityId: Int? = null
        private var chatroomId: Int? = null
        private var route: String? = null
        private var attendingStatus: Boolean? = null
        private var hasProfileImage: Boolean? = null
        private var updatedAt: Long? = null
        private var sdkClientInfo: _SDKClientInfo_? = null
        private var uuid: String = ""

        fun id(id: String) = apply { this.id = id }
        fun userUniqueId(userUniqueId: String) = apply { this.userUniqueId = userUniqueId }
        fun name(name: String) = apply { this.name = name }
        fun imageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }
        fun questionAnswers(questionAnswers: List<_Question_>?) =
            apply { this.questionAnswers = questionAnswers }

        fun state(state: Int?) = apply { this.state = state }
        fun isGuest(isGuest: Boolean) = apply { this.isGuest = isGuest }
        fun customIntroText(customIntroText: String?) =
            apply { this.customIntroText = customIntroText }

        fun customClickText(customClickText: String?) =
            apply { this.customClickText = customClickText }

        fun memberSince(memberSince: String?) = apply { this.memberSince = memberSince }
        fun communityName(communityName: String?) = apply { this.communityName = communityName }
        fun isOwner(isOwner: Boolean?) = apply { this.isOwner = isOwner }
        fun customTitle(customTitle: String?) = apply { this.customTitle = customTitle }
        fun menu(menu: List<_MemberAction_>?) = apply { this.menu = menu }
        fun communityId(communityId: Int?) = apply { this.communityId = communityId }
        fun chatroomId(chatroomId: Int?) = apply { this.chatroomId = chatroomId }
        fun route(route: String?) = apply { this.route = route }
        fun attendingStatus(attendingStatus: Boolean?) =
            apply { this.attendingStatus = attendingStatus }

        fun hasProfileImage(hasProfileImage: Boolean?) =
            apply { this.hasProfileImage = hasProfileImage }

        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }
        fun sdkClientInfo(sdkClientInfo: _SDKClientInfo_?) =
            apply { this.sdkClientInfo = sdkClientInfo }

        fun uuid(uuid: String) = apply { this.uuid = uuid }

        fun build() = _Member_(
            id,
            userUniqueId,
            name,
            imageUrl,
            questionAnswers,
            state,
            isGuest,
            customIntroText,
            customClickText,
            memberSince,
            communityName,
            isOwner,
            customTitle,
            menu,
            communityId,
            chatroomId,
            route,
            attendingStatus,
            hasProfileImage,
            updatedAt,
            sdkClientInfo,
            uuid
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .userUniqueId(userUniqueId)
            .name(name)
            .imageUrl(imageUrl)
            .questionAnswers(questionAnswers)
            .state(state)
            .isGuest(isGuest)
            .customIntroText(customIntroText)
            .customClickText(customClickText)
            .memberSince(memberSince)
            .communityName(communityName)
            .isOwner(isOwner)
            .customTitle(customTitle)
            .menu(menu)
            .communityId(communityId)
            .chatroomId(chatroomId)
            .route(route)
            .attendingStatus(attendingStatus)
            .hasProfileImage(hasProfileImage)
            .updatedAt(updatedAt)
            .sdkClientInfo(sdkClientInfo)
            .uuid(uuid)
    }
}