package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.data.BuildConfig
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class OllamaProvider @Inject constructor(
    @Named("ollama") retrofit: Retrofit
) : AiTextProvider {
    private val api = retrofit.create(OllamaApi::class.java)

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        return try {
            val prompt = buildPrompt(context)
            val response = api.generate(OllamaRequest(prompt = prompt))
            Result.Success(EnrichmentResult(
                text = response.response.orEmpty(),
                confidence = 0.6f
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isAvailable(): Boolean {
        return BuildConfig.OLLAMA_BASE_URL.isNotEmpty()
    }

    private fun buildPrompt(context: EnrichmentContext): String = """
        Create a concise, positive insight from:
        Numerology: ${context.numerology}
        Astrology: ${context.astrology}
        Energy: ${context.energyProfile}
        Limit to ~200 words.
    """.trimIndent()
}


