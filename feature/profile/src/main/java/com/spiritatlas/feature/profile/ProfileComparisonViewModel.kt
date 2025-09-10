package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileComparisonUiState(
    val isLoading: Boolean = false,
    val profile1: UserProfile? = null,
    val profile2: UserProfile? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileComparisonViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileComparisonUiState())
    val uiState = _uiState.asStateFlow()
    
    fun loadProfiles(profileId1: String, profileId2: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                val profile1 = userRepository.getProfileById(profileId1)
                val profile2 = userRepository.getProfileById(profileId2)
                
                if (profile1 == null || profile2 == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "One or both profiles could not be found"
                    )
                    return@launch
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    profile1 = profile1,
                    profile2 = profile2
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load profiles: ${e.message}"
                )
            }
        }
    }
}
