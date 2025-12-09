package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Combined AI provider that routes requests to the appropriate provider based on user settings
 *
 * Priority order for AUTO mode:
 * 1. Gemini (free, excellent reasoning, 15 RPM)
 * 2. OpenRouter (paid/free models)
 * 3. Ollama (local, offline)
 */
class CombinedAiProvider @Inject constructor(
    private val geminiProvider: GeminiProvider,
    private val openRouterProvider: OpenRouterProvider,
    private val ollamaProvider: OllamaProvider,
    private val aiSettingsRepository: AiSettingsRepository
) : AiTextProvider {

    private suspend fun selectProvider(): AiTextProvider? {
        val mode = aiSettingsRepository.observeMode().first()
        return when (mode) {
            AiProviderMode.GEMINI -> if (geminiProvider.isAvailable()) geminiProvider else null
            AiProviderMode.OPENROUTER -> if (openRouterProvider.isAvailable()) openRouterProvider else null
            AiProviderMode.LOCAL -> if (ollamaProvider.isAvailable()) ollamaProvider else null
            AiProviderMode.GROQ -> null // TODO: Implement Groq provider
            AiProviderMode.AUTO -> when {
                geminiProvider.isAvailable() -> geminiProvider  // Free tier, best default
                openRouterProvider.isAvailable() -> openRouterProvider
                ollamaProvider.isAvailable() -> ollamaProvider
                else -> null
            }
        }
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        val provider = selectProvider() ?: return Result.Error(IllegalStateException("No AI provider available. Please add a Gemini API key in local.properties"))
        return provider.generateEnrichment(context)
    }

    override suspend fun isAvailable(): Boolean {
        return geminiProvider.isAvailable() || openRouterProvider.isAvailable() || ollamaProvider.isAvailable()
    }
}


