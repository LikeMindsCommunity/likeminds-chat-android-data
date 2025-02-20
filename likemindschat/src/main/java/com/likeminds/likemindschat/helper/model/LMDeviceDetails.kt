package com.likeminds.likemindschat.helper.model

class LMDeviceDetails private constructor(
    val versionOS: String,
    val deviceName: String,
    val screenHeight: Int,
    val screenWidth: Int,
    val wifi: Boolean
) {
    class Builder {
        private var versionOS: String = ""
        private var deviceName: String = ""
        private var screenHeight: Int = 0
        private var screenWidth: Int = 0
        private var wifi: Boolean = false

        fun versionOS(versionOS: String) = apply {
            this.versionOS = versionOS
        }

        fun deviceName(deviceName: String) = apply {
            this.deviceName = deviceName
        }

        fun screenHeight(screenHeight: Int) = apply {
            this.screenHeight = screenHeight
        }

        fun screenWidth(screenWidth: Int) = apply {
            this.screenWidth = screenWidth
        }

        fun wifi(wifi: Boolean) = apply {
            this.wifi = wifi
        }

        fun build() = LMDeviceDetails(
            versionOS,
            deviceName,
            screenHeight,
            screenWidth,
            wifi
        )
    }

    fun toBuilder(): Builder {
        return Builder().versionOS(versionOS)
            .deviceName(deviceName)
            .screenHeight(screenHeight)
            .screenWidth(screenWidth)
            .wifi(wifi)
    }
}