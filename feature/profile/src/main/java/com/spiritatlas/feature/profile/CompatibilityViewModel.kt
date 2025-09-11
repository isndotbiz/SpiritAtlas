package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.UserRepository
import com.spiritatlas.domain.service.EnhancedCouplesAnalysisEngine
import com.spiritatlas.domain.service.DetailedCouplesReport
import javax.inject.Inject

/**
 * ViewModel for the modern Compatibility Analysis screen
 * Manages compatibility computation and report generation
 */
@HiltViewModel
class CompatibilityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val analysisEngine: EnhancedCouplesAnalysisEngine
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CompatibilityUiState())
    val uiState: StateFlow<CompatibilityUiState> = _uiState.asStateFlow()
    
    private val _compatibilityReport = MutableStateFlow<DetailedCouplesReport?>(null)
    val compatibilityReport: StateFlow<DetailedCouplesReport?> = _compatibilityReport.asStateFlow()
    
    fun analyzeCompatibility(profile1Id: String, profile2Id: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                
                // Fetch both profiles
                val profile1 = userRepository.getProfileById(profile1Id)
                val profile2 = userRepository.getProfileById(profile2Id)
                
                if (profile1 == null || profile2 == null) {
                    _uiState.update { 
                        it.copy(
                            isLoading = false, 
                            error = "One or both profiles could not be found"
                        ) 
                    }
                    return@launch
                }
                
                // Perform compatibility analysis
                val report = analysisEngine.analyzeCouplesCompatibility(profile1, profile2)
                
                _compatibilityReport.update { report }
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        profile1Name = profile1.profileName,
                        profile2Name = profile2.profileName
                    ) 
                }
                
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Failed to analyze compatibility: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun shareCompatibilityReport() {
        val report = _compatibilityReport.value ?: return
        val state = _uiState.value
        
        viewModelScope.launch {
            try {
                // In a real app, this would generate a shareable image or file
                // For now, we'll just show a message
                _uiState.update { 
                    it.copy(message = "Compatibility report sharing coming soon!")
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to share report: ${e.message}")
                }
            }
        }
    }
    
    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * UI state for the Compatibility screen
 */
data class CompatibilityUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val profile1Name: String? = null,
    val profile2Name: String? = null
)
