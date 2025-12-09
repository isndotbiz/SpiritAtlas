package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Anthropic Claude provider - Excellent reasoning and analysis
 *
 * User must provide their own API key (via settings or OAuth)
 *
 * Supported models:
 * - claude-sonnet-4-5 (best balance, reasoning)
 * - claude-opus-4-5 (highest quality)
 * - claude-haiku-4 (fast, affordable)
 *
 * Lean implementation using OkHttp (no Anthropic SDK bloat)
 */
@Singleton
class ClaudeProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : AiTextProvider {

    private var userApiKey: String? = null
    private val baseUrl = "https://api.anthropic.com/v1"
    private val apiVersion = "2023-06-01"

    fun setApiKey(apiKey: String) {
        userApiKey = apiKey
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        val apiKey = userApiKey ?: return@withContext Result.Error(IllegalStateException("Claude API key not configured"))

        try {
            val prompt = buildEnrichmentPrompt(context)
            val model = selectModel(context.completedFields)

            val requestBody = JSONObject().apply {
                put("model", model)
                put("max_tokens", 4000)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", prompt)
                    })
                })
                put("system", "You are an expert spiritual advisor combining numerology, astrology, ayurveda, and human design. Provide insightful, personalized analysis.")
                put("temperature", 0.7)
            }.toString()

            val request = Request.Builder()
                .url("$baseUrl/messages")
                .addHeader("x-api-key", apiKey)
                .addHeader("anthropic-version", apiVersion)
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.Error(Exception("Claude API error: ${response.code} - ${response.message}"))
            }

            val responseBody = response.body?.string() ?: return@withContext Result.Error(Exception("Empty response"))
            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse
                .getJSONArray("content")
                .getJSONObject(0)
                .getString("text")

            Result.Success(EnrichmentResult(
                text = content,
                confidence = 0.95f // Claude has excellent reliability and reasoning
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        return !userApiKey.isNullOrBlank()
    }

    private fun selectModel(completedFields: Int): String {
        return when {
            completedFields >= 24 -> "claude-opus-4-5"  // Best quality
            completedFields >= 10 -> "claude-sonnet-4-5"  // Best balance
            else -> "claude-haiku-4"  // Fast and affordable
        }
    }

    private fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        return """
            I need you to analyze this spiritual profile and provide deeply personalized insights.

            **Profile Completion:** ${context.completedFields}/${context.totalFields} fields (${context.accuracyLevel} accuracy)

            **Numerology Data:**
            ${formatMapDetailed(context.numerology)}

            **Astrology Data:**
            ${formatMapDetailed(context.astrology)}

            **Energy Profile:**
            ${formatMapDetailed(context.energyProfile)}

            **Personal Details:**
            ${formatMapDetailed(context.personalDetails)}

            Please provide a comprehensive spiritual analysis covering:

            1. **Life Purpose & Soul Mission** - What is their deeper calling?
            2. **Natural Strengths & Gifts** - What innate talents do they possess?
            3. **Growth Challenges** - What obstacles are opportunities for evolution?
            4. **Relationship Dynamics** - How do they connect with others?
            5. **Career & Vocation** - What work aligns with their soul?
            6. **Spiritual Guidance** - Specific practices and recommendations

            Format your response in markdown with clear sections and bullet points.
            Be specific, insightful, and reference the actual data provided.
            Offer actionable guidance that empowers them.
        """.trimIndent()
    }

    private fun formatMapDetailed(map: Map<String, Any>): String {
        return if (map.isEmpty()) {
            "No data provided"
        } else {
            map.entries.joinToString("\n") { (key, value) ->
                "• $key: $value"
            }
        }
    }

    companion object {
        const val PROVIDER_NAME = "Claude (Anthropic)"

        // Pricing (user's account)
        const val OPUS_INPUT_PER_1M = 15.00  // $15/1M tokens
        const val OPUS_OUTPUT_PER_1M = 75.00
        const val SONNET_INPUT_PER_1M = 3.00  // $3/1M tokens
        const val SONNET_OUTPUT_PER_1M = 15.00
        const val HAIKU_INPUT_PER_1M = 0.80  // $0.80/1M tokens
        const val HAIKU_OUTPUT_PER_1M = 4.00
    }
}
