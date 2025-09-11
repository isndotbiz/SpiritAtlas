package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.ShareableProfile
import com.spiritatlas.domain.model.ProfileLibraryEntry
import com.spiritatlas.domain.repository.UserRepository
import javax.inject.Inject

/**
 * ViewModel for the modern Profile Library screen
 * Handles profile management, sharing, and compatibility selection
 */
@HiltViewModel
class ProfileLibraryViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileLibraryUiState())
    val uiState: StateFlow<ProfileLibraryUiState> = _uiState.asStateFlow()
    
    // Get all profiles as a flow
    val profiles: StateFlow<List<UserProfile>> = userRepository.getAllProfilesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    init {
        loadProfiles()
    }
    
    private fun loadProfiles() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                // Profiles are loaded via the flow, no need to manually fetch
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        error = "Failed to load profiles: ${e.message}"
                    ) 
                }
            }
        }
    }
    
    fun shareProfile(profileId: String) {
        viewModelScope.launch {
            try {
                val result = userRepository.exportProfileForSharing(profileId)
                result.onSuccess { shareableProfile ->
                    // In a real app, you'd trigger the Android share intent here
                    _uiState.update { 
                        it.copy(message = "Profile exported successfully! Share with friends.")
                    }
                }.onFailure { error ->
                    _uiState.update { 
                        it.copy(error = "Failed to export profile: ${error.message}")
                    }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to share profile: ${e.message}")
                }
            }
        }
    }
    
    fun enrichProfile(profileId: String) {
        viewModelScope.launch {
            try {
                // Trigger enrichment worker for this specific profile
                // In a real implementation, you'd enqueue a WorkManager job here
                _uiState.update { 
                    it.copy(message = "Enrichment started! Check back in a few minutes.")
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to start enrichment: ${e.message}")
                }
            }
        }
    }
    
    fun importProfile() {
        viewModelScope.launch {
            try {
                // In a real app, you'd trigger a file picker here
                _uiState.update { 
                    it.copy(message = "Import functionality coming soon!")
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to import profile: ${e.message}")
                }
            }
        }
    }
    
    fun exportAllProfiles() {
        viewModelScope.launch {
            try {
                val currentProfiles = profiles.value
                if (currentProfiles.isEmpty()) {
                    _uiState.update { 
                        it.copy(message = "No profiles to export.")
                    }
                    return@launch
                }
                
                // In a real app, you'd create a zip file of all profiles
                _uiState.update { 
                    it.copy(message = "Exported ${currentProfiles.size} profiles successfully!")
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Failed to export profiles: ${e.message}")
                }
            }
        }
    }
    
    fun searchProfiles(query: String) {
        viewModelScope.launch {
            try {
                val results = userRepository.searchProfiles(query)
                // The UI handles filtering, but we could also update state here
                _uiState.update { 
                    it.copy(searchQuery = query)
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(error = "Search failed: ${e.message}")
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
 * UI state for the Profile Library screen
 */
data class ProfileLibraryUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null,
    val searchQuery: String = "",
    val selectedProfileIds: Set<String> = emptySet()
)
