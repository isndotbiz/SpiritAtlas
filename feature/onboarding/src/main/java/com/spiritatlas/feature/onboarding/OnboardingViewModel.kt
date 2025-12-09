package com.spiritatlas.feature.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val isCompleted: Boolean = false
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private val pages = getOnboardingPages()
    private val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "spiritatlas_onboarding"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        val isCompleted = sharedPrefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        _uiState.value = _uiState.value.copy(isCompleted = isCompleted)
    }

    fun onPageChanged(page: Int) {
        _uiState.value = _uiState.value.copy(
            currentPage = page,
            isLastPage = page == pages.size - 1
        )
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            sharedPrefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
            _uiState.value = _uiState.value.copy(isCompleted = true)
        }
    }

    fun skipOnboarding() {
        completeOnboarding()
    }

    fun getTotalPages(): Int = pages.size

    fun isOnboardingCompleted(): Boolean {
        return sharedPrefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun resetOnboarding() {
        viewModelScope.launch {
            sharedPrefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, false).apply()
            _uiState.value = OnboardingUiState()
        }
    }
}
