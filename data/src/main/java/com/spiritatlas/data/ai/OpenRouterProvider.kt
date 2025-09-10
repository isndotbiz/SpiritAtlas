package com.spiritatlas.data.ai

import com.spiritatlas.data.BuildConfig
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import retrofit2.Retrofit
import javax.inject.Inject

class OpenRouterProvider @Inject constructor(
    private val retrofit: Retrofit
) : AiTextProvider {
    
    private val api = retrofit.create(OpenRouterApi::class.java)
    
    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        return try {
            if (BuildConfig.OPENROUTER_API_KEY.isEmpty()) {
                return Result.Error(IllegalStateException("OpenRouter API key not configured"))
            }
            
            val prompt = buildPrompt(context)
            val response = api.complete(
                authorization = "Bearer ${BuildConfig.OPENROUTER_API_KEY}",
                request = CompletionRequest(
                    model = "openai/gpt-3.5-turbo",
                    messages = listOf(Message("user", prompt))
                )
            )
            
            Result.Success(EnrichmentResult(
                text = response.choices.firstOrNull()?.message?.content ?: "",
                confidence = 0.8f
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        return BuildConfig.OPENROUTER_API_KEY.isNotEmpty()
    }
    
    private fun buildPrompt(context: EnrichmentContext): String {
        return """
            Based on the following spiritual profile data, provide a brief insight:
            Numerology: ${context.numerology}
            Astrology: ${context.astrology}
            Energy Profile: ${context.energyProfile}
            
            Keep the response positive, empowering, and under 200 words.
        """.trimIndent()
    }
}


