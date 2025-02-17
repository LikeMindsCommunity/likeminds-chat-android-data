package com.likeminds.chatinternalsdk.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpAPICallQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebSocketQualifier
