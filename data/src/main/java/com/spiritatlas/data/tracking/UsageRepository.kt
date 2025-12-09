package com.spiritatlas.data.tracking

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for AI usage statistics
 *
 * Provides:
 * - Real-time usage stats via StateFlow
 * - Rate limit checking
 * - Usage reset functionality
 * - Time-to-next-request calculations
 */
@Singleton
class UsageRepository @Inject constructor(
    private val usageTracker: UsageTracker
) {
    /**
     * Observe Gemini usage statistics
     */
    fun observeGeminiUsage(): StateFlow<ProviderUsage> = usageTracker.geminiUsage

    /**
     * Observe Groq usage statistics
     */
    fun observeGroqUsage(): StateFlow<ProviderUsage> = usageTracker.groqUsage

    /**
     * Get current usage stats for a provider
     */
    suspend fun getUsageStats(provider: AiProvider): ProviderUsage {
        return usageTracker.getUsageStats(provider)
    }

    /**
     * Check if a request can be made to the provider
     */
    suspend fun canMakeRequest(provider: AiProvider): Boolean {
        return usageTracker.canMakeRequest(provider)
    }

    /**
     * Record a successful request to the provider
     * Call this AFTER the API request succeeds
     */
    suspend fun recordRequest(provider: AiProvider) {
        usageTracker.recordRequest(provider)
    }

    /**
     * Get time in milliseconds until the next request can be made
     * Returns 0 if request can be made immediately
     */
    suspend fun getTimeUntilNextRequest(provider: AiProvider): Long {
        return usageTracker.getTimeUntilNextRequest(provider)
    }

    /**
     * Reset usage counters for a specific provider
     */
    suspend fun resetUsage(provider: AiProvider) {
        usageTracker.resetUsage(provider)
    }

    /**
     * Reset all usage counters
     */
    suspend fun resetAllUsage() {
        usageTracker.resetAllUsage()
    }

    /**
     * Get formatted wait time message for UI display
     */
    suspend fun getWaitTimeMessage(provider: AiProvider): String? {
        val waitTimeMs = getTimeUntilNextRequest(provider)
        if (waitTimeMs == 0L) return null

        val waitTimeSeconds = waitTimeMs / 1000
        val waitTimeMinutes = waitTimeSeconds / 60
        val waitTimeHours = waitTimeMinutes / 60

        return when {
            waitTimeHours > 0 -> "${waitTimeHours}h ${waitTimeMinutes % 60}m"
            waitTimeMinutes > 0 -> "${waitTimeMinutes}m ${waitTimeSeconds % 60}s"
            else -> "${waitTimeSeconds}s"
        }
    }
}
