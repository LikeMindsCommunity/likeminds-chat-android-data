# Model Class
-keep class com.likeminds.internalsdk.chatroom.model.** { *; }
-keep class com.likeminds.internalsdk.community.model.** { *; }
-keep class com.likeminds.internalsdk.conversation.model.** { *; }
-keep class com.likeminds.internalsdk.db.model.** { *; }
-keep class com.likeminds.internalsdk.helper.model.** { *; }
-keep class com.likeminds.internalsdk.homefeed.model.** { *; }
-keep class com.likeminds.internalsdk.moderation.model.** { *; }
-keep class com.likeminds.internalsdk.notification.model.** { *; }
-keep class com.likeminds.internalsdk.poll.model.** { *; }
-keep class com.likeminds.internalsdk.refreshtoken.model.** { *; }
-keep class com.likeminds.internalsdk.sdk.model.** { *; }
-keep class com.likeminds.internalsdk.search.model.** { *; }
-keep class com.likeminds.internalsdk.sync.model.** { *; }
-keep class com.likeminds.internalsdk.user.model.** { *; }
-keep class com.likeminds.internalsdk.utils.retrofit.model.** { *; }
-keep class com.likeminds.internalsdk.utils.retrofit.model.** { *; }

# Retrofit
-keep class retrofit2.Response { *; }
-keep class * extends retrofit2.Response
-keep class retrofit2.Converter { *; }
-keep class * extends retrofit2.Converter

# Kotlin coroutines flow
-keep class kotlinx.coroutines.**

# Gson
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken