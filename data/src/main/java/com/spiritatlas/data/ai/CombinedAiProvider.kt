package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.data.tracking.AiProvider
import com.spiritatlas.data.tracking.UsageTracker
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
 * Architecture:
 * - Built-in free providers: Gemini (app key), Groq (app key)
 * - User providers: OpenAI (user key/OAuth), Claude (user key/OAuth)
 * - Alternative: OpenRouter, Ollama
 *
 * Priority order for AUTO mode:
 * 1. Gemini (app-provided, free tier, excellent reasoning) - if not rate limited
 * 2. Groq (app-provided, free tier, ultra-fast) - if not rate limited
 * 3. User-configured providers (OpenAI, Claude)
 * 4. OpenRouter, Ollama
 */
class CombinedAiProvider @Inject constructor(
    private val geminiProvider: GeminiProvider,
    private val groqProvider: GroqProvider,
    private val openAIProvider: OpenAIProvider,
    private val claudeProvider: ClaudeProvider,
    private val openRouterProvider: OpenRouterProvider,
    private val ollamaProvider: OllamaProvider,
    private val aiSettingsRepository: AiSettingsRepository,
    private val usageTracker: UsageTracker
) : AiTextProvider {

    private suspend fun selectProvider(): AiTextProvider? {
        val mode = aiSettingsRepository.observeMode().first()
        return when (mode) {
            AiProviderMode.GEMINI -> if (geminiProvider.isAvailable()) geminiProvider else null
            AiProviderMode.GROQ -> if (groqProvider.isAvailable()) groqProvider else null
            AiProviderMode.OPENAI -> if (openAIProvider.isAvailable()) openAIProvider else null
            AiProviderMode.CLAUDE -> if (claudeProvider.isAvailable()) claudeProvider else null
            AiProviderMode.OPENROUTER -> if (openRouterProvider.isAvailable()) openRouterProvider else null
            AiProviderMode.LOCAL -> if (ollamaProvider.isAvailable()) ollamaProvider else null
            AiProviderMode.AUTO -> when {
                // Prefer Gemini if available and not rate limited
                geminiProvider.isAvailable() && usageTracker.canMakeRequest(AiProvider.GEMINI) -> geminiProvider
                // Fall back to Groq if available and not rate limited
                groqProvider.isAvailable() && usageTracker.canMakeRequest(AiProvider.GROQ) -> groqProvider
                // Try user-configured providers next
                openAIProvider.isAvailable() -> openAIProvider
                claudeProvider.isAvailable() -> claudeProvider
                openRouterProvider.isAvailable() -> openRouterProvider
                ollamaProvider.isAvailable() -> ollamaProvider
                // Last resort: use rate-limited providers (they will handle rate limit errors)
                geminiProvider.isAvailable() -> geminiProvider
                groqProvider.isAvailable() -> groqProvider
                else -> null
            }
            else -> null
        }
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        val provider = selectProvider() ?: return Result.Error(IllegalStateException("No AI provider available. Please add a Gemini API key in local.properties"))
        return provider.generateEnrichment(context)
    }

    override suspend fun isAvailable(): Boolean {
        return geminiProvider.isAvailable() ||
                groqProvider.isAvailable() ||
                openAIProvider.isAvailable() ||
                claudeProvider.isAvailable() ||
                openRouterProvider.isAvailable() ||
                ollamaProvider.isAvailable()
    }
}


