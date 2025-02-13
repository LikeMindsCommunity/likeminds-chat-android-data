package com.likeminds.chatinternalsdk.helper.model

class _LMSDKMeta_ private constructor(
    val dataLayerVersion: String?,
    val coreVersion: String?
) {
    class Builder {
        private var dataLayerVersion: String? = null
        private var coreVersion: String? = null

        fun dataLayerVersion(dataLayerVersion: String?) = apply {
            this.dataLayerVersion = dataLayerVersion
        }

        fun coreVersion(coreVersion: String?) = apply {
            this.coreVersion = coreVersion
        }

        fun build() = _LMSDKMeta_(dataLayerVersion, coreVersion)
    }

    fun toBuilder(): Builder {
        return Builder().dataLayerVersion(dataLayerVersion)
            .coreVersion(coreVersion)
    }
}