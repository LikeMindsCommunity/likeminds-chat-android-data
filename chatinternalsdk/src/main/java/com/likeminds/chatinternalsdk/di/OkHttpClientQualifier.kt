package com.likeminds.chatinternalsdk.di

import javax.inject.Qualifier

/**
 * To be used for HTTPS API Calls
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpAPICallQualifier

/**
 * To be used for WebSocket Connection
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebSocketQualifier
