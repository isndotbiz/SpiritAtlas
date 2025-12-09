package com.spiritatlas.domain.ai

/**
 * AI-specific models for compatibility analysis
 * These models represent AI-generated insights that complement calculated scores
 */

/**
 * AI-enhanced compatibility insights across multiple dimensions
 */
data class AiCompatibilityInsights(
    val numerologyInsight: AiDimensionInsight? = null,
    val astrologyInsight: AiDimensionInsight? = null,
    val tantricInsight: AiDimensionInsight? = null,
    val emotionalInsight: AiDimensionInsight? = null,
    val communicationInsight: AiDimensionInsight? = null,
    val karmicInsight: AiDimensionInsight? = null,
    val overallSummary: String? = null,
    val generatedAt: java.time.LocalDateTime = java.time.LocalDateTime.now(),
    val provider: String? = null // Which AI provider generated this
)

/**
 * AI insight for a specific compatibility dimension
 */
data class AiDimensionInsight(
    val dimension: String, // "numerology", "astrology", etc.
    val analysis: String, // AI-generated analysis text
    val strengthScore: Double? = null, // AI's assessment of strength (0-100)
    val keyPoints: List<String> = emptyList(), // Bullet points of key insights
    val warnings: List<String> = emptyList(), // Potential challenges
    val recommendations: List<String> = emptyList(), // Specific advice
    val confidence: Float = 0.0f // AI confidence in this analysis (0-1)
)

/**
 * Context for AI compatibility analysis
 * Provides all necessary profile data for comprehensive AI insights
 */
data class AiCompatibilityContext(
    val profileA: ProfileSummary,
    val profileB: ProfileSummary,
    val calculatedScores: ScoreSummary,
    val focusDimensions: List<String> = listOf("all") // Which dimensions to analyze
)

/**
 * Summarized profile data for AI context
 * Reduces token usage while preserving essential information
 */
data class ProfileSummary(
    val name: String?,
    val birthDate: String?, // ISO format
    val birthTime: String?, // HH:mm format
    val birthPlace: String?, // "City, Country"
    val gender: String?,

    // Numerology essentials
    val lifePathNumber: Int? = null,
    val expressionNumber: Int? = null,
    val soulUrgeNumber: Int? = null,

    // Astrology essentials
    val sunSign: String? = null,
    val moonSign: String? = null,
    val risingSign: String? = null,

    // Relationship attributes
    val loveLanguage: String? = null,
    val communicationStyle: String? = null,
    val attachmentStyle: String? = null,
    val sexualEnergy: String? = null
)

/**
 * Summary of calculated compatibility scores
 * Used to inform AI analysis
 */
data class ScoreSummary(
    val numerology: Double,
    val astrology: Double,
    val tantric: Double,
    val emotional: Double,
    val communication: Double,
    val energetic: Double,
    val overall: Double
)

/**
 * Request for AI compatibility analysis
 */
data class AiCompatibilityRequest(
    val context: AiCompatibilityContext,
    val analysisType: AnalysisType = AnalysisType.COMPREHENSIVE
)

/**
 * Type of AI analysis to perform
 */
enum class AnalysisType {
    QUICK, // Fast overview, minimal tokens
    STANDARD, // Balanced analysis
    COMPREHENSIVE // Deep, multi-dimensional analysis
}
