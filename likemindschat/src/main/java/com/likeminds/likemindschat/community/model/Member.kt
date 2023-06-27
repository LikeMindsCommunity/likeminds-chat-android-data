package com.likeminds.likemindschat.community.model

import com.likeminds.likemindschat.user.model.SDKClientInfo

class Member private constructor(
    val id: String,
    val userUniqueId: String?,
    val name: String?,
    val email: String?,
    val headline: String?,
    val city: String?,
    val imageUrl: String?,
    val questionAnswers: List<Question>?,
    val state: Int?,
    val removeState: Int?,
    val isGuest: Boolean?,
    val customIntroText: String?,
    val customClickText: String?,
    val memberSince: String?,
    val communityName: String?,
    val isOwner: Boolean?,
    val customTitle: String?,
    val menu: List<MemberAction>?,
    val communityId: Int?,
    val chatroomId: Int?,
    val route: String?,
    val attendingStatus: Boolean?,
    val hasProfileImage: Boolean?,
    val updatedAt: Long?,
    val sdkClientInfo: SDKClientInfo?
) {

    class Builder {

        private var id: String = ""
        private var userUniqueId: String? = null
        private var name: String? = null
        private var email: String? = null
        private var headline: String? = null
        private var city: String? = null
        private var imageUrl: String? = null
        private var questionAnswers: List<Question>? = null
        private var state: Int? = null
        private var removeState: Int? = null
        private var isGuest: Boolean? = null
        private var customIntroText: String? = null
        private var customClickText: String? = null
        private var memberSince: String? = null
        private var communityName: String? = null
        private var isOwner: Boolean? = null
        private var customTitle: String? = null
        private var menu: List<MemberAction>? = null
        private var communityId: Int? = null
        private var chatroomId: Int? = null
        private var route: String? = null
        private var attendingStatus: Boolean? = null
        private var hasProfileImage: Boolean? = null
        private var updatedAt: Long? = null
        private var sdkClientInfo: SDKClientInfo? = null

        fun id(id: String) = apply { this.id = id }
        fun userUniqueId(userUniqueId: String?) = apply { this.userUniqueId = userUniqueId }
        fun name(name: String?) = apply { this.name = name }
        fun email(email: String?) = apply { this.email = email }
        fun headline(headline: String?) = apply { this.headline = headline }
        fun city(city: String?) = apply { this.city = city }
        fun imageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }
        fun questionAnswers(questionAnswers: List<Question>?) =
            apply { this.questionAnswers = questionAnswers }

        fun state(state: Int?) = apply { this.state = state }
        fun removeState(removeState: Int?) = apply { this.removeState = removeState }
        fun isGuest(isGuest: Boolean?) = apply { this.isGuest = isGuest }
        fun customIntroText(customIntroText: String?) =
            apply { this.customIntroText = customIntroText }

        fun customClickText(customClickText: String?) =
            apply { this.customClickText = customClickText }

        fun memberSince(memberSince: String?) = apply { this.memberSince = memberSince }
        fun communityName(communityName: String?) = apply { this.communityName = communityName }
        fun isOwner(isOwner: Boolean?) = apply { this.isOwner = isOwner }
        fun customTitle(customTitle: String?) = apply { this.customTitle = customTitle }
        fun menu(menu: List<MemberAction>?) = apply { this.menu = menu }
        fun communityId(communityId: Int?) = apply { this.communityId = communityId }
        fun chatroomId(chatroomId: Int?) = apply { this.chatroomId = chatroomId }
        fun route(route: String?) = apply { this.route = route }
        fun attendingStatus(attendingStatus: Boolean?) =
            apply { this.attendingStatus = attendingStatus }

        fun hasProfileImage(hasProfileImage: Boolean?) =
            apply { this.hasProfileImage = hasProfileImage }

        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }

        fun sdkClientInfo(sdkClientInfo: SDKClientInfo?) =
            apply { this.sdkClientInfo = sdkClientInfo }

        fun build() = Member(
            id,
            userUniqueId,
            name,
            email,
            headline,
            city,
            imageUrl,
            questionAnswers,
            state,
            removeState,
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
            sdkClientInfo
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .userUniqueId(userUniqueId)
            .name(name)
            .email(email)
            .headline(headline)
            .city(city)
            .imageUrl(imageUrl)
            .questionAnswers(questionAnswers)
            .state(state)
            .removeState(removeState)
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
    }
}