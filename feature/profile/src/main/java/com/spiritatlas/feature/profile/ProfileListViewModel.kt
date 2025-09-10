package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import android.util.Log
import javax.inject.Inject

/**
 * ViewModel for managing multiple spiritual profiles
 * Supports browsing, searching, and deleting profiles ✨
 */
@HiltViewModel
class ProfileListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    data class ProfileListUiState(
        val profiles: List<UserProfile> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val searchQuery: String = ""
    )
    
    private val _uiState = MutableStateFlow(ProfileListUiState())
    val uiState: StateFlow<ProfileListUiState> = _uiState.asStateFlow()
    
    init {
        loadProfiles()
    }
    
    /**
     * Load all profiles from the repository
     */
    private fun loadProfiles() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                Log.d("ProfileListVM", "Loading all profiles...")
                
                userRepository.getAllProfilesFlow()
                    .catch { error ->
                        Log.e("ProfileListVM", "Error loading profiles", error)
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Failed to load profiles"
                        )
                    }
                    .collect { profiles ->
                        Log.d("ProfileListVM", "Loaded ${profiles.size} profiles")
                        _uiState.value = _uiState.value.copy(
                            profiles = profiles,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
            } catch (e: Exception) {
                Log.e("ProfileListVM", "Error setting up profile loading", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load profiles"
                )
            }
        }
    }
    
    /**
     * Delete a specific profile
     */
    fun deleteProfile(profileId: String) {
        viewModelScope.launch {
            try {
                Log.d("ProfileListVM", "Deleting profile: $profileId")
                userRepository.deleteProfile(profileId)
                Log.d("ProfileListVM", "Profile deleted successfully")
            } catch (e: Exception) {
                Log.e("ProfileListVM", "Error deleting profile", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to delete profile: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Search profiles by name
     */
    fun searchProfiles(query: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(searchQuery = query, isLoading = true)
                
                val results = if (query.isBlank()) {
                    userRepository.getAllProfiles()
                } else {
                    userRepository.searchProfiles(query)
                }
                
                _uiState.value = _uiState.value.copy(
                    profiles = results,
                    isLoading = false,
                    errorMessage = null
                )
                
                Log.d("ProfileListVM", "Search for '$query' returned ${results.size} profiles")
            } catch (e: Exception) {
                Log.e("ProfileListVM", "Error searching profiles", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Search failed: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Clear any error messages
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    /**
     * Refresh the profiles list
     */
    fun refresh() {
        loadProfiles()
    }
}
