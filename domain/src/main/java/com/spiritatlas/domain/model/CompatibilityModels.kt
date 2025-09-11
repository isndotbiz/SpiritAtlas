package com.spiritatlas.domain.model

/**
 * Domain models for relationship compatibility analysis
 * Integrates numerology, astrology, and tantric insights
 */

data class CompatibilityReport(
    val profileA: UserProfile,
    val profileB: UserProfile,
    val overallScore: CompatibilityScores,
    val insights: List<RelationshipInsight>,
    val strengths: List<CompatibilityStrength>,
    val challenges: List<CompatibilityChallenge>,
    val recommendations: List<CompatibilityRecommendation>,
    val tantricMatches: List<TantricCompatibility>,
    val generatedAt: java.time.LocalDateTime = java.time.LocalDateTime.now()
) {
    val compatibilityLevel: CompatibilityLevel
        get() = when {
            overallScore.totalScore >= 90.0 -> CompatibilityLevel.SOULMATE
            overallScore.totalScore >= 75.0 -> CompatibilityLevel.EXCELLENT
            overallScore.totalScore >= 60.0 -> CompatibilityLevel.GOOD
            overallScore.totalScore >= 45.0 -> CompatibilityLevel.MODERATE
            overallScore.totalScore >= 30.0 -> CompatibilityLevel.CHALLENGING
            else -> CompatibilityLevel.INCOMPATIBLE
        }
}

data class CompatibilityScores(
    val numerologyScore: Double,
    val astrologyScore: Double,
    val tantricScore: Double,
    val energeticScore: Double,
    val communicationScore: Double,
    val emotionalScore: Double
) {
    val totalScore: Double
        get() = (numerologyScore + astrologyScore + tantricScore + 
                energeticScore + communicationScore + emotionalScore) / 6.0
    
    val scoreBreakdown: Map<String, Double>
        get() = mapOf(
            "Numerology" to numerologyScore,
            "Astrology" to astrologyScore,
            "Tantric" to tantricScore,
            "Energetic" to energeticScore,
            "Communication" to communicationScore,
            "Emotional" to emotionalScore
        )
}

data class RelationshipInsight(
    val title: String,
    val description: String,
    val category: InsightCategory,
    val importance: InsightImportance,
    val icon: String,
    val supportingEvidence: List<String> = emptyList()
)

data class CompatibilityStrength(
    val aspect: String,
    val description: String,
    val score: Double,
    val category: CompatibilityCategory,
    val icon: String = "✨"
)

data class CompatibilityChallenge(
    val aspect: String,
    val description: String,
    val severity: ChallengeSeverity,
    val category: CompatibilityCategory,
    val solutions: List<String> = emptyList(),
    val icon: String = "⚠️"
)

data class CompatibilityRecommendation(
    val title: String,
    val description: String,
    val actionType: RecommendationType,
    val priority: RecommendationPriority,
    val relatedTantricContent: List<String> = emptyList(),
    val icon: String
)

data class TantricCompatibility(
    val contentId: String,
    val contentType: String, // Content type as string for flexibility
    val compatibilityScore: Double,
    val reason: String,
    val recommendation: String
)

enum class CompatibilityLevel(val displayName: String, val icon: String, val color: String) {
    SOULMATE("Soulmate Connection", "💫", "#FF6B9D"),
    EXCELLENT("Excellent Match", "✨", "#4ECDC4"),
    GOOD("Good Compatibility", "🌟", "#45B7D1"),
    MODERATE("Moderate Match", "⭐", "#FFA07A"),
    CHALLENGING("Needs Work", "⚠️", "#FFD93D"),
    INCOMPATIBLE("Incompatible", "❌", "#FF6B6B")
}

enum class InsightCategory {
    SOUL_CONNECTION,
    COMMUNICATION_STYLE,
    EMOTIONAL_HARMONY,
    PHYSICAL_ATTRACTION,
    SPIRITUAL_ALIGNMENT,
    LIFE_GOALS,
    CONFLICT_RESOLUTION,
    GROWTH_POTENTIAL
}

enum class InsightImportance {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW
}

enum class CompatibilityCategory {
    NUMEROLOGICAL,
    ASTROLOGICAL,
    TANTRIC,
    ENERGETIC,
    COMMUNICATION,
    EMOTIONAL,
    PHYSICAL,
    SPIRITUAL
}

enum class ChallengeSeverity {
    MINOR,
    MODERATE,
    MAJOR,
    CRITICAL
}

enum class RecommendationType {
    COMMUNICATION_TECHNIQUE,
    TANTRIC_PRACTICE,
    SPIRITUAL_EXERCISE,
    DATE_IDEA,
    CONFLICT_RESOLUTION,
    INTIMACY_ENHANCEMENT,
    PERSONAL_GROWTH,
    RELATIONSHIP_RITUAL
}

enum class RecommendationPriority {
    IMMEDIATE,
    HIGH,
    MEDIUM,
    LOW,
    OPTIONAL
}

/**
 * Profile pair for compatibility analysis
 */
data class ProfilePair(
    val primaryProfile: UserProfile,
    val partnerProfile: UserProfile
) {
    val isValid: Boolean
        get() = primaryProfile.profileCompletion.accuracyLevel >= AccuracyLevel.BASIC &&
                partnerProfile.profileCompletion.accuracyLevel >= AccuracyLevel.BASIC
}
