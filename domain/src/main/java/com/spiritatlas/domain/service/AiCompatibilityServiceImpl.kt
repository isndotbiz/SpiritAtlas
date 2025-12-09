package com.spiritatlas.domain.service

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.*
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.CompatibilityScores
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONArray

/**
 * Implementation of AI-powered compatibility analysis service
 *
 * This service generates intelligent, multi-perspective compatibility insights
 * by sending profile data and calculated scores to an AI provider.
 */
class AiCompatibilityServiceImpl @Inject constructor(
    private val aiTextProvider: AiTextProvider
) : AiCompatibilityService {

    override suspend fun analyzeCompatibility(
        profileA: UserProfile,
        profileB: UserProfile,
        calculatedScores: CompatibilityScores,
        analysisType: AnalysisType
    ): Result<AiCompatibilityInsights> = withContext(Dispatchers.IO) {
        try {
            // Check AI availability
            if (!aiTextProvider.isAvailable()) {
                return@withContext Result.Error(
                    IllegalStateException("AI provider not available for compatibility analysis")
                )
            }

            // Build context for AI analysis
            val context = buildCompatibilityContext(profileA, profileB, calculatedScores)

            // Generate AI prompt based on analysis type
            val prompt = buildCompatibilityPrompt(context, analysisType)

            // Build enrichment context (reusing existing infrastructure)
            val enrichmentContext = EnrichmentContext(
                numerology = buildNumerologyData(profileA, profileB, calculatedScores.numerologyScore),
                astrology = buildAstrologyData(profileA, profileB, calculatedScores.astrologyScore),
                energyProfile = buildEnergyData(profileA, profileB, calculatedScores),
                personalDetails = mapOf(
                    "analysis_type" to "compatibility",
                    "prompt" to prompt
                ),
                completedFields = (profileA.profileCompletion.completedFields +
                                  profileB.profileCompletion.completedFields) / 2,
                totalFields = 36,
                accuracyLevel = profileA.profileCompletion.accuracyLevel.name
            )

            // Get AI analysis
            when (val result = aiTextProvider.generateEnrichment(enrichmentContext)) {
                is Result.Success -> {
                    // Parse AI response into structured insights
                    val insights = parseAiResponse(result.data.text, result.data.confidence)
                    Result.Success(insights)
                }
                is Result.Error -> {
                    Result.Error(result.exception)
                }
                is Result.Loading -> {
                    Result.Error(IllegalStateException("Unexpected loading state"))
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        return try {
            aiTextProvider.isAvailable()
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun analyzeDimension(
        profileA: UserProfile,
        profileB: UserProfile,
        dimension: String,
        calculatedScore: Double
    ): Result<AiDimensionInsight> = withContext(Dispatchers.IO) {
        try {
            if (!aiTextProvider.isAvailable()) {
                return@withContext Result.Error(
                    IllegalStateException("AI provider not available")
                )
            }

            val prompt = buildDimensionPrompt(profileA, profileB, dimension, calculatedScore)

            val enrichmentContext = EnrichmentContext(
                numerology = if (dimension == "numerology")
                    buildNumerologyData(profileA, profileB, calculatedScore) else emptyMap(),
                astrology = if (dimension == "astrology")
                    buildAstrologyData(profileA, profileB, calculatedScore) else emptyMap(),
                energyProfile = mapOf("dimension" to dimension, "score" to calculatedScore),
                personalDetails = mapOf("prompt" to prompt),
                completedFields = 10,
                totalFields = 36,
                accuracyLevel = "GOOD"
            )

            when (val result = aiTextProvider.generateEnrichment(enrichmentContext)) {
                is Result.Success -> {
                    val insight = parseDimensionResponse(
                        dimension,
                        result.data.text,
                        result.data.confidence
                    )
                    Result.Success(insight)
                }
                is Result.Error -> Result.Error(result.exception)
                is Result.Loading -> Result.Error(IllegalStateException("Unexpected loading state"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // Private helper methods

    private fun buildCompatibilityContext(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): AiCompatibilityContext {
        return AiCompatibilityContext(
            profileA = profileToSummary(profileA),
            profileB = profileToSummary(profileB),
            calculatedScores = ScoreSummary(
                numerology = scores.numerologyScore,
                astrology = scores.astrologyScore,
                tantric = scores.tantricScore,
                emotional = scores.emotionalScore,
                communication = scores.communicationScore,
                energetic = scores.energeticScore,
                overall = scores.totalScore
            )
        )
    }

    private fun profileToSummary(profile: UserProfile): ProfileSummary {
        return ProfileSummary(
            name = profile.name,
            birthDate = profile.birthDateTime?.toLocalDate()?.toString(),
            birthTime = profile.birthDateTime?.toLocalTime()?.toString(),
            birthPlace = profile.birthPlace?.let { "${it.city}, ${it.country}" },
            gender = profile.gender?.name,
            lifePathNumber = null, // Would calculate from numerology
            expressionNumber = null,
            soulUrgeNumber = null,
            sunSign = null, // Would calculate from astrology
            moonSign = null,
            risingSign = null,
            loveLanguage = profile.loveLanguage?.name,
            communicationStyle = profile.communicationStyle?.name,
            attachmentStyle = profile.attachmentStyle?.name,
            sexualEnergy = profile.sexualEnergy?.name
        )
    }

    private fun buildCompatibilityPrompt(
        context: AiCompatibilityContext,
        analysisType: AnalysisType
    ): String {
        val depth = when (analysisType) {
            AnalysisType.QUICK -> "brief overview"
            AnalysisType.STANDARD -> "balanced analysis"
            AnalysisType.COMPREHENSIVE -> "deep, comprehensive analysis"
        }

        return """
You are an expert in spiritual compatibility analysis, specializing in numerology, astrology, tantric energy, and relationship dynamics.

Analyze the compatibility between these two individuals and provide a $depth:

**Profile A:**
- Name: ${context.profileA.name ?: "Unknown"}
- Birth: ${context.profileA.birthDate ?: "Unknown"} at ${context.profileA.birthTime ?: "Unknown"}
- Location: ${context.profileA.birthPlace ?: "Unknown"}
- Gender: ${context.profileA.gender ?: "Unknown"}
- Love Language: ${context.profileA.loveLanguage ?: "Unknown"}
- Communication: ${context.profileA.communicationStyle ?: "Unknown"}
- Attachment: ${context.profileA.attachmentStyle ?: "Unknown"}
- Sexual Energy: ${context.profileA.sexualEnergy ?: "Unknown"}

**Profile B:**
- Name: ${context.profileB.name ?: "Unknown"}
- Birth: ${context.profileB.birthDate ?: "Unknown"} at ${context.profileB.birthTime ?: "Unknown"}
- Location: ${context.profileB.birthPlace ?: "Unknown"}
- Gender: ${context.profileB.gender ?: "Unknown"}
- Love Language: ${context.profileB.loveLanguage ?: "Unknown"}
- Communication: ${context.profileB.communicationStyle ?: "Unknown"}
- Attachment: ${context.profileB.attachmentStyle ?: "Unknown"}
- Sexual Energy: ${context.profileB.sexualEnergy ?: "Unknown"}

**Calculated Compatibility Scores:**
- Numerology: ${context.calculatedScores.numerology}/100
- Astrology: ${context.calculatedScores.astrology}/100
- Tantric: ${context.calculatedScores.tantric}/100
- Emotional: ${context.calculatedScores.emotional}/100
- Communication: ${context.calculatedScores.communication}/100
- Energetic: ${context.calculatedScores.energetic}/100
- Overall: ${context.calculatedScores.overall}/100

Please provide your analysis in the following JSON format:

{
  "numerology": {
    "analysis": "Deep analysis of numerological compatibility...",
    "keyPoints": ["Point 1", "Point 2", "Point 3"],
    "warnings": ["Potential challenge 1"],
    "recommendations": ["Advice 1", "Advice 2"]
  },
  "astrology": {
    "analysis": "Astrological synastry insights...",
    "keyPoints": ["Point 1", "Point 2"],
    "warnings": [],
    "recommendations": ["Advice 1"]
  },
  "tantric": {
    "analysis": "Sexual and tantric energy dynamics...",
    "keyPoints": ["Point 1", "Point 2"],
    "warnings": [],
    "recommendations": ["Advice 1", "Advice 2"]
  },
  "emotional": {
    "analysis": "Emotional compatibility patterns...",
    "keyPoints": ["Point 1", "Point 2"],
    "warnings": ["Challenge 1"],
    "recommendations": ["Advice 1"]
  },
  "communication": {
    "analysis": "Communication style dynamics...",
    "keyPoints": ["Point 1", "Point 2"],
    "warnings": [],
    "recommendations": ["Advice 1"]
  },
  "karmic": {
    "analysis": "Karmic connections and soul contract insights...",
    "keyPoints": ["Point 1", "Point 2"],
    "warnings": [],
    "recommendations": ["Advice 1"]
  },
  "summary": "Overall relationship compatibility summary and guidance..."
}

Focus on practical insights, spiritual growth opportunities, and actionable recommendations.
""".trimIndent()
    }

    private fun buildDimensionPrompt(
        profileA: UserProfile,
        profileB: UserProfile,
        dimension: String,
        score: Double
    ): String {
        return """
Analyze the $dimension compatibility between these two individuals (calculated score: $score/100):

Profile A: ${profileA.name ?: "Unknown"} born ${profileA.birthDateTime ?: "Unknown"}
Profile B: ${profileB.name ?: "Unknown"} born ${profileB.birthDateTime ?: "Unknown"}

Provide deep insights about their $dimension compatibility, including strengths, challenges, and recommendations.
""".trimIndent()
    }

    private fun buildNumerologyData(
        profileA: UserProfile,
        profileB: UserProfile,
        score: Double
    ): Map<String, Any> {
        return mapOf(
            "profile_a_name" to (profileA.name ?: ""),
            "profile_b_name" to (profileB.name ?: ""),
            "calculated_score" to score,
            "dimension" to "numerology"
        )
    }

    private fun buildAstrologyData(
        profileA: UserProfile,
        profileB: UserProfile,
        score: Double
    ): Map<String, Any> {
        return mapOf(
            "profile_a_birth" to (profileA.birthDateTime?.toString() ?: ""),
            "profile_b_birth" to (profileB.birthDateTime?.toString() ?: ""),
            "calculated_score" to score,
            "dimension" to "astrology"
        )
    }

    private fun buildEnergyData(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): Map<String, Any> {
        return mapOf(
            "tantric_score" to scores.tantricScore,
            "energetic_score" to scores.energeticScore,
            "profile_a_energy" to (profileA.sexualEnergy?.name ?: "Unknown"),
            "profile_b_energy" to (profileB.sexualEnergy?.name ?: "Unknown")
        )
    }

    private fun parseAiResponse(text: String, confidence: Float): AiCompatibilityInsights {
        return try {
            // Try to parse as JSON first
            val json = extractJson(text)
            if (json != null) {
                parseJsonResponse(json, confidence)
            } else {
                // Fallback: parse as free-form text
                parseTextResponse(text, confidence)
            }
        } catch (e: Exception) {
            // Graceful fallback
            AiCompatibilityInsights(
                overallSummary = text,
                generatedAt = java.time.LocalDateTime.now(),
                provider = "ai"
            )
        }
    }

    private fun extractJson(text: String): JSONObject? {
        return try {
            // Look for JSON object in the text
            val start = text.indexOf('{')
            val end = text.lastIndexOf('}')
            if (start >= 0 && end > start) {
                JSONObject(text.substring(start, end + 1))
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseJsonResponse(json: JSONObject, confidence: Float): AiCompatibilityInsights {
        return AiCompatibilityInsights(
            numerologyInsight = parseDimensionJson(json, "numerology", confidence),
            astrologyInsight = parseDimensionJson(json, "astrology", confidence),
            tantricInsight = parseDimensionJson(json, "tantric", confidence),
            emotionalInsight = parseDimensionJson(json, "emotional", confidence),
            communicationInsight = parseDimensionJson(json, "communication", confidence),
            karmicInsight = parseDimensionJson(json, "karmic", confidence),
            overallSummary = json.optString("summary", ""),
            generatedAt = java.time.LocalDateTime.now(),
            provider = "ai"
        )
    }

    private fun parseDimensionJson(
        json: JSONObject,
        dimension: String,
        confidence: Float
    ): AiDimensionInsight? {
        return try {
            val dimJson = json.optJSONObject(dimension) ?: return null
            AiDimensionInsight(
                dimension = dimension,
                analysis = dimJson.optString("analysis", ""),
                strengthScore = null,
                keyPoints = jsonArrayToList(dimJson.optJSONArray("keyPoints")),
                warnings = jsonArrayToList(dimJson.optJSONArray("warnings")),
                recommendations = jsonArrayToList(dimJson.optJSONArray("recommendations")),
                confidence = confidence
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun jsonArrayToList(array: JSONArray?): List<String> {
        if (array == null) return emptyList()
        return (0 until array.length()).mapNotNull {
            array.optString(it)?.takeIf { str -> str.isNotBlank() }
        }
    }

    private fun parseTextResponse(text: String, confidence: Float): AiCompatibilityInsights {
        // Simple text-based parsing
        return AiCompatibilityInsights(
            overallSummary = text.take(1000), // Limit to prevent excessive text
            generatedAt = java.time.LocalDateTime.now(),
            provider = "ai"
        )
    }

    private fun parseDimensionResponse(
        dimension: String,
        text: String,
        confidence: Float
    ): AiDimensionInsight {
        return AiDimensionInsight(
            dimension = dimension,
            analysis = text,
            strengthScore = null,
            keyPoints = emptyList(),
            warnings = emptyList(),
            recommendations = emptyList(),
            confidence = confidence
        )
    }
}
