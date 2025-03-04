package com.likeminds.likemindschat.helper.model

enum class LMSeverity(val severityName: String, val severityLevel: Int) {
    INFO("info", 0),
    DEBUG("debug", 1),
    NOTICE("notice", 2),
    WARNING("warning", 3),
    ERROR("error", 4),
    CRITICAL("critical", 5),
    ALERT("alert", 6),
    EMERGENCY("emergency", 7),
    DEFAULT("default", 8)
}