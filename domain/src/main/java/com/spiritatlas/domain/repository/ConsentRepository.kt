package com.spiritatlas.domain.repository

import com.spiritatlas.domain.model.ConsentStatus
import kotlinx.coroutines.flow.Flow

interface ConsentRepository {
    suspend fun updateConsent(type: ConsentType, status: ConsentStatus)
    fun getConsentStatus(type: ConsentType): Flow<ConsentStatus>
    fun getAllConsentStatuses(): Flow<Map<ConsentType, ConsentStatus>>
}

enum class ConsentType {
    AI_ENRICHMENT,
    CLOUD_SYNC,
    ANALYTICS
}


