package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

class _CommunitySetting_ private constructor(
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("setting_sub_title")
    val settingSubTitle: String,
    @SerializedName("setting_title")
    val settingTitle: String,
    @SerializedName("setting_type")
    val settingType: String,
    @SerializedName("enabled_by")
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

        fun build() = _CommunitySetting_(
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