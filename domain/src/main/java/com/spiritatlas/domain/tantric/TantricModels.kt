package com.spiritatlas.domain.tantric

/**
 * Core tantric content domain models
 */

data class TantricContent(
    val id: String,
    val title: String,
    val description: String,
    val contentType: TantricContentType,
    val tags: List<String> = emptyList(),
    val benefits: List<String> = emptyList(),
    val instructions: List<String> = emptyList(),
    val practiceIntensity: Int = 5, // 1-10 scale
    val rating: Int = 0, // 1-5 stars
    val visualContent: List<VisualContent> = emptyList(),
    val durationMinutes: Int = 30,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class TantricContentType {
    TANTRIC_PRACTICES,
    KAMA_SUTRA,
    ROBERT_GREENE,
    COMPATIBILITY
}

data class VisualContent(
    val id: String,
    val url: String,
    val type: VisualContentType,
    val description: String,
    val thumbnailUrl: String? = null
)

enum class VisualContentType {
    IMAGE,
    VIDEO,
    ANIMATION,
    ILLUSTRATION
}

// Profile models that might be referenced
data class NumerologyProfile(
    val lifePathNumber: Int,
    val expressionNumber: Int,
    val soulUrgeNumber: Int,
    val personalityNumber: Int,
    val birthDayNumber: Int
)

data class AstrologicalProfile(
    val sunSign: String,
    val moonSign: String,
    val risingSign: String,
    val venusSign: String,
    val marsSign: String
)

data class ChakraProfile(
    val chakraScores: Map<String, Float>, // chakra name to score (0.0-1.0)
    val dominantChakras: List<String>,
    val blockedChakras: List<String>
)

enum class AttachmentStyle {
    SECURE,
    ANXIOUS_PREOCCUPIED,
    DISMISSIVE_AVOIDANT,
    FEARFUL_AVOIDANT
}
