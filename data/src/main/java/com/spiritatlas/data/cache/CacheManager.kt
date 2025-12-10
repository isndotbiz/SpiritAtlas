package com.spiritatlas.data.cache

import android.util.Log
import com.spiritatlas.data.database.dao.AiResponseCacheDao
import com.spiritatlas.data.database.dao.CompatibilityReportDao
import com.spiritatlas.domain.model.CompatibilityReport
import com.spiritatlas.domain.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized cache manager coordinating in-memory and persistent caching
 * Implements multi-tier caching strategy:
 * 1. Hot data in memory (LRU)
 * 2. Warm data in Room database
 * 3. Cold data requires regeneration
 */
@Singleton
class CacheManager @Inject constructor(
    private val compatibilityReportDao: CompatibilityReportDao,
    private val aiResponseCacheDao: AiResponseCacheDao
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // In-memory caches with size limits
    private val profileCache = InMemoryCache<UserProfile>(
        maxSize = 100,
        defaultTtlMillis = TimeUnit.HOURS.toMillis(24)
    )

    private val compatibilityReportCache = InMemoryCache<CompatibilityReport>(
        maxSize = 50,
        defaultTtlMillis = TimeUnit.DAYS.toMillis(7)
    )

    private val aiResponseCache = InMemoryCache<String>(
        maxSize = 100,
        defaultTtlMillis = TimeUnit.HOURS.toMillis(24)
    )

    companion object {
        private const val TAG = "CacheManager"

        // Cache configuration
        const val MAX_PROFILES = 100
        const val MAX_COMPATIBILITY_REPORTS = 50
        const val MAX_AI_RESPONSES = 100

        // TTL configuration
        val PROFILE_TTL = TimeUnit.HOURS.toMillis(24)
        val COMPATIBILITY_REPORT_TTL = TimeUnit.DAYS.toMillis(7)
        val AI_RESPONSE_TTL = TimeUnit.HOURS.toMillis(24)
    }

    init {
        // Schedule periodic cleanup
        scope.launch {
            schedulePeriodicCleanup()
        }
    }

    // === Profile Caching ===

    suspend fun cacheProfile(profile: UserProfile) {
        profileCache.put(profile.id, profile, PROFILE_TTL)
        Log.d(TAG, "Cached profile: ${profile.id}")
    }

    suspend fun getCachedProfile(profileId: String): UserProfile? {
        return profileCache.get(profileId)
    }

    suspend fun removeCachedProfile(profileId: String) {
        profileCache.remove(profileId)
        Log.d(TAG, "Removed cached profile: $profileId")
    }

    // === Compatibility Report Caching ===

    suspend fun cacheCompatibilityReport(report: CompatibilityReport) {
        val cacheKey = generateReportCacheKey(report.profileA.id, report.profileB.id)
        compatibilityReportCache.put(cacheKey, report, COMPATIBILITY_REPORT_TTL)
        Log.d(TAG, "Cached compatibility report: $cacheKey")
    }

    suspend fun getCachedCompatibilityReport(
        profileAId: String,
        profileBId: String
    ): CompatibilityReport? {
        val cacheKey = generateReportCacheKey(profileAId, profileBId)
        return compatibilityReportCache.get(cacheKey)
    }

    suspend fun removeCachedCompatibilityReport(profileAId: String, profileBId: String) {
        val cacheKey = generateReportCacheKey(profileAId, profileBId)
        compatibilityReportCache.remove(cacheKey)
        Log.d(TAG, "Removed cached compatibility report: $cacheKey")
    }

    // === AI Response Caching ===

    suspend fun cacheAiResponse(
        prompt: String,
        model: String,
        provider: String,
        response: String,
        ttlMillis: Long = AI_RESPONSE_TTL
    ) {
        val cacheKey = generateAiCacheKey(prompt, model, provider)
        aiResponseCache.put(cacheKey, response, ttlMillis)
        Log.d(TAG, "Cached AI response: ${cacheKey.take(16)}...")
    }

    suspend fun getCachedAiResponse(
        prompt: String,
        model: String,
        provider: String
    ): String? {
        val cacheKey = generateAiCacheKey(prompt, model, provider)
        return aiResponseCache.get(cacheKey)
    }

    // === Cache Management ===

    /**
     * Clear all in-memory caches
     */
    suspend fun clearAllMemoryCaches() {
        profileCache.clear()
        compatibilityReportCache.clear()
        aiResponseCache.clear()
        Log.d(TAG, "Cleared all in-memory caches")
    }

    /**
     * Clear specific cache type
     */
    suspend fun clearCache(cacheType: CacheType) {
        when (cacheType) {
            CacheType.PROFILES -> {
                profileCache.clear()
                Log.d(TAG, "Cleared profile cache")
            }
            CacheType.COMPATIBILITY_REPORTS -> {
                compatibilityReportCache.clear()
                compatibilityReportDao.clearCache()
                Log.d(TAG, "Cleared compatibility report cache")
            }
            CacheType.AI_RESPONSES -> {
                aiResponseCache.clear()
                aiResponseCacheDao.clearCache()
                Log.d(TAG, "Cleared AI response cache")
            }
            CacheType.ALL -> {
                clearAllMemoryCaches()
                compatibilityReportDao.clearCache()
                aiResponseCacheDao.clearCache()
                Log.d(TAG, "Cleared all caches")
            }
        }
    }

    /**
     * Get cache statistics across all caches
     */
    suspend fun getCacheStatistics(): CombinedCacheStats {
        return CombinedCacheStats(
            profiles = profileCache.getStats(),
            compatibilityReports = compatibilityReportCache.getStats(),
            aiResponses = aiResponseCache.getStats(),
            persistentCompatibilityReports = compatibilityReportDao.getCacheSize(),
            persistentAiResponses = aiResponseCacheDao.getCacheSize()
        )
    }

    /**
     * Trim caches to configured limits
     */
    suspend fun trimCaches() {
        // In-memory caches are self-trimming via LRU
        // Trim persistent caches
        compatibilityReportDao.trimToLimit(MAX_COMPATIBILITY_REPORTS)
        aiResponseCacheDao.trimToLimit(MAX_AI_RESPONSES)
        Log.d(TAG, "Trimmed persistent caches to limits")
    }

    /**
     * Clean up expired entries from all caches
     */
    suspend fun cleanupExpired() {
        profileCache.cleanupExpired()
        compatibilityReportCache.cleanupExpired()
        aiResponseCache.cleanupExpired()

        val currentTime = System.currentTimeMillis()
        compatibilityReportDao.deleteExpiredReports(currentTime)
        aiResponseCacheDao.deleteExpiredEntries(currentTime)

        Log.d(TAG, "Cleaned up expired entries from all caches")
    }

    /**
     * Schedule periodic cache cleanup
     */
    private suspend fun schedulePeriodicCleanup() {
        while (true) {
            kotlinx.coroutines.delay(TimeUnit.HOURS.toMillis(1))
            try {
                cleanupExpired()
                trimCaches()
            } catch (e: Exception) {
                Log.e(TAG, "Error during periodic cleanup", e)
            }
        }
    }

    // === Helper Functions ===

    private fun generateReportCacheKey(profileAId: String, profileBId: String): String {
        // Sort IDs to ensure consistent keys regardless of order
        val sorted = listOf(profileAId, profileBId).sorted()
        return "${sorted[0]}_${sorted[1]}"
    }

    private fun generateAiCacheKey(prompt: String, model: String, provider: String): String {
        val input = "$provider:$model:$prompt"
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}

/**
 * Cache type enumeration for targeted cache operations
 */
enum class CacheType {
    PROFILES,
    COMPATIBILITY_REPORTS,
    AI_RESPONSES,
    ALL
}

/**
 * Combined statistics across all cache layers
 */
data class CombinedCacheStats(
    val profiles: CacheStats,
    val compatibilityReports: CacheStats,
    val aiResponses: CacheStats,
    val persistentCompatibilityReports: Int,
    val persistentAiResponses: Int
) {
    val totalMemoryEntries: Int
        get() = profiles.size + compatibilityReports.size + aiResponses.size

    val totalPersistentEntries: Int
        get() = persistentCompatibilityReports + persistentAiResponses

    val overallHitRate: Double
        get() {
            val totalHits = profiles.hitCount + compatibilityReports.hitCount + aiResponses.hitCount
            val totalMisses = profiles.missCount + compatibilityReports.missCount + aiResponses.missCount
            return if (totalHits + totalMisses > 0) {
                totalHits.toDouble() / (totalHits + totalMisses)
            } else 0.0
        }
}
