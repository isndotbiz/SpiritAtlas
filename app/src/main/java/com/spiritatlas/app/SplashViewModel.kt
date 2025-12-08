package com.spiritatlas.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for SplashScreen handling animation timing and navigation logic
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val SPLASH_DURATION = 2800L // Total animation duration
        private const val ANIMATION_STEP = 16L // ~60fps update rate
        private const val ONBOARDING_PREF_KEY = "onboarding_completed"
        private const val PREFS_NAME = "spirit_atlas_prefs"
    }

    private val _animationProgress = MutableStateFlow(0f)
    val animationProgress: StateFlow<Float> = _animationProgress.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        startAnimation()
    }

    /**
     * Start the splash animation sequence
     */
    private fun startAnimation() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()

            while (true) {
                val elapsed = System.currentTimeMillis() - startTime
                _animationProgress.value = elapsed.toFloat()

                if (elapsed >= SPLASH_DURATION) {
                    break
                }

                delay(ANIMATION_STEP)
            }

            // Animation complete, navigate to appropriate screen
            navigateToNextScreen()
        }
    }

    /**
     * Navigate to onboarding or home based on whether user has completed onboarding
     */
    private fun navigateToNextScreen() {
        val hasCompletedOnboarding = sharedPreferences.getBoolean(ONBOARDING_PREF_KEY, false)

        _navigationEvent.value = if (hasCompletedOnboarding) {
            NavigationEvent.NavigateToHome
        } else {
            NavigationEvent.NavigateToOnboarding
        }
    }

    /**
     * Mark onboarding as completed
     */
    fun completeOnboarding() {
        sharedPreferences.edit()
            .putBoolean(ONBOARDING_PREF_KEY, true)
            .apply()
    }

    /**
     * Check if onboarding has been completed
     */
    fun hasCompletedOnboarding(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_PREF_KEY, false)
    }

    /**
     * Clear navigation event after handling
     */
    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    /**
     * Reset onboarding status (for testing/debugging)
     */
    fun resetOnboarding() {
        sharedPreferences.edit()
            .putBoolean(ONBOARDING_PREF_KEY, false)
            .apply()
    }
}

/**
 * Navigation events from splash screen
 */
sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
    object NavigateToOnboarding : NavigationEvent()
}
