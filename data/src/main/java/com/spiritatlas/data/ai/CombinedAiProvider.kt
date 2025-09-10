package com.spiritatlas.data.ai

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CombinedAiProvider @Inject constructor(
    private val openRouterProvider: OpenRouterProvider,
    private val ollamaProvider: OllamaProvider,
    private val aiSettingsRepository: AiSettingsRepository
) : AiTextProvider {

    private suspend fun selectProvider(): AiTextProvider? {
        val mode = aiSettingsRepository.observeMode().first()
        return when (mode) {
            AiProviderMode.CLOUD -> if (openRouterProvider.isAvailable()) openRouterProvider else null
            AiProviderMode.LOCAL -> if (ollamaProvider.isAvailable()) ollamaProvider else null
            AiProviderMode.AUTO -> when {
                openRouterProvider.isAvailable() -> openRouterProvider
                ollamaProvider.isAvailable() -> ollamaProvider
                else -> null
            }
        }
    }

    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        val provider = selectProvider() ?: return Result.Error(IllegalStateException("No AI provider available"))
        return provider.generateEnrichment(context)
    }

    override suspend fun isAvailable(): Boolean {
        return openRouterProvider.isAvailable() || ollamaProvider.isAvailable()
    }
}


