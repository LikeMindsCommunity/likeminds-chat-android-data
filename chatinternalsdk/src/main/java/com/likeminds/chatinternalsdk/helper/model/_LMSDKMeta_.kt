package com.likeminds.chatinternalsdk.helper.model

import com.google.gson.annotations.SerializedName

class _LMSDKMeta_ private constructor(
    @SerializedName("data_layer_version")
    val dataLayerVersion: String?,
    @SerializedName("core_version")
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