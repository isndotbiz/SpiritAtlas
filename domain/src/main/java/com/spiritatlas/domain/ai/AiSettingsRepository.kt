package com.spiritatlas.domain.ai

import kotlinx.coroutines.flow.Flow

interface AiSettingsRepository {
    fun observeMode(): Flow<AiProviderMode>
    suspend fun setMode(mode: AiProviderMode)
}


