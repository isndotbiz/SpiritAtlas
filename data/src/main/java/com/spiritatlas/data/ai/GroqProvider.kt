package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.data.BuildConfig
import com.spiritatlas.data.tracking.AiProvider
import com.spiritatlas.data.tracking.UsageTracker
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Groq AI provider - Ultra-fast inference (800+ tokens/sec)
 *
 * Free tier limits:
 * - 30 requests per minute
 * - 6,000-30,000 tokens per minute (model dependent)
 *
 * Features:
 * - Blazing fast (3-11x faster than competitors)
 * - OpenAI-compatible API
 * - Llama 3.3 70B for reasoning
 * - Lean implementation using OkHttp (no SDK bloat)
 */
@Singleton
class GroqProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val usageTracker: UsageTracker
) : AiTextProvider {

    private val apiKey: String
        get() = BuildConfig.GROQ_API_KEY ?: ""

    private val baseUrl = "https://api.groq.com/openai/v1"

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> = withContext(Dispatchers.IO) {
        if (!isAvailable()) {
            return@withContext Result.Error(IllegalStateException("Groq API key not configured"))
        }

        // Check rate limits before making request
        if (!usageTracker.canMakeRequest(AiProvider.GROQ)) {
            val waitTime = usageTracker.getTimeUntilNextRequest(AiProvider.GROQ)
            val waitTimeSeconds = waitTime / 1000
            val waitTimeMinutes = waitTimeSeconds / 60

            val message = when {
                waitTimeMinutes > 60 -> "Groq rate limit reached. Try again in ${waitTimeMinutes / 60}h ${waitTimeMinutes % 60}m"
                waitTimeMinutes > 0 -> "Groq rate limit reached. Try again in ${waitTimeMinutes}m ${waitTimeSeconds % 60}s"
                else -> "Groq rate limit reached. Try again in ${waitTimeSeconds}s"
            }

            return@withContext Result.Error(RateLimitException(message))
        }

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
                put("max_tokens", 2000)
            }.toString()

            val request = Request.Builder()
                .url("$baseUrl/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody("application/json".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.Error(Exception("Groq API error: ${response.code} - ${response.message}"))
            }

            val responseBody = response.body?.string() ?: return@withContext Result.Error(Exception("Empty response"))
            val jsonResponse = JSONObject(responseBody)
            val content = jsonResponse
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")

            // Record successful request
            usageTracker.recordRequest(AiProvider.GROQ)

            Result.Success(EnrichmentResult(
                text = content,
                confidence = 0.88f // Llama 3.3 70B has good reliability
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        return apiKey.isNotBlank() && apiKey != "null"
    }

    private fun selectModel(completedFields: Int): String {
        // Use fastest model for quick analyses, 70B for comprehensive
        return when {
            completedFields >= 20 -> "llama-3.3-70b-versatile"  // Best reasoning
            else -> "llama-3.1-8b-instant"  // Fastest (800+ tok/s)
        }
    }

    private fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        return """
            Analyze this spiritual profile and provide personalized insights.

            Profile completion: ${context.completedFields}/${context.totalFields} fields

            Numerology: ${formatMap(context.numerology)}
            Astrology: ${formatMap(context.astrology)}
            Energy: ${formatMap(context.energyProfile)}
            Personal: ${formatMap(context.personalDetails)}

            Provide analysis covering:
            1. Life Purpose & Path
            2. Strengths & Gifts
            3. Challenges & Growth Areas
            4. Relationship Dynamics
            5. Career Alignment
            6. Spiritual Practices

            Format in markdown. Be specific and insightful.
        """.trimIndent()
    }

    private fun formatMap(map: Map<String, Any>): String {
        return map.entries.take(5).joinToString(", ") { "${it.key}: ${it.value}" }
            .ifBlank { "None" }
    }

    companion object {
        const val PROVIDER_NAME = "Groq"
        const val FREE_RPM = 30
        const val FREE_TPM_8B = 30000
        const val FREE_TPM_70B = 6000
    }
}
