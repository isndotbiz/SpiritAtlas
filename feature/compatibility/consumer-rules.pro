# Consumer ProGuard Rules for feature:compatibility
# These rules are applied to consumers of this library module
# Last updated: 2025-12-10

#===============================================================================
# DOMAIN MODELS - Required for Compatibility Analysis
#===============================================================================

# Keep all domain model classes used in compatibility analysis
# Allow obfuscation but preserve structure for serialization and Compose state
-keep,allowobfuscation class com.spiritatlas.domain.model.UserProfile { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.BirthPlace { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.UserPreferences { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.ProfileCompletion { *; }

# Keep compatibility-specific domain models
-keep,allowobfuscation class com.spiritatlas.domain.model.CompatibilityReport { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.CompatibilityScores { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.RelationshipInsight { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.CompatibilityStrength { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.CompatibilityChallenge { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.CompatibilityRecommendation { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.TantricCompatibility { *; }
-keep,allowobfuscation class com.spiritatlas.domain.model.ProfilePair { *; }

# Keep AI insights models
-keep,allowobfuscation class com.spiritatlas.domain.ai.AiCompatibilityInsights { *; }

# Keep repository interfaces and data classes
-keep,allowobfuscation interface com.spiritatlas.domain.repository.CompatibilityRepository { *; }
-keep,allowobfuscation interface com.spiritatlas.domain.repository.UserRepository { *; }
-keep,allowobfuscation class com.spiritatlas.domain.repository.CompatibilityCriteria { *; }
-keep,allowobfuscation class com.spiritatlas.domain.repository.ProfileMatch { *; }
-keep,allowobfuscation class com.spiritatlas.domain.repository.CompatibilityPreview { *; }

#===============================================================================
# ENUMS - Keep all enum functionality
#===============================================================================

# Keep all enum methods (required for Kotlin enum functionality)
-keepclassmembers enum com.spiritatlas.domain.model.** {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public *;
}

# Specific compatibility enums
-keep,allowobfuscation enum com.spiritatlas.domain.model.CompatibilityLevel { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.InsightCategory { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.InsightImportance { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.CompatibilityCategory { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.ChallengeSeverity { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.RecommendationType { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.RecommendationPriority { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.AccuracyLevel { *; }

# User profile enums
-keep,allowobfuscation enum com.spiritatlas.domain.model.Gender { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.BloodType { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.Hand { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.LoveLanguage { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.PersonalityType { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.AttachmentStyle { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.SexualEnergy { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.CommunicationStyle { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.ConflictStyle { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.IntimacyStyle { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.SpiritualConnection { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.HouseSystem { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.NumerologySystem { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.TerminologyStyle { *; }
-keep,allowobfuscation enum com.spiritatlas.domain.model.InsightDetail { *; }

#===============================================================================
# JETPACK COMPOSE - Compatibility UI State Classes
#===============================================================================

# Keep Compose Stable/Immutable annotations (critical for recomposition)
-keep,allowobfuscation @androidx.compose.runtime.Stable class com.spiritatlas.feature.compatibility.** { *; }
-keep,allowobfuscation @androidx.compose.runtime.Immutable class com.spiritatlas.feature.compatibility.** { *; }

# Keep Composable functions (preserve for Compose compiler)
-keepclassmembers,allowobfuscation class com.spiritatlas.feature.compatibility.** {
    @androidx.compose.runtime.Composable <methods>;
}

#===============================================================================
# VIEWMODEL STATE
#===============================================================================

# Keep ViewModel state classes (used in StateFlow)
-keep,allowobfuscation class com.spiritatlas.feature.compatibility.CompatibilityUiState { *; }
-keep,allowobfuscation class com.spiritatlas.feature.compatibility.CompatibilityViewModel { *; }

#===============================================================================
# SERIALIZATION - JSON/Moshi Support
#===============================================================================

# Keep fields for JSON serialization (if using Moshi/Gson)
-keepclassmembers class com.spiritatlas.domain.model.** {
    <fields>;
}

# Preserve field names for reflection-based serialization
-keepclassmembers class com.spiritatlas.domain.model.** {
    !transient <fields>;
}

#===============================================================================
# JAVA TIME API (LocalDateTime)
#===============================================================================

# Keep LocalDateTime and related time classes (used extensively in models)
-keep class java.time.** { *; }
-dontwarn java.time.**

#===============================================================================
# HILT DEPENDENCIES
#===============================================================================

# Keep Hilt ViewModel classes in this module
-keep,allowobfuscation @dagger.hilt.android.lifecycle.HiltViewModel class com.spiritatlas.feature.compatibility.** { *; }

#===============================================================================
# COROUTINES & FLOW
#===============================================================================

# Keep coroutine state for StateFlow (used in ViewModel)
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

#===============================================================================
# NAVIGATION ARGUMENTS
#===============================================================================

# Keep navigation argument classes (if using type-safe navigation)
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#===============================================================================
# DEBUGGING (Remove in production)
#===============================================================================

# Preserve line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
