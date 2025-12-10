# SpiritAtlas ProGuard Rules - MAXIMUM OPTIMIZATION
# Optimized for APK size reduction while maintaining functionality
# Last updated: 2025-12-10

#===============================================================================
# AGGRESSIVE OPTIMIZATION SETTINGS
#===============================================================================

# Enable maximum optimization passes for code shrinking
-optimizationpasses 7
-allowaccessmodification
-repackageclasses ''
-overloadaggressively
-mergeinterfacesaggressively

# Enable all optimizations except those that break functionality
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

#===============================================================================
# GENERAL SETTINGS
#===============================================================================

# Preserve line numbers for debugging crash reports (minimal impact)
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve only essential annotations (reduced from *Annotation*)
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations,AnnotationDefault
-keepattributes Signature
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
# HILT / DAGGER - OPTIMIZED FOR SIZE
#===============================================================================

-dontwarn dagger.hilt.**
-dontwarn javax.inject.**

# Keep only essential Hilt infrastructure (allow obfuscation where safe)
-keep,allowobfuscation,allowshrinking class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }

# Keep Hilt generated classes (minimal set)
-keep class **_HiltModules { *; }
-keep class **_HiltComponents { *; }
-keep,allowshrinking class **_Factory { *; }
-keep,allowshrinking class **_MembersInjector { *; }

# Keep @Inject annotated constructors (required for DI)
-keepclasseswithmembers,allowshrinking class * {
    @javax.inject.Inject <init>(...);
}

# Keep @HiltViewModel annotated classes (required by lifecycle)
-keep,allowobfuscation @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

# Keep Hilt entry points
-keep,allowobfuscation @dagger.hilt.InstallIn class *
-keep,allowobfuscation @dagger.hilt.android.HiltAndroidApp class *

#===============================================================================
# RETROFIT / OKHTTP / MOSHI - OPTIMIZED
#===============================================================================

# Retrofit - minimal keep rules
-dontwarn retrofit2.**
-keep,allowobfuscation,allowshrinking class retrofit2.** { *; }
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# OkHttp - minimal keep rules
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
-keep,allowobfuscation class okhttp3.** { *; }
-keep,allowobfuscation interface okhttp3.** { *; }
-keep,allowobfuscation class okio.** { *; }

# Moshi - keep only what's needed for JSON serialization
-dontwarn com.squareup.moshi.**
-keep,allowobfuscation,allowshrinking class com.squareup.moshi.** { *; }
-keepclassmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier @interface *

# Keep Moshi adapters and API models (required for serialization)
-keep,allowobfuscation class **JsonAdapter { *; }
-keep,allowobfuscation class com.spiritatlas.data.api.** { *; }
-keep,allowobfuscation class com.spiritatlas.data.model.** { *; }

#===============================================================================
# ROOM DATABASE - OPTIMIZED
#===============================================================================

-dontwarn androidx.room.**

# Keep Room database classes (allow obfuscation of names, not structure)
-keep,allowobfuscation class * extends androidx.room.RoomDatabase { *; }
-keep,allowobfuscation @androidx.room.Entity class * { *; }
-keep,allowobfuscation @androidx.room.Dao class * { *; }

# Keep Room annotations and methods
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Keep Room generated implementations
-keep,allowobfuscation class * extends androidx.room.RoomDatabase { *; }
-keep,allowobfuscation class **_Impl { *; }

# Keep app-specific Room entities (allow obfuscation)
-keep,allowobfuscation class com.spiritatlas.data.db.** { *; }
-keep,allowobfuscation class com.spiritatlas.data.entity.** { *; }

#===============================================================================
# JETPACK COMPOSE - HIGHLY OPTIMIZED
#===============================================================================

-dontwarn androidx.compose.**

# Compose runtime optimization - allow aggressive shrinking
-keep,allowobfuscation,allowshrinking class androidx.compose.** { *; }

# Keep Composable functions (allow name obfuscation, preserve structure)
-keepclassmembers,allowobfuscation class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Keep Stable/Immutable classes (critical for recomposition optimization)
-keep,allowobfuscation @androidx.compose.runtime.Stable class * { *; }
-keep,allowobfuscation @androidx.compose.runtime.Immutable class * { *; }

# Remove Compose debug metadata in release
-assumenosideeffects class androidx.compose.runtime.ComposerKt {
    boolean isTraceInProgress();
    void traceEventStart(int, int, int, java.lang.String);
    void traceEventEnd();
}

#===============================================================================
# COIL (Image Loading) - OPTIMIZED
#===============================================================================

-dontwarn coil.**
-keep,allowobfuscation,allowshrinking class coil.** { *; }
-keep,allowobfuscation interface coil.** { *; }

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
# DOMAIN MODELS - OPTIMIZED (Keep for serialization/reflection)
#===============================================================================

# Allow obfuscation of domain models (keep structure for serialization)
-keep,allowobfuscation class com.spiritatlas.domain.model.** { *; }
-keep,allowobfuscation class com.spiritatlas.domain.ai.** { *; }
-keep,allowobfuscation interface com.spiritatlas.domain.repository.** { *; }

# Keep enum methods (required for Kotlin enum functionality)
-keepclassmembers enum com.spiritatlas.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public *;
}

#===============================================================================
# CORE CALCULATION MODULES - CRITICAL SPIRITUAL LOGIC
#===============================================================================

# Keep spiritual calculation classes - THESE ARE CRITICAL FOR APP FUNCTIONALITY
# Allow obfuscation but preserve all calculation logic

# Numerology calculators (Chaldean and Pythagorean systems)
-keep,allowobfuscation class com.spiritatlas.core.numerology.** { *; }
-keepclassmembers class com.spiritatlas.core.numerology.** {
    public <methods>;
    public <fields>;
}

# Astrology calculators (zodiac, planets, houses, aspects)
-keep,allowobfuscation class com.spiritatlas.core.astro.** { *; }
-keepclassmembers class com.spiritatlas.core.astro.** {
    public <methods>;
    public <fields>;
}

# Human Design calculators (gates, channels, centers)
-keep,allowobfuscation class com.spiritatlas.core.humandesign.** { *; }
-keepclassmembers class com.spiritatlas.core.humandesign.** {
    public <methods>;
    public <fields>;
}

# Ayurveda calculators (dosha analysis)
-keep,allowobfuscation class com.spiritatlas.core.ayurveda.** { *; }
-keepclassmembers class com.spiritatlas.core.ayurveda.** {
    public <methods>;
    public <fields>;
}

# Keep calculation result data classes
-keep,allowobfuscation class com.spiritatlas.domain.model.NumerologyProfile { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.AstroProfile { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.HumanDesignProfile { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.AyurvedaProfile { *; }

#===============================================================================
# AI PROVIDERS - OPTIMIZED (Keep API models)
#===============================================================================

# Keep AI provider implementations (allow obfuscation)
-keep,allowobfuscation class com.spiritatlas.data.ai.** { *; }
-keepclassmembers class com.spiritatlas.data.ai.** {
    public <methods>;
}

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
# GOOGLE CRYPTO TINK & ERROR PRONE
#===============================================================================

# Keep Tink crypto library - optimized rules
# Only keep public API and classes used via reflection
-keep class com.google.crypto.tink.Aead { *; }
-keep class com.google.crypto.tink.KeysetHandle { *; }
-keep class com.google.crypto.tink.KeysetManager { *; }
-keep class com.google.crypto.tink.KeyTemplate { *; }
-keep class com.google.crypto.tink.Registry { *; }
-keep class com.google.crypto.tink.aead.** { *; }
-keep class com.google.crypto.tink.config.TinkConfig { *; }

# Suppress warnings for optional dependencies
-dontwarn com.google.crypto.tink.**
-dontwarn com.google.errorprone.annotations.**
-dontwarn javax.annotation.**
-dontwarn org.checkerframework.**

#===============================================================================
# ADDITIONAL SIZE OPTIMIZATIONS
#===============================================================================

# Remove unused resources at compile time
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkNotNull(java.lang.Object);
    public static void checkNotNull(java.lang.Object, java.lang.String);
    public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    public static void checkNotNullParameter(java.lang.Object, java.lang.String);
    public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    public static void throwUninitializedPropertyAccessException(java.lang.String);
}

# Remove BuildConfig debug fields
-assumenosideeffects class **.BuildConfig {
    public static boolean DEBUG;
    public static final boolean DEBUG;
}

# Aggressive resource shrinking
-keep,allowobfuscation,allowshrinking @androidx.annotation.Keep class *

#===============================================================================
# R8 FULL MODE OPTIMIZATIONS
#===============================================================================

# Enable R8 full mode for maximum optimization
# This is enabled by default in AGP 8.0+, but we specify for clarity
-allowaccessmodification
-repackageclasses ''

# Additional R8 optimizations
-assumevalues class android.os.Build$VERSION {
    int SDK_INT return 26..35;
}

# Optimize away unused feature flags
-assumenosideeffects class com.spiritatlas.** {
    boolean isDebugMode() return false;
    boolean isLoggingEnabled() return false;
}
