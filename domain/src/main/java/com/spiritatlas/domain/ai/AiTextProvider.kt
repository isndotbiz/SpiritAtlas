package com.spiritatlas.domain.ai

import com.spiritatlas.core.common.Result

interface AiTextProvider {
    suspend fun generateEnrichment(
        context: EnrichmentContext
    ): Result<EnrichmentResult>
    
    suspend fun isAvailable(): Boolean
}

data class EnrichmentContext(
    val numerology: Map<String, Any>,
    val astrology: Map<String, Any>,
    val energyProfile: Map<String, Any>,
    val personalDetails: Map<String, Any>, // Name, birth info, family, etc.
    val completedFields: Int,
    val totalFields: Int,
    val accuracyLevel: String
)

data class EnrichmentResult(
    val text: String,
    val confidence: Float
)


