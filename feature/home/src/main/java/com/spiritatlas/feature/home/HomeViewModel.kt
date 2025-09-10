package com.spiritatlas.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    aiSettingsRepository: AiSettingsRepository,
    consentRepository: ConsentRepository
) : ViewModel() {
    val providerLabel: StateFlow<String> = aiSettingsRepository
        .observeMode()
        .map { mode ->
            when (mode) {
                AiProviderMode.CLOUD -> "AI Provider: OpenRouter"
                AiProviderMode.LOCAL -> "AI Provider: Ollama"
                AiProviderMode.AUTO -> "AI Provider: Auto (prefers OpenRouter)"
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "AI Provider: Auto (prefers OpenRouter)")
}

@HiltViewModel
class ConsentDebugViewModel @Inject constructor(
    private val consentRepository: ConsentRepository
) : ViewModel() {
    private val ai = consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT)
    private val cloud = consentRepository.getConsentStatus(ConsentType.CLOUD_SYNC)
    private val analytics = consentRepository.getConsentStatus(ConsentType.ANALYTICS)

    val debugText: StateFlow<String> = combine(ai, cloud, analytics) { a, c, an ->
        "AI_ENRICHMENT=${a.name}  CLOUD_SYNC=${c.name}  ANALYTICS=${an.name}"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")
}


