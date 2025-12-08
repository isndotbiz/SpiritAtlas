# SpiritAtlas ProGuard Rules
# Comprehensive obfuscation and optimization rules

#===============================================================================
# GENERAL SETTINGS
#===============================================================================

# Preserve line numbers for debugging crash reports
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve annotations
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes EnclosingMethod

#===============================================================================
# KOTLIN
#===============================================================================

-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Kotlin Serialization
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault
-dontwarn kotlinx.serialization.**
-keep,includedescriptorclasses class com.spiritatlas.**$$serializer { *; }
-keepclassmembers class com.spiritatlas.** {
    *** Companion;
}
-keepclasseswithmembers class com.spiritatlas.** {
    kotlinx.serialization.KSerializer serializer(...);
}

#===============================================================================
# HILT / DAGGER
#===============================================================================

-dontwarn dagger.hilt.**
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep Hilt generated classes
-keep class **_HiltModules** { *; }
-keep class **_HiltComponents** { *; }
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }

# Keep @Inject annotated constructors
-keepclasseswithmembers class * {
    @javax.inject.Inject <init>(...);
}

# Keep @HiltViewModel annotated classes
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

#===============================================================================
# RETROFIT / OKHTTP / MOSHI
#===============================================================================

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }

# Moshi
-dontwarn com.squareup.moshi.**
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier @interface *
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
}
-keepclassmembers class com.squareup.moshi.internal.Util {
    private static java.lang.String getKotlinMetadataClassName();
}

# Keep Moshi JSON adapters
-keep class **JsonAdapter { *; }
-keep class com.spiritatlas.data.api.** { *; }
-keep class com.spiritatlas.data.model.** { *; }

#===============================================================================
# ROOM DATABASE
#===============================================================================

-dontwarn androidx.room.**
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Keep Room database entities
-keep class com.spiritatlas.data.db.** { *; }
-keep class com.spiritatlas.data.entity.** { *; }

#===============================================================================
# JETPACK COMPOSE
#===============================================================================

-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }

# Keep Composable functions (needed for reflection)
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Keep Stable/Immutable annotations
-keep @androidx.compose.runtime.Stable class * { *; }
-keep @androidx.compose.runtime.Immutable class * { *; }

#===============================================================================
# COIL (Image Loading)
#===============================================================================

-dontwarn coil.**
-keep class coil.** { *; }
-keep interface coil.** { *; }

#===============================================================================
# WORKMANAGER
#===============================================================================

-keep class * extends androidx.work.Worker { *; }
-keep class * extends androidx.work.ListenableWorker { *; }
-keep class androidx.work.WorkerParameters { *; }

# Keep HiltWorker classes
-keep @androidx.hilt.work.HiltWorker class * { *; }
-keep class com.spiritatlas.data.worker.** { *; }

#===============================================================================
# ENCRYPTED SHARED PREFERENCES
#===============================================================================

-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

#===============================================================================
# DOMAIN MODELS (Keep for serialization/reflection)
#===============================================================================

-keep class com.spiritatlas.domain.model.** { *; }
-keep class com.spiritatlas.domain.ai.** { *; }
-keep class com.spiritatlas.domain.repository.** { *; }

# Keep enums
-keepclassmembers enum com.spiritatlas.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#===============================================================================
# CORE CALCULATION MODULES
#===============================================================================

# Keep numerology calculators
-keep class com.spiritatlas.core.numerology.** { *; }

# Keep astrology calculators
-keep class com.spiritatlas.core.astro.** { *; }

# Keep human design calculators
-keep class com.spiritatlas.core.humandesign.** { *; }

# Keep ayurveda calculators
-keep class com.spiritatlas.core.ayurveda.** { *; }

#===============================================================================
# AI PROVIDERS (Keep API models)
#===============================================================================

-keep class com.spiritatlas.data.ai.** { *; }

#===============================================================================
# NAVIGATION
#===============================================================================

-keep class * extends androidx.navigation.Navigator { *; }
-keepnames class * extends android.os.Parcelable

#===============================================================================
# SECURITY - Additional obfuscation for sensitive classes
#===============================================================================

# Obfuscate but keep functionality for security classes
-keep,allowobfuscation class com.spiritatlas.data.security.** { *; }
-keep,allowobfuscation class com.spiritatlas.data.consent.** { *; }

#===============================================================================
# REMOVE LOGGING IN RELEASE
#===============================================================================

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

# Remove Timber logging in release (if used)
-assumenosideeffects class timber.log.Timber* {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
}

#===============================================================================
# OPTIMIZATION FLAGS
#===============================================================================

-optimizationpasses 5
-allowaccessmodification
-repackageclasses ''
