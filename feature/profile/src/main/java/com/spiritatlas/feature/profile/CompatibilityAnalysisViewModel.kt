package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.repository.*
import com.spiritatlas.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompatibilityAnalysisViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tantricContentRepository: TantricContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CompatibilityAnalysisUiState>(CompatibilityAnalysisUiState.Loading)
    val uiState: StateFlow<CompatibilityAnalysisUiState> = _uiState.asStateFlow()

    fun analyzeCompatibility(profile1Id: String, profile2Id: String) {
        viewModelScope.launch {
            _uiState.value = CompatibilityAnalysisUiState.Loading
            
            try {
                // Load both profiles
                val profile1 = userRepository.getProfileById(profile1Id)
                val profile2 = userRepository.getProfileById(profile2Id)

                if (profile1 == null || profile2 == null) {
                    _uiState.value = CompatibilityAnalysisUiState.Error(
                        "Could not find both profiles for compatibility analysis"
                    )
                    return@launch
                }

                // Check if both profiles have sufficient data
                if (profile1.profileCompletion.completedFields < 3 || 
                    profile2.profileCompletion.completedFields < 3) {
                    _uiState.value = CompatibilityAnalysisUiState.Error(
                        "Both profiles need at least 3 completed fields for accurate compatibility analysis"
                    )
                    return@launch
                }

                // Create spiritual profiles for compatibility analysis
                val spiritualProfile1 = createSpiritualProfile(profile1)
                val spiritualProfile2 = createSpiritualProfile(profile2)

                // Perform compatibility analysis using the tantric content repository
                tantricContentRepository.getCompatibilityAnalysis(spiritualProfile1, spiritualProfile2)
                    .collect { result ->
                        when (result) {
                            is Result.Loading -> {
                                _uiState.value = CompatibilityAnalysisUiState.Loading
                            }
                            is Result.Success -> {
                                _uiState.value = CompatibilityAnalysisUiState.Success(
                                    analysis = result.data,
                                    profile1Name = profile1.displayName ?: profile1.name ?: "Profile 1",
                                    profile2Name = profile2.displayName ?: profile2.name ?: "Profile 2"
                                )
                            }
                            is Result.Error -> {
                                _uiState.value = CompatibilityAnalysisUiState.Error(
                                    result.exception.message ?: "Failed to analyze compatibility"
                                )
                            }
                        }
                    }

            } catch (e: Exception) {
                _uiState.value = CompatibilityAnalysisUiState.Error(
                    "Error analyzing compatibility: ${e.message}"
                )
            }
        }
    }

    private fun createSpiritualProfile(userProfile: UserProfile): UserSpiritualProfile {
        // Convert UserProfile to UserSpiritualProfile for compatibility analysis
        return UserSpiritualProfile(
            userId = userProfile.id,
            numerologyProfile = userProfile.name?.let { createNumerologyProfile(it) },
            astrologicalProfile = userProfile.birthDateTime?.let { 
                createAstrologicalProfile(userProfile) 
            },
            chakraProfile = createChakraProfile(userProfile),
            tantricPreferences = createTantricPreferences(userProfile),
            experienceLevel = determineExperienceLevel(userProfile),
            updatedAt = System.currentTimeMillis()
        )
    }

    private fun createNumerologyProfile(name: String): com.spiritatlas.domain.tantric.NumerologyProfile {
        // Simplified numerology calculation
        val nameValue = name.uppercase().map { char ->
            when (char) {
                'A', 'J', 'S' -> 1
                'B', 'K', 'T' -> 2
                'C', 'L', 'U' -> 3
                'D', 'M', 'V' -> 4
                'E', 'N', 'W' -> 5
                'F', 'O', 'X' -> 6
                'G', 'P', 'Y' -> 7
                'H', 'Q', 'Z' -> 8
                'I', 'R' -> 9
                else -> 0
            }
        }.sum()
        
        val lifePathNumber = reduceToSingleDigit(nameValue)
        
        return com.spiritatlas.domain.tantric.NumerologyProfile(
            lifePathNumber = lifePathNumber,
            expressionNumber = lifePathNumber, // Simplified
            soulUrgeNumber = lifePathNumber,
            personalityNumber = lifePathNumber,
            birthDayNumber = lifePathNumber
        )
    }

    private fun createAstrologicalProfile(userProfile: UserProfile): com.spiritatlas.domain.tantric.AstrologicalProfile? {
        val birthDateTime = userProfile.birthDateTime ?: return null
        
        // Simplified astrological calculation based on birth month
        val sunSign = when (birthDateTime.monthValue) {
            1 -> if (birthDateTime.dayOfMonth <= 19) "Capricorn" else "Aquarius"
            2 -> if (birthDateTime.dayOfMonth <= 18) "Aquarius" else "Pisces"
            3 -> if (birthDateTime.dayOfMonth <= 20) "Pisces" else "Aries"
            4 -> if (birthDateTime.dayOfMonth <= 19) "Aries" else "Taurus"
            5 -> if (birthDateTime.dayOfMonth <= 20) "Taurus" else "Gemini"
            6 -> if (birthDateTime.dayOfMonth <= 20) "Gemini" else "Cancer"
            7 -> if (birthDateTime.dayOfMonth <= 22) "Cancer" else "Leo"
            8 -> if (birthDateTime.dayOfMonth <= 22) "Leo" else "Virgo"
            9 -> if (birthDateTime.dayOfMonth <= 22) "Virgo" else "Libra"
            10 -> if (birthDateTime.dayOfMonth <= 22) "Libra" else "Scorpio"
            11 -> if (birthDateTime.dayOfMonth <= 21) "Scorpio" else "Sagittarius"
            12 -> if (birthDateTime.dayOfMonth <= 21) "Sagittarius" else "Capricorn"
            else -> "Unknown"
        }
        
        return com.spiritatlas.domain.tantric.AstrologicalProfile(
            sunSign = sunSign,
            moonSign = sunSign, // Simplified
            risingSign = sunSign, 
            venusSign = sunSign,
            marsSign = sunSign
        )
    }

    private fun createChakraProfile(userProfile: UserProfile): com.spiritatlas.domain.tantric.ChakraProfile {
        // Create a balanced chakra profile as default
        return com.spiritatlas.domain.tantric.ChakraProfile(
            chakraScores = mapOf(
                "Root" to 0.7f,
                "Sacral" to 0.8f,
                "Solar Plexus" to 0.6f,
                "Heart" to 0.9f,
                "Throat" to 0.5f,
                "Third Eye" to 0.8f,
                "Crown" to 0.7f
            ),
            dominantChakras = listOf("Heart", "Third Eye"),
            blockedChakras = listOf("Throat")
        )
    }

    private fun createTantricPreferences(userProfile: UserProfile): TantricPreferences {
        return TantricPreferences(
            preferredPractices = listOf("Breathing", "Meditation", "Energy Work"),
            intensityPreference = 5, // Medium intensity as default
            focusAreas = listOf("Intimacy", "Spirituality", "Connection"),
            partnerPreferences = null, // Could be expanded based on profile data
            meditationExperience = ExperienceLevel.BEGINNER,
            breathworkExperience = ExperienceLevel.BEGINNER
        )
    }

    private fun determineExperienceLevel(userProfile: UserProfile): ExperienceLevel {
        // Determine experience level based on profile completion
        return when (userProfile.profileCompletion.completedFields) {
            in 0..5 -> ExperienceLevel.BEGINNER
            in 6..15 -> ExperienceLevel.INTERMEDIATE  
            in 16..25 -> ExperienceLevel.ADVANCED
            else -> ExperienceLevel.EXPERT
        }
    }

    private fun reduceToSingleDigit(number: Int): Int {
        var result = number
        while (result > 9) {
            result = result.toString().sumOf { it.digitToInt() }
        }
        return if (result == 0) 9 else result
    }
}

sealed class CompatibilityAnalysisUiState {
    object Loading : CompatibilityAnalysisUiState()
    
    data class Success(
        val analysis: CompatibilityAnalysis,
        val profile1Name: String,
        val profile2Name: String
    ) : CompatibilityAnalysisUiState()
    
    data class Error(
        val message: String
    ) : CompatibilityAnalysisUiState()
}
