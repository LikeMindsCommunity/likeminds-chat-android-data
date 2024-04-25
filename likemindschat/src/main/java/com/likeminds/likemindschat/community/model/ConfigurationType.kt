package com.likeminds.likemindschat.community.model

enum class ConfigurationType(val value: String) {
    NONE("none"),
    MEDIA_LIMITS("media_limits"),
    FEED_METADATA("feed_metadata"),
    PROFILE_METADATA("profile_metadata"),
    NSFW_FILTERING("nsfw_filtering"),
    WIDGET_METADATA("widgets_metadata"),
    GUEST_FLOW_METADATA("guest_flow_metadata")
}