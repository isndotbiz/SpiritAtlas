package com.spiritatlas.feature.consent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.domain.model.ConsentStatus
import com.spiritatlas.domain.ai.AiProviderMode
import com.spiritatlas.domain.ai.AiSettingsRepository
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsentViewModel @Inject constructor(
    private val consentRepository: ConsentRepository,
    private val aiSettingsRepository: AiSettingsRepository
) : ViewModel() {

    val consentMap: StateFlow<Map<ConsentType, ConsentStatus>> =
        combine(
            ConsentType.values().map { type ->
                consentRepository.getConsentStatus(type)
            }
        ) { statuses ->
            ConsentType.values().zip(statuses.toList()).toMap()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    fun toggle(type: ConsentType, granted: Boolean) {
        viewModelScope.launch {
            consentRepository.updateConsent(
                type,
                if (granted) ConsentStatus.GRANTED else ConsentStatus.DENIED
            )
        }
    }

    fun providerModeState(): StateFlow<AiProviderMode> =
        aiSettingsRepository.observeMode().stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), AiProviderMode.AUTO
        )

    fun setProviderMode(mode: AiProviderMode) {
        viewModelScope.launch { aiSettingsRepository.setMode(mode) }
    }
}


