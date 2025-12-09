package com.spiritatlas.data.ai

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.spiritatlas.core.common.Result
import com.spiritatlas.data.BuildConfig
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import javax.inject.Inject

/**
 * Google Gemini AI provider using Gemini 2.5 Flash model
 *
 * Free tier limits:
 * - 15 requests per minute (RPM)
 * - 1 million tokens per minute (TPM)
 * - 1,500 requests per day (RPD)
 *
 * Features:
 * - Excellent analytical reasoning with thinking mode
 * - Native JSON structured output
 * - 1M token context window
 * - Fast inference (~2-3 seconds)
 */
class GeminiProvider @Inject constructor() : AiTextProvider {

    private val apiKey = BuildConfig.GEMINI_API_KEY

    private val model by lazy {
        GenerativeModel(
            modelName = "gemini-2.0-flash-exp", // Using experimental for higher free limits
            apiKey = apiKey,
            generationConfig = GenerationConfig.builder().apply {
                temperature = 0.7f
                topP = 0.95f
                maxOutputTokens = 4000
            }.build()
        )
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        if (!isAvailable()) {
            return Result.Error(IllegalStateException("Gemini API key not configured"))
        }

        try {
            val prompt = buildEnrichmentPrompt(context)
            val response = model.generateContent(prompt)
            val text = response.text ?: return Result.Error(IllegalStateException("Empty response from Gemini"))

            return Result.Success(EnrichmentResult(
                text = text,
                confidence = 0.92f // Gemini has high reliability for analytical tasks
            ))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        return apiKey.isNotBlank() && apiKey != "null"
    }

    private fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        val completionLevel = when {
            context.completedFields >= 24 -> "Master"
            context.completedFields >= 20 -> "Comprehensive"
            context.completedFields >= 10 -> "Detailed"
            context.completedFields >= 3 -> "Basic"
            else -> "Minimal"
        }

        return """
            You are an expert spiritual advisor combining numerology, astrology, ayurveda, and human design.

            Analyze this spiritual profile and provide personalized insights.

            **Profile Completion:** $completionLevel ($context.completedFields/${context.totalFields} fields)
            **Accuracy Level:** ${context.accuracyLevel}

            **Numerology Data:**
            ${formatMap(context.numerology)}

            **Astrology Data:**
            ${formatMap(context.astrology)}

            **Energy Profile:**
            ${formatMap(context.energyProfile)}

            **Personal Details:**
            ${formatMap(context.personalDetails)}

            Provide a comprehensive spiritual analysis covering:
            1. **Life Purpose & Path** - What is their soul's mission?
            2. **Strengths & Gifts** - What natural talents do they possess?
            3. **Challenges & Growth** - What obstacles help them evolve?
            4. **Relationships** - How do they connect with others?
            5. **Career & Vocation** - What work aligns with their energy?
            6. **Spiritual Guidance** - Specific practices and recommendations

            Format the response in markdown with clear sections and bullet points.
            Be specific, insightful, and encouraging. Reference the actual data provided.
        """.trimIndent()
    }

    private fun formatMap(map: Map<String, Any>): String {
        return map.entries.joinToString("\n") { (key, value) ->
            "- $key: $value"
        }.ifBlank { "No data provided" }
    }
}
