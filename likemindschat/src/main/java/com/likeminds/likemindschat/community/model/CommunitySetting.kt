package com.likeminds.likemindschat.community.model

class CommunitySetting private constructor(
    val enabled: Boolean,
    val settingSubTitle: String,
    val settingTitle: String,
    val settingType: String,
    val enabledBy: Int?
) {

    class Builder {
        private var enabled: Boolean = true
        private var settingSubTitle: String = ""
        private var settingTitle: String = ""
        private var settingType: String = ""
        private var enabledBy: Int? = null

        fun enabled(enabled: Boolean) = apply { this.enabled = enabled }
        fun settingSubTitle(settingSubTitle: String) =
            apply { this.settingSubTitle = settingSubTitle }

        fun settingTitle(settingTitle: String) = apply { this.settingTitle = settingTitle }
        fun settingType(settingType: String) = apply { this.settingType = settingType }
        fun enabledBy(enabledBy: Int?) = apply { this.enabledBy = enabledBy }

        fun build() = CommunitySetting(
            enabled,
            settingSubTitle,
            settingTitle,
            settingType,
            enabledBy
        )
    }

    fun toBuilder(): Builder {
        return Builder().enabled(enabled)
            .enabledBy(enabledBy)
            .settingType(settingType)
            .settingTitle(settingTitle)
            .settingSubTitle(settingSubTitle)
    }

    override fun toString(): String {
        return buildString {
            append("CommunitySetting(enabled=")
            append(enabled)
            append(", settingSubTitle='")
            append(settingSubTitle)
            append("', settingTitle='")
            append(settingTitle)
            append("', settingType='")
            append(settingType)
            append("', enabledBy=")
            append(enabledBy)
            append(")")
        }
    }
}