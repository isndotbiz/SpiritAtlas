package com.spiritatlas.app

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.data.mock.MockProfileInitializer
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
    @ApplicationContext private val context: Context,
    private val mockProfileInitializer: MockProfileInitializer
) : ViewModel() {

    companion object {
        private const val TAG = "SplashViewModel"
        private const val SPLASH_DURATION = 2800L // Total animation duration
        private const val ANIMATION_STEP = 16L // ~60fps update rate
        private const val ONBOARDING_PREF_KEY = "onboarding_completed"
        private const val MOCK_PROFILES_INITIALIZED_KEY = "mock_profiles_initialized"
        private const val PREFS_NAME = "spirit_atlas_prefs"
    }

    private val _animationProgress = MutableStateFlow(0f)
    val animationProgress: StateFlow<Float> = _animationProgress.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        // Initialize mock profiles asynchronously (non-blocking)
        initializeMockProfilesIfNeeded()

        // Start splash animation
        startAnimation()
    }

    /**
     * Initialize mock profiles on first app launch only.
     * Runs asynchronously and doesn't block the splash animation.
     */
    private fun initializeMockProfilesIfNeeded() {
        viewModelScope.launch {
            try {
                val alreadyInitialized = sharedPreferences.getBoolean(
                    MOCK_PROFILES_INITIALIZED_KEY,
                    false
                )

                if (!alreadyInitialized) {
                    Log.d(TAG, "First launch detected - initializing mock profiles...")

                    val createdCount = mockProfileInitializer.initializeMockProfiles()

                    // Mark as initialized
                    sharedPreferences.edit()
                        .putBoolean(MOCK_PROFILES_INITIALIZED_KEY, true)
                        .apply()

                    Log.d(TAG, "Mock profiles initialized successfully. Created $createdCount profiles.")
                } else {
                    Log.d(TAG, "Mock profiles already initialized, skipping.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing mock profiles: ${e.message}", e)
                // Don't crash the app - continue with splash animation
            }
        }
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

    /**
     * Reset mock profile initialization flag (for testing/debugging)
     * This will cause mock profiles to be re-initialized on next app launch.
     *
     * Note: This only resets the flag. To actually clear the profiles from the database,
     * use mockProfileInitializer.clearMockProfiles() separately.
     */
    fun resetMockProfileInitialization() {
        sharedPreferences.edit()
            .putBoolean(MOCK_PROFILES_INITIALIZED_KEY, false)
            .apply()
        Log.d(TAG, "Mock profile initialization flag reset.")
    }
}

/**
 * Navigation events from splash screen
 */
sealed class NavigationEvent {
    object NavigateToHome : NavigationEvent()
    object NavigateToOnboarding : NavigationEvent()
}
