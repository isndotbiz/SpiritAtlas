package com.spiritatlas.domain.service

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.*
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.CompatibilityScores

/**
 * Service for AI-powered compatibility analysis
 *
 * This service enhances calculated compatibility scores with AI-generated insights
 * across multiple spiritual dimensions:
 * - Numerology compatibility analysis
 * - Astrological synastry insights
 * - Tantric/sexual energy dynamics
 * - Emotional compatibility patterns
 * - Communication style analysis
 * - Karmic connections and past life influences
 *
 * The AI analysis is optional and complements (not replaces) the calculated scores.
 * If AI is unavailable, the system gracefully falls back to calculation-only mode.
 */
interface AiCompatibilityService {

    /**
     * Generate comprehensive AI-powered compatibility insights
     *
     * @param profileA First person's profile
     * @param profileB Second person's profile
     * @param calculatedScores Pre-calculated compatibility scores
     * @param analysisType Type of analysis (quick/standard/comprehensive)
     * @return AI-generated insights or error if AI unavailable
     */
    suspend fun analyzeCompatibility(
        profileA: UserProfile,
        profileB: UserProfile,
        calculatedScores: CompatibilityScores,
        analysisType: AnalysisType = AnalysisType.STANDARD
    ): Result<AiCompatibilityInsights>

    /**
     * Check if AI compatibility analysis is available
     * @return true if AI provider is configured and ready
     */
    suspend fun isAvailable(): Boolean

    /**
     * Generate AI insights for a specific compatibility dimension
     * Useful for drill-down analysis on particular aspects
     *
     * @param profileA First person's profile
     * @param profileB Second person's profile
     * @param dimension Specific dimension to analyze (e.g., "numerology", "astrology")
     * @param calculatedScore Pre-calculated score for this dimension
     * @return AI insight for the specific dimension
     */
    suspend fun analyzeDimension(
        profileA: UserProfile,
        profileB: UserProfile,
        dimension: String,
        calculatedScore: Double
    ): Result<AiDimensionInsight>
}
