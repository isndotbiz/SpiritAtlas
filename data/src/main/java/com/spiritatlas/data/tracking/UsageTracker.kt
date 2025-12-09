package com.spiritatlas.data.tracking

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Usage tracker for AI provider rate limits using sliding window algorithm
 *
 * Tracks:
 * - Gemini: 15 RPM (requests per minute), 1500 RPD (requests per day)
 * - Groq: 30 RPM
 *
 * Storage: SharedPreferences with JSON-encoded timestamp arrays
 * Thread-safety: Mutex for concurrent access
 */
@Singleton
class UsageTracker @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val mutex = Mutex()

    // In-memory state flows for UI observation
    private val _geminiUsage = MutableStateFlow(ProviderUsage(0, 0, 15, 1500))
    val geminiUsage: StateFlow<ProviderUsage> = _geminiUsage.asStateFlow()

    private val _groqUsage = MutableStateFlow(ProviderUsage(0, 0, 30, null))
    val groqUsage: StateFlow<ProviderUsage> = _groqUsage.asStateFlow()

    init {
        // Load initial state
        refreshUsageStats()
    }

    /**
     * Check if a request to the provider is allowed under rate limits
     * @return true if request is allowed, false if rate limited
     */
    suspend fun canMakeRequest(provider: AiProvider): Boolean = mutex.withLock {
        val limits = when (provider) {
            AiProvider.GEMINI -> RateLimits(rpm = 15, rpd = 1500)
            AiProvider.GROQ -> RateLimits(rpm = 30, rpd = null)
        }

        val now = System.currentTimeMillis()
        val timestamps = loadTimestamps(provider)

        // Clean old timestamps outside the tracking window
        val oneMinuteAgo = now - MINUTE_IN_MILLIS
        val oneDayAgo = now - DAY_IN_MILLIS
        val recentTimestamps = timestamps.filter { it >= oneDayAgo }

        // Check per-minute limit
        val requestsInLastMinute = recentTimestamps.count { it >= oneMinuteAgo }
        if (requestsInLastMinute >= limits.rpm) {
            return@withLock false
        }

        // Check per-day limit if applicable
        if (limits.rpd != null) {
            val requestsInLastDay = recentTimestamps.size
            if (requestsInLastDay >= limits.rpd) {
                return@withLock false
            }
        }

        return@withLock true
    }

    /**
     * Record a request to the provider
     * Should be called AFTER a successful API call
     */
    suspend fun recordRequest(provider: AiProvider) = mutex.withLock {
        val now = System.currentTimeMillis()
        val timestamps = loadTimestamps(provider).toMutableList()

        // Add current timestamp
        timestamps.add(now)

        // Clean old timestamps (keep only last 24 hours)
        val oneDayAgo = now - DAY_IN_MILLIS
        val recentTimestamps = timestamps.filter { it >= oneDayAgo }

        // Save back to preferences
        saveTimestamps(provider, recentTimestamps)

        // Update UI state
        refreshUsageStats()
    }

    /**
     * Get current usage statistics for a provider
     */
    suspend fun getUsageStats(provider: AiProvider): ProviderUsage = mutex.withLock {
        val limits = when (provider) {
            AiProvider.GEMINI -> RateLimits(rpm = 15, rpd = 1500)
            AiProvider.GROQ -> RateLimits(rpm = 30, rpd = null)
        }

        val now = System.currentTimeMillis()
        val timestamps = loadTimestamps(provider)

        val oneMinuteAgo = now - MINUTE_IN_MILLIS
        val oneDayAgo = now - DAY_IN_MILLIS

        val requestsInLastMinute = timestamps.count { it >= oneMinuteAgo }
        val requestsInLastDay = timestamps.count { it >= oneDayAgo }

        return@withLock ProviderUsage(
            requestsPerMinute = requestsInLastMinute,
            requestsPerDay = requestsInLastDay,
            limitPerMinute = limits.rpm,
            limitPerDay = limits.rpd
        )
    }

    /**
     * Reset usage counters (for testing or manual reset)
     */
    suspend fun resetUsage(provider: AiProvider) = mutex.withLock {
        saveTimestamps(provider, emptyList())
        refreshUsageStats()
    }

    /**
     * Reset all usage counters
     */
    suspend fun resetAllUsage() = mutex.withLock {
        prefs.edit()
            .remove(getTimestampsKey(AiProvider.GEMINI))
            .remove(getTimestampsKey(AiProvider.GROQ))
            .apply()
        refreshUsageStats()
    }

    /**
     * Get time until next request is available (in milliseconds)
     * Returns 0 if request can be made now, or milliseconds to wait
     */
    suspend fun getTimeUntilNextRequest(provider: AiProvider): Long = mutex.withLock {
        val limits = when (provider) {
            AiProvider.GEMINI -> RateLimits(rpm = 15, rpd = 1500)
            AiProvider.GROQ -> RateLimits(rpm = 30, rpd = null)
        }

        val now = System.currentTimeMillis()
        val timestamps = loadTimestamps(provider)

        // Check per-minute limit
        val oneMinuteAgo = now - MINUTE_IN_MILLIS
        val requestsInLastMinute = timestamps.filter { it >= oneMinuteAgo }

        if (requestsInLastMinute.size >= limits.rpm) {
            // Find the oldest request in the window
            val oldestInWindow = requestsInLastMinute.minOrNull() ?: return@withLock 0
            // Time until that request falls out of the window
            return@withLock maxOf(0, (oldestInWindow + MINUTE_IN_MILLIS) - now)
        }

        // Check per-day limit if applicable
        if (limits.rpd != null) {
            val oneDayAgo = now - DAY_IN_MILLIS
            val requestsInLastDay = timestamps.filter { it >= oneDayAgo }

            if (requestsInLastDay.size >= limits.rpd) {
                // Find the oldest request in the day window
                val oldestInDay = requestsInLastDay.minOrNull() ?: return@withLock 0
                // Time until that request falls out of the window
                return@withLock maxOf(0, (oldestInDay + DAY_IN_MILLIS) - now)
            }
        }

        return@withLock 0
    }

    /**
     * Refresh UI state flows with current usage stats
     */
    private fun refreshUsageStats() {
        val now = System.currentTimeMillis()

        // Update Gemini usage
        val geminiTimestamps = loadTimestamps(AiProvider.GEMINI)
        val geminiPerMinute = geminiTimestamps.count { it >= now - MINUTE_IN_MILLIS }
        val geminiPerDay = geminiTimestamps.count { it >= now - DAY_IN_MILLIS }
        _geminiUsage.value = ProviderUsage(geminiPerMinute, geminiPerDay, 15, 1500)

        // Update Groq usage
        val groqTimestamps = loadTimestamps(AiProvider.GROQ)
        val groqPerMinute = groqTimestamps.count { it >= now - MINUTE_IN_MILLIS }
        val groqPerDay = groqTimestamps.count { it >= now - DAY_IN_MILLIS }
        _groqUsage.value = ProviderUsage(groqPerMinute, groqPerDay, 30, null)
    }

    /**
     * Load timestamps from SharedPreferences
     */
    private fun loadTimestamps(provider: AiProvider): List<Long> {
        val key = getTimestampsKey(provider)
        val json = prefs.getString(key, null) ?: return emptyList()

        return try {
            json.split(",").mapNotNull { it.toLongOrNull() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Save timestamps to SharedPreferences
     */
    private fun saveTimestamps(provider: AiProvider, timestamps: List<Long>) {
        val key = getTimestampsKey(provider)
        val json = timestamps.joinToString(",")
        prefs.edit().putString(key, json).apply()
    }

    private fun getTimestampsKey(provider: AiProvider): String {
        return when (provider) {
            AiProvider.GEMINI -> KEY_GEMINI_TIMESTAMPS
            AiProvider.GROQ -> KEY_GROQ_TIMESTAMPS
        }
    }

    companion object {
        private const val PREFS_NAME = "ai_usage_tracker"
        private const val KEY_GEMINI_TIMESTAMPS = "gemini_timestamps"
        private const val KEY_GROQ_TIMESTAMPS = "groq_timestamps"

        private const val MINUTE_IN_MILLIS = 60_000L
        private const val DAY_IN_MILLIS = 24 * 60 * 60 * 1000L
    }
}

/**
 * AI provider identifiers for usage tracking
 */
enum class AiProvider {
    GEMINI,
    GROQ
}

/**
 * Rate limits for a provider
 */
data class RateLimits(
    val rpm: Int,  // Requests per minute
    val rpd: Int?  // Requests per day (null if no limit)
)

/**
 * Current usage statistics for a provider
 */
data class ProviderUsage(
    val requestsPerMinute: Int,
    val requestsPerDay: Int,
    val limitPerMinute: Int,
    val limitPerDay: Int?
) {
    val percentagePerMinute: Float
        get() = if (limitPerMinute > 0) (requestsPerMinute.toFloat() / limitPerMinute) * 100 else 0f

    val percentagePerDay: Float
        get() = if (limitPerDay != null && limitPerDay > 0) {
            (requestsPerDay.toFloat() / limitPerDay) * 100
        } else 0f

    val isNearLimitPerMinute: Boolean
        get() = requestsPerMinute >= (limitPerMinute * 0.8).toInt()

    val isNearLimitPerDay: Boolean
        get() = limitPerDay != null && requestsPerDay >= (limitPerDay * 0.8).toInt()
}
