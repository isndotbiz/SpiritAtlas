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
 * OpenAI provider - Industry standard models
 *
 * User must provide their own API key (via settings or OAuth)
 *
 * Supported models:
 * - gpt-4o-mini (fast, affordable)
 * - gpt-4o (advanced reasoning)
 * - gpt-3.5-turbo (legacy)
 *
 * Lean implementation using OkHttp (no OpenAI SDK bloat)
 */
@Singleton
class OpenAIProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : AiTextProvider {

    private var userApiKey: String? = null
    private val baseUrl = "https://api.openai.com/v1"

    fun setApiKey(apiKey: String) {
        userApiKey = apiKey
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        val apiKey = userApiKey ?: return@withContext Result.Error(IllegalStateException("OpenAI API key not configured"))

        try {
            val prompt = buildEnrichmentPrompt(context)
            val model = selectModel(context.completedFields)

            val requestBody = JSONObject().apply {
                put("model", model)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", "You are an expert spiritual advisor combining numerology, astrology, ayurveda, and human design.")
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", prompt)
                    })
                })
                put("temperature", 0.7)
                put("max_tokens", 3000)
                put("response_format", JSONObject().put("type", "text")) // Can use json_object for structured
            }.toString()

            val request = Request.Builder()
                .url("$baseUrl/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.Error(Exception("OpenAI API error: ${response.code} - ${response.message}"))
            }

            val responseBody = response.body?.string() ?: return@withContext Result.Error(Exception("Empty response"))
            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")

            Result.Success(EnrichmentResult(
                text = content,
                confidence = 0.93f // GPT-4 models are highly reliable
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
            completedFields >= 24 -> "gpt-4o"  // Best quality for comprehensive profiles
            completedFields >= 10 -> "gpt-4o-mini"  // Fast and affordable
            else -> "gpt-3.5-turbo"  // Basic analysis
        }
    }

    private fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        return """
            Analyze this spiritual profile and provide personalized insights.

            Completion: ${context.completedFields}/${context.totalFields} fields (${context.accuracyLevel})

            Numerology Data: ${formatMap(context.numerology)}
            Astrology Data: ${formatMap(context.astrology)}
            Energy Profile: ${formatMap(context.energyProfile)}
            Personal Details: ${formatMap(context.personalDetails)}

            Provide comprehensive analysis:
            1. Life Purpose & Soul Mission
            2. Natural Strengths & Talents
            3. Growth Challenges
            4. Relationship Patterns
            5. Career & Vocation Alignment
            6. Spiritual Guidance & Practices

            Format in markdown with clear sections.
        """.trimIndent()
    }

    private fun formatMap(map: Map<String, Any>): String {
        return map.entries.take(5).joinToString(", ") { "${it.key}: ${it.value}" }
            .ifBlank { "None" }
    }

    companion object {
        const val PROVIDER_NAME = "OpenAI"

        // Pricing (user's account)
        const val GPT_4O_INPUT_PER_1M = 2.50  // $2.50/1M tokens
        const val GPT_4O_OUTPUT_PER_1M = 10.00
        const val GPT_4O_MINI_INPUT_PER_1M = 0.150  // $0.15/1M tokens
        const val GPT_4O_MINI_OUTPUT_PER_1M = 0.600
    }
}
