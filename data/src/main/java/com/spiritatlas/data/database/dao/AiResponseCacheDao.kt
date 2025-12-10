package com.spiritatlas.data.database.dao

import androidx.room.*
import com.spiritatlas.data.database.entities.AiResponseCacheEntity

/**
 * Data Access Object for AI response caching
 * Reduces API calls by caching responses with TTL
 */
@Dao
interface AiResponseCacheDao {

    /**
     * Get cached response by request hash if not expired
     */
    @Query("""
        SELECT * FROM ai_response_cache
        WHERE requestHash = :requestHash
          AND expiresAt > :currentTime
        LIMIT 1
    """)
    suspend fun getCachedResponse(
        requestHash: String,
        currentTime: Long = System.currentTimeMillis()
    ): AiResponseCacheEntity?

    /**
     * Insert or update a cached AI response
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedResponse(response: AiResponseCacheEntity)

    /**
     * Update hit count when cache is accessed
     */
    @Query("""
        UPDATE ai_response_cache
        SET hitCount = hitCount + 1,
            lastHitAt = :timestamp
        WHERE id = :cacheId
    """)
    suspend fun recordHit(cacheId: Long, timestamp: Long = System.currentTimeMillis())

    /**
     * Delete expired cache entries
     */
    @Query("DELETE FROM ai_response_cache WHERE expiresAt < :currentTime")
    suspend fun deleteExpiredEntries(currentTime: Long = System.currentTimeMillis())

    /**
     * Delete least recently used entries when over limit
     */
    @Query("""
        DELETE FROM ai_response_cache
        WHERE id NOT IN (
            SELECT id FROM ai_response_cache
            ORDER BY lastHitAt DESC
            LIMIT :maxEntries
        )
    """)
    suspend fun trimToLimit(maxEntries: Int = 100)

    /**
     * Get total cache size
     */
    @Query("SELECT COUNT(*) FROM ai_response_cache")
    suspend fun getCacheSize(): Int

    /**
     * Get total cache hits
     */
    @Query("SELECT SUM(hitCount) FROM ai_response_cache")
    suspend fun getTotalHits(): Int?

    /**
     * Get cache statistics by provider
     */
    @Query("""
        SELECT provider, COUNT(*) as count, SUM(hitCount) as hits, SUM(tokensUsed) as tokens
        FROM ai_response_cache
        GROUP BY provider
    """)
    suspend fun getCacheStatsByProvider(): List<CacheStatsByProvider>

    /**
     * Clear all cached responses
     */
    @Query("DELETE FROM ai_response_cache")
    suspend fun clearCache()

    /**
     * Clear cache for specific provider
     */
    @Query("DELETE FROM ai_response_cache WHERE provider = :provider")
    suspend fun clearCacheForProvider(provider: String)
}

/**
 * Result class for cache statistics query
 */
data class CacheStatsByProvider(
    val provider: String,
    val count: Int,
    val hits: Int,
    val tokens: Int
)
