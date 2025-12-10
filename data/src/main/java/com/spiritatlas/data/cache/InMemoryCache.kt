package com.spiritatlas.data.cache

import android.util.LruCache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.TimeUnit

/**
 * Generic in-memory LRU cache with TTL support
 * Thread-safe implementation for hot data caching
 *
 * @param T The type of cached values
 * @param maxSize Maximum number of entries to cache
 * @param defaultTtlMillis Default time-to-live for cached entries
 */
class InMemoryCache<T : Any>(
    maxSize: Int,
    private val defaultTtlMillis: Long = TimeUnit.HOURS.toMillis(24)
) {
    private val cache = LruCache<String, CacheEntry<T>>(maxSize)
    private val mutex = Mutex()

    /**
     * Get a value from cache if it exists and is not expired
     */
    suspend fun get(key: String): T? = mutex.withLock {
        val entry = cache.get(key) ?: return null

        if (entry.isExpired()) {
            cache.remove(key)
            return null
        }

        entry.accessCount++
        entry.lastAccessedAt = System.currentTimeMillis()
        return entry.value
    }

    /**
     * Put a value into cache with optional custom TTL
     */
    suspend fun put(key: String, value: T, ttlMillis: Long = defaultTtlMillis): Unit = mutex.withLock {
        val entry = CacheEntry(
            value = value,
            createdAt = System.currentTimeMillis(),
            expiresAt = System.currentTimeMillis() + ttlMillis
        )
        cache.put(key, entry)
    }

    /**
     * Remove a specific entry from cache
     */
    suspend fun remove(key: String): Unit = mutex.withLock {
        cache.remove(key)
    }

    /**
     * Clear all entries from cache
     */
    suspend fun clear() = mutex.withLock {
        cache.evictAll()
    }

    /**
     * Get current cache size
     */
    fun size(): Int = cache.size()

    /**
     * Get cache statistics
     */
    suspend fun getStats(): CacheStats = mutex.withLock {
        val snapshot = cache.snapshot()
        var totalAccesses = 0L
        var expiredCount = 0

        snapshot.values.forEach { entry ->
            totalAccesses += entry.accessCount
            if (entry.isExpired()) expiredCount++
        }

        CacheStats(
            size = snapshot.size,
            maxSize = cache.maxSize(),
            hitCount = cache.hitCount().toLong(),
            missCount = cache.missCount().toLong(),
            putCount = cache.putCount().toLong(),
            evictionCount = cache.evictionCount().toLong(),
            totalAccesses = totalAccesses,
            expiredCount = expiredCount
        )
    }

    /**
     * Clean up expired entries
     */
    suspend fun cleanupExpired() = mutex.withLock {
        val keysToRemove = mutableListOf<String>()
        val snapshot = cache.snapshot()

        snapshot.forEach { (key, entry) ->
            if (entry.isExpired()) {
                keysToRemove.add(key)
            }
        }

        keysToRemove.forEach { cache.remove(it) }
    }

    /**
     * Internal cache entry with metadata
     */
    private data class CacheEntry<T>(
        val value: T,
        val createdAt: Long,
        val expiresAt: Long,
        var lastAccessedAt: Long = createdAt,
        var accessCount: Long = 0
    ) {
        fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt
    }
}

/**
 * Cache statistics for monitoring
 */
data class CacheStats(
    val size: Int,
    val maxSize: Int,
    val hitCount: Long,
    val missCount: Long,
    val putCount: Long,
    val evictionCount: Long,
    val totalAccesses: Long,
    val expiredCount: Int
) {
    val hitRate: Double
        get() = if (hitCount + missCount > 0) {
            hitCount.toDouble() / (hitCount + missCount)
        } else 0.0

    val averageAccessCount: Double
        get() = if (size > 0) totalAccesses.toDouble() / size else 0.0
}
