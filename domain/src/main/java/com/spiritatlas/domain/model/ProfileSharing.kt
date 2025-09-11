package com.spiritatlas.domain.model

import java.time.LocalDateTime

/**
 * Shareable profile data that can be exported/imported between users
 * Similar to Co-Star sharing but with spiritual insights
 */
data class ShareableProfile(
    val profileName: String,
    val basicInfo: ShareableBasicInfo,
    val enrichmentSummary: String?, // Condensed version of enrichment for sharing
    val compatibilityReadiness: Boolean, // If this profile has enough data for compatibility
    val sharedAt: LocalDateTime,
    val sharedBy: String?, // Optional identifier of who shared it
    val profileVersion: String = "1.0" // For future compatibility
)

data class ShareableBasicInfo(
    val firstName: String?,
    val birthDateTime: LocalDateTime?,
    val birthPlace: BirthPlace?,
    val gender: Gender?,
    val loveLanguage: LoveLanguage?,
    val personalityType: PersonalityType?,
    val sexualEnergy: SexualEnergy?,
    val communicationStyle: CommunicationStyle?
)

/**
 * Result of importing a shared profile
 */
data class ProfileImportResult(
    val success: Boolean,
    val importedProfile: UserProfile?,
    val errorMessage: String?,
    val isCompatibilityReady: Boolean = false
)

/**
 * Profile library entry for organizing and discovering profiles
 */
data class ProfileLibraryEntry(
    val id: String,
    val profileName: String,
    val displayName: String?,
    val thumbnailEmoji: String, // Emoji representing this profile
    val completionLevel: AccuracyLevel,
    val hasEnrichment: Boolean,
    val isOwn: Boolean, // True if this is user's own profile
    val isImported: Boolean, // True if imported from sharing
    val lastUpdated: LocalDateTime,
    val tags: List<String> = emptyList() // For categorization
)

/**
 * Compatibility report that can be shared
 */
data class ShareableCompatibilityReport(
    val partner1Name: String,
    val partner2Name: String,
    val overallScore: Double,
    val keyStrengths: List<String>,
    val keyChallenges: List<String>,
    val reportSummary: String,
    val generatedAt: LocalDateTime,
    val shareableImageUrl: String? = null // For social sharing
)
