package com.spiritatlas.feature.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.SampleUserProfile
import com.spiritatlas.domain.model.RandomProfileGenerator
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing spiritual profile creation and editing
 * Handles database persistence with real-time completion tracking ✨
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    private val _navigationEvent = MutableStateFlow<ProfileNavigationEvent?>(null)
    val navigationEvent: StateFlow<ProfileNavigationEvent?> = _navigationEvent.asStateFlow()
    
    init {
        // Don't auto-load in init - wait for explicit call from UI
    }
    
    /**
     * Load existing profile from database or create empty one
     * If profileId is null or "new", creates a new profile
     */
    fun loadProfile(profileId: String? = null) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                
                val profile = when {
                    profileId == null || profileId == "new" -> createEmptyProfile()
                    else -> {
                        userRepository.getProfileById(profileId) ?: createEmptyProfile()
                    }
                }
                
                _uiState.value = _uiState.value.copy(
                    currentProfile = profile,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    currentProfile = createEmptyProfile(),
                    isLoading = false,
                    errorMessage = "Failed to load profile: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Load existing profile from database or create empty one (deprecated - use loadProfile instead)
     */
    private fun loadExistingProfile() {
        viewModelScope.launch {
            try {
                val existingProfile = userRepository.getUserProfile().first()
                _uiState.value = _uiState.value.copy(
                    currentProfile = existingProfile ?: createEmptyProfile(),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    currentProfile = createEmptyProfile(),
                    isLoading = false,
                    errorMessage = "Failed to load profile: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Update the current profile (triggers real-time completion calculation)
     */
    fun updateProfile(updatedProfile: UserProfile) {
        _uiState.value = _uiState.value.copy(currentProfile = updatedProfile)
    }
    
    /**
     * Save current profile to database
     */
    fun saveProfile() {
        viewModelScope.launch {
            try {
                Log.d("SpiritAtlas", "Save button clicked, starting save process...")
                _uiState.value = _uiState.value.copy(isSaving = true, errorMessage = null)
                
                val profile = _uiState.value.currentProfile
                Log.d("SpiritAtlas", "Saving profile with ID: ${profile.id}, name: ${profile.name}")
                
                userRepository.saveUserProfile(profile)
                
                Log.d("SpiritAtlas", "Save completed successfully")
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    saveSuccess = true
                )
                
                // Clear success message after 3 seconds
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(saveSuccess = false)
                
            } catch (e: Exception) {
                Log.e("SpiritAtlas", "Error in saveProfile: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = "Failed to save profile: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Load demo profile (Luna Alexandra Moonwhisper)
     */
    fun loadDemoProfile() {
        _uiState.value = _uiState.value.copy(
            currentProfile = SampleUserProfile.maxAccuracySample
        )
    }
    
    /**
     * Load randomized tier testing profiles for AI enrichment validation
     * Each call generates a new random profile with different data
     */
    fun loadTier0Profile() {
        val randomIndex = (System.currentTimeMillis() % 10).toInt()
        val isMale = (System.currentTimeMillis() % 2) == 0L
        _uiState.value = _uiState.value.copy(
            currentProfile = RandomProfileGenerator.generateRandomProfile(0, isMale, randomIndex)
        )
    }
    
    fun loadTier1Profile() {
        val randomIndex = (System.currentTimeMillis() % 10).toInt()
        val isMale = (System.currentTimeMillis() % 2) == 0L
        _uiState.value = _uiState.value.copy(
            currentProfile = RandomProfileGenerator.generateRandomProfile(1, isMale, randomIndex)
        )
    }
    
    fun loadTier2Profile() {
        val randomIndex = (System.currentTimeMillis() % 10).toInt()
        val isMale = (System.currentTimeMillis() % 2) == 0L
        _uiState.value = _uiState.value.copy(
            currentProfile = RandomProfileGenerator.generateRandomProfile(2, isMale, randomIndex)
        )
    }
    
    fun loadTier3Profile() {
        val randomIndex = (System.currentTimeMillis() % 10).toInt()
        val isMale = (System.currentTimeMillis() % 2) == 0L
        _uiState.value = _uiState.value.copy(
            currentProfile = RandomProfileGenerator.generateRandomProfile(3, isMale, randomIndex)
        )
    }
    
    /**
     * Clear profile (create new empty one)
     */
    fun clearProfile() {
        _uiState.value = _uiState.value.copy(
            currentProfile = createEmptyProfile()
        )
    }
    
    /**
     * Set active section for UI navigation
     */
    fun setActiveSection(section: ProfileSection) {
        _uiState.value = _uiState.value.copy(activeSection = section)
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    /**
     * Clear navigation event after handling
     */
    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
    
    /**
     * Generate compatibility report between two profiles
     */
    fun generateCompatibilityReport(profile1Id: String, profile2Id: String) {
        viewModelScope.launch {
            try {
                val profile1 = userRepository.getProfileById(profile1Id)
                val profile2 = userRepository.getProfileById(profile2Id)
                
                if (profile1 != null && profile2 != null) {
                    // Check if both profiles have sufficient data for compatibility analysis
                    if (profile1.profileCompletion.completedFields >= 3 && profile2.profileCompletion.completedFields >= 3) {
                        // Navigate to compatibility analysis screen
                        _navigationEvent.value = ProfileNavigationEvent.NavigateToCompatibility(profile1Id, profile2Id)
                        Log.d("SpiritAtlas", "Navigating to compatibility analysis for profiles: $profile1Id, $profile2Id")
                    } else {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = "Both profiles need at least 3 completed fields for compatibility analysis. Please complete more profile information first."
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Could not find both profiles for compatibility analysis."
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to generate compatibility report: ${e.message}"
                )
            }
        }
    }
    
    private fun createEmptyProfile(): UserProfile {
        val now = java.time.LocalDateTime.now()
        return UserProfile(
            id = "user_profile_${System.currentTimeMillis()}",
            profileName = "New Profile",
            createdAt = now,
            lastModified = now,
            name = null,
            displayName = null,
            birthDateTime = null,
            birthPlace = null
        )
    }
}

/**
 * UI state for profile screen
 */
data class ProfileUiState(
    val currentProfile: UserProfile = UserProfile(
        id = "empty",
        profileName = "",
        createdAt = java.time.LocalDateTime.now(),
        lastModified = java.time.LocalDateTime.now(),
        name = null,
        displayName = null, 
        birthDateTime = null,
        birthPlace = null
    ),
    val activeSection: ProfileSection = ProfileSection.CORE,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Profile section enum for navigation
 */
enum class ProfileSection(val displayName: String, val icon: String) {
    CORE("Core", "✨"),
    NAMES("Names", "🏷️"),
    FAMILY("Family", "👨‍👩‍👧‍👦"),
    PHYSICAL("Physical", "⚡"),
    TIMING("Timing", "🕐"),
    ENVIRONMENTAL("Environment", "🌙"),
    LIFE_PATTERNS("Patterns", "🌱"),
    PREFERENCES("Settings", "⚙️")
}

/**
 * Navigation events for profile screen
 */
sealed class ProfileNavigationEvent {
    data class NavigateToCompatibility(val profile1Id: String, val profile2Id: String) : ProfileNavigationEvent()
}
