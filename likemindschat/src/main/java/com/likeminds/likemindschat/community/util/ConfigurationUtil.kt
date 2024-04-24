package com.likeminds.likemindschat.community.util

import com.likeminds.likemindschat.community.model.ConfigurationType


object ConfigurationUtil {
    /**
     * Get the enum of [ConfigurationType] using String value of type
     */
    fun String.getConfigurationType(): ConfigurationType {
        return when (this) {
            ConfigurationType.NONE.value -> ConfigurationType.NONE
            ConfigurationType.MEDIA_LIMITS.value -> ConfigurationType.MEDIA_LIMITS
            ConfigurationType.FEED_METADATA.value -> ConfigurationType.FEED_METADATA
            ConfigurationType.PROFILE_METADATA.value -> ConfigurationType.PROFILE_METADATA
            ConfigurationType.NSFW_FILTERING.value -> ConfigurationType.NSFW_FILTERING
            ConfigurationType.WIDGET_METADATA.value -> ConfigurationType.WIDGET_METADATA
            ConfigurationType.GUEST_FLOW_METADATA.value -> ConfigurationType.GUEST_FLOW_METADATA
            else -> ConfigurationType.NONE
        }
    }
}