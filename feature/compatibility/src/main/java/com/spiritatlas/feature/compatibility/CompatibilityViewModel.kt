package com.spiritatlas.feature.compatibility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.repository.CompatibilityCriteria
import com.spiritatlas.domain.repository.ProfileMatch
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing compatibility analysis between user profiles
 * Handles profile selection, analysis execution, and results display
 */
@HiltViewModel
class CompatibilityViewModel @Inject constructor(
    private val compatibilityRepository: CompatibilityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompatibilityUiState())
    val uiState: StateFlow<CompatibilityUiState> = _uiState.asStateFlow()

    private val _compatibilityReport = MutableStateFlow<CompatibilityReport?>(null)
    val compatibilityReport: StateFlow<CompatibilityReport?> = _compatibilityReport.asStateFlow()

    init {
        loadAvailableProfiles()
    }

    /**
     * Load all available user profiles for selection
     */
    private fun loadAvailableProfiles() {
        viewModelScope.launch {
            userRepository.getAllProfilesFlow()
                .collect { profiles ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            availableProfiles = profiles,
                            isLoadingProfiles = false
                        )
                    }
                }
        }
    }

    /**
     * Set the first profile for compatibility analysis
     */
    fun selectProfileA(profile: UserProfile) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProfileA = profile,
                compatibilityReport = null // Clear previous analysis
            )
        }
    }

    /**
     * Set the second profile for compatibility analysis
     */
    fun selectProfileB(profile: UserProfile) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProfileB = profile,
                compatibilityReport = null // Clear previous analysis
            )
        }
    }

    /**
     * Start compatibility analysis between selected profiles
     */
    fun analyzeCompatibility() {
        val state = _uiState.value
        val profileA = state.selectedProfileA
        val profileB = state.selectedProfileB

        if (profileA == null || profileB == null) {
            _uiState.update { currentState ->
                currentState.copy(
                    errorMessage = "Please select both profiles for analysis"
                )
            }
            return
        }

        if (profileA.id == profileB.id) {
            _uiState.update { currentState ->
                currentState.copy(
                    errorMessage = "Cannot analyze compatibility with the same profile"
                )
            }
            return
        }

        viewModelScope.launch {
            compatibilityRepository.analyzeCompatibility(profileA, profileB)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _compatibilityReport.value = result.data
                            _uiState.update { currentState ->
                                currentState.copy(
                                    compatibilityReport = result.data,
                                    isAnalyzing = false,
                                    activeTab = CompatibilityTab.RESULTS
                                )
                            }
                            // Auto-save the report
                            saveCompatibilityReport(result.data)
                        }
                        is Result.Loading -> {
                            _uiState.update { it.copy(isAnalyzing = true) }
                        }
                        is Result.Error -> {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    isAnalyzing = false,
                                    errorMessage = "Analysis failed: ${result.exception.message}"
                                )
                            }
                        }
                    }
                }
        }
    }

    /**
     * Save compatibility report for future reference
     */
    private fun saveCompatibilityReport(report: CompatibilityReport) {
        viewModelScope.launch {
            compatibilityRepository.saveCompatibilityReport(report)
        }
    }

    /**
     * Load cached compatibility reports for the selected profile
     */
    fun loadCachedReports(profileId: String) {
        viewModelScope.launch {
            compatibilityRepository.getCachedCompatibilityReports(profileId)
                .collect { reports ->
                    _uiState.update { currentState ->
                        currentState.copy(cachedReports = reports)
                    }
                }
        }
    }

    /**
     * Search for compatible profiles based on criteria
     */
    fun searchCompatibleProfiles(baseProfile: UserProfile, criteria: CompatibilityCriteria = CompatibilityCriteria()) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }
            
            compatibilityRepository.findCompatibleProfiles(baseProfile, criteria)
                .collect { matches ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            compatibleMatches = matches,
                            isSearching = false
                        )
                    }
                }
        }
    }

    /**
     * Switch between different tabs in the compatibility screen
     */
    fun setActiveTab(tab: CompatibilityTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Load a cached compatibility report from history
     */
    fun loadReportFromHistory(report: CompatibilityReport) {
        _uiState.update { currentState ->
            currentState.copy(
                compatibilityReport = report,
                selectedProfileA = report.profileA,
                selectedProfileB = report.profileB,
                activeTab = CompatibilityTab.RESULTS,
                errorMessage = null
            )
        }
    }

    /**
     * Share compatibility report
     */
    fun shareReport(report: CompatibilityReport): String {
        return buildString {
            appendLine("🔮 Spiritual Compatibility Analysis")
            appendLine()
            appendLine("${report.profileA.displayName ?: report.profileA.name} ✨ ${report.profileB.displayName ?: report.profileB.name}")
            appendLine()
            appendLine("${report.compatibilityLevel.icon} Overall Compatibility: ${report.compatibilityLevel.displayName}")
            appendLine("Score: ${String.format("%.1f", report.overallScore.totalScore)}%")
            appendLine()
            
            if (report.insights.isNotEmpty()) {
                appendLine("🌟 Key Insights:")
                report.insights.take(3).forEach { insight ->
                    appendLine("${insight.icon} ${insight.title}")
                    appendLine("   ${insight.description}")
                    appendLine()
                }
            }
            
            if (report.strengths.isNotEmpty()) {
                appendLine("✨ Relationship Strengths:")
                report.strengths.take(2).forEach { strength ->
                    appendLine("${strength.icon} ${strength.aspect}")
                    appendLine()
                }
            }
            
            if (report.recommendations.isNotEmpty()) {
                appendLine("💡 Recommendations:")
                report.recommendations.take(2).forEach { rec ->
                    appendLine("${rec.icon} ${rec.title}")
                    appendLine("   ${rec.description}")
                    appendLine()
                }
            }
            
            appendLine("Generated by SpiritAtlas - Your AI Spiritual Guide")
            appendLine("Discover deeper connections through spiritual compatibility")
        }
    }
}

/**
 * UI state for compatibility analysis screen
 */
data class CompatibilityUiState(
    val availableProfiles: List<UserProfile> = emptyList(),
    val selectedProfileA: UserProfile? = null,
    val selectedProfileB: UserProfile? = null,
    val compatibilityReport: CompatibilityReport? = null,
    val cachedReports: List<CompatibilityReport> = emptyList(),
    val compatibleMatches: List<ProfileMatch> = emptyList(),
    val activeTab: CompatibilityTab = CompatibilityTab.SELECTION,
    val isLoadingProfiles: Boolean = false,
    val isAnalyzing: Boolean = false,
    val isSearching: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Different tabs/sections in the compatibility screen
 */
enum class CompatibilityTab(val displayName: String, val icon: String) {
    SELECTION("Profile Selection", "👥"),
    RESULTS("Analysis Results", "📊"),
    HISTORY("Compatibility History", "📚"),
    SEARCH("Find Matches", "🔍")
}
