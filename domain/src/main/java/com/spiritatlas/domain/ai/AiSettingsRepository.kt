package com.spiritatlas.domain.ai

import kotlinx.coroutines.flow.Flow

interface AiSettingsRepository {
    fun observeMode(): Flow<AiProviderMode>
    suspend fun setMode(mode: AiProviderMode)

    // API Key Management
    suspend fun setOpenAiApiKey(apiKey: String?)
    suspend fun getOpenAiApiKey(): String?
    suspend fun setClaudeApiKey(apiKey: String?)
    suspend fun getClaudeApiKey(): String?
    suspend fun setOpenRouterApiKey(apiKey: String?)
    suspend fun getOpenRouterApiKey(): String?

    // Provider availability
    suspend fun isProviderAvailable(provider: AiProviderMode): Boolean
}


