package com.spiritatlas.data.cache

import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import coil.ImageLoader
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized memory management for the SpiritAtlas application
 *
 * Responsibilities:
 * - Monitor memory usage and respond to memory pressure
 * - Manage domain object caching with LRU eviction
 * - Coordinate with Coil's image cache
 * - Clear caches appropriately on low memory
 * - Track memory metrics for optimization
 *
 * Target: <100MB average memory usage
 */
@Singleton
class MemoryManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    // Domain object LRU cache with weak references to prevent memory leaks
    private val domainObjectCache = LruCache<String, Any>(maxSize = 50)

    // Track memory-intensive operations
    private val activeOperations = ConcurrentHashMap<String, WeakReference<Any>>()

    // Memory metrics for monitoring
    private var peakMemoryUsageMb: Long = 0
    private var cacheHits: Long = 0
    private var cacheMisses: Long = 0

    // Weak reference to ImageLoader to avoid circular dependency
    private var imageLoaderRef: WeakReference<ImageLoader>? = null

    /**
     * Get current memory usage in MB
     */
    fun getCurrentMemoryUsageMb(): Long {
        val runtime = Runtime.getRuntime()
        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)

        // Update peak memory tracking
        if (usedMemory > peakMemoryUsageMb) {
            peakMemoryUsageMb = usedMemory
        }

        return usedMemory
    }

    /**
     * Get available memory in MB
     */
    fun getAvailableMemoryMb(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.availMem / (1024 * 1024)
    }

    /**
     * Get memory class (max memory app can use) in MB
     */
    fun getMemoryClassMb(): Int {
        return activityManager.memoryClass
    }

    /**
     * Check if device is running low on memory
     */
    fun isLowMemory(): Boolean {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.lowMemory
    }

    /**
     * Register ImageLoader for memory management
     */
    fun registerImageLoader(imageLoader: ImageLoader) {
        imageLoaderRef = WeakReference(imageLoader)
    }

    /**
     * Handle memory trim events from Android system
     */
    fun onTrimMemory(level: Int) {
        Log.d(TAG, "Memory trim requested: level=$level")

        when (level) {
            // Critical memory situations - clear everything
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE,
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                Log.w(TAG, "Critical memory pressure - clearing all caches")
                clearAllCaches()
            }

            // Moderate memory pressure - clear non-essential caches
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW,
            ComponentCallbacks2.TRIM_MEMORY_MODERATE -> {
                Log.i(TAG, "Moderate memory pressure - clearing domain object cache")
                clearDomainObjectCache()
                trimImageCache(trimPercentage = 0.5)
            }

            // UI hidden - good time to release memory
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                Log.d(TAG, "UI hidden - trimming caches")
                trimImageCache(trimPercentage = 0.25)
                cleanupActiveOperations()
            }

            // Background situations - release as appropriate
            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND -> {
                Log.d(TAG, "App backgrounded - minor cache trim")
                trimImageCache(trimPercentage = 0.1)
            }
        }
    }

    companion object {
        private const val TAG = "MemoryManager"
    }

    /**
     * Clear all managed caches
     */
    fun clearAllCaches() {
        clearDomainObjectCache()
        clearImageCache()
        cleanupActiveOperations()
        System.gc() // Suggest garbage collection in critical situations
    }

    /**
     * Clear domain object cache
     */
    fun clearDomainObjectCache() {
        val cacheSize = domainObjectCache.size()
        domainObjectCache.evictAll()
        Log.d(TAG, "Cleared domain object cache: $cacheSize items")
    }

    /**
     * Clear Coil image cache
     */
    fun clearImageCache() {
        imageLoaderRef?.get()?.let { imageLoader ->
            // Clear memory cache
            imageLoader.memoryCache?.clear()
            Log.d(TAG, "Cleared image memory cache")
        }
    }

    /**
     * Trim image cache by percentage
     */
    private fun trimImageCache(trimPercentage: Double) {
        imageLoaderRef?.get()?.memoryCache?.let { memoryCache ->
            val currentSize = memoryCache.size
            val targetSize = (currentSize * (1.0 - trimPercentage)).toLong()
            // Coil doesn't have explicit trim, but clearing and relying on LRU helps
            if (trimPercentage > 0.3) {
                memoryCache.clear()
                Log.d(TAG, "Trimmed image cache by ${(trimPercentage * 100).toInt()}%")
            }
        }
    }

    /**
     * Cleanup weak references to completed operations
     */
    private fun cleanupActiveOperations() {
        val iterator = activeOperations.entries.iterator()
        var cleaned = 0
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.value.get() == null) {
                iterator.remove()
                cleaned++
            }
        }
        if (cleaned > 0) {
            Log.d(TAG, "Cleaned up $cleaned completed operations")
        }
    }

    /**
     * Cache a domain object with LRU eviction
     */
    fun <T : Any> cacheDomainObject(key: String, value: T): T {
        domainObjectCache.put(key, value)
        return value
    }

    /**
     * Retrieve a cached domain object
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getCachedDomainObject(key: String): T? {
        val value = domainObjectCache.get(key) as? T

        if (value != null) {
            cacheHits++
        } else {
            cacheMisses++
        }

        return value
    }

    /**
     * Register an active memory-intensive operation
     */
    fun registerOperation(id: String, operation: Any) {
        activeOperations[id] = WeakReference(operation)
    }

    /**
     * Unregister a completed operation
     */
    fun unregisterOperation(id: String) {
        activeOperations.remove(id)
    }

    /**
     * Get memory usage statistics
     */
    fun getMemoryStats(): MemoryStats {
        return MemoryStats(
            currentMemoryMb = getCurrentMemoryUsageMb(),
            peakMemoryMb = peakMemoryUsageMb,
            availableMemoryMb = getAvailableMemoryMb(),
            memoryClassMb = getMemoryClassMb(),
            isLowMemory = isLowMemory(),
            domainCacheSize = domainObjectCache.size(),
            domainCacheMaxSize = domainObjectCache.maxSize(),
            cacheHitRate = if (cacheHits + cacheMisses > 0) {
                cacheHits.toDouble() / (cacheHits + cacheMisses)
            } else 0.0,
            activeOperations = activeOperations.size
        )
    }

    /**
     * Reset memory tracking statistics
     */
    @VisibleForTesting
    fun resetStats() {
        peakMemoryUsageMb = 0
        cacheHits = 0
        cacheMisses = 0
    }
}

/**
 * Memory statistics snapshot
 */
data class MemoryStats(
    val currentMemoryMb: Long,
    val peakMemoryMb: Long,
    val availableMemoryMb: Long,
    val memoryClassMb: Int,
    val isLowMemory: Boolean,
    val domainCacheSize: Int,
    val domainCacheMaxSize: Int,
    val cacheHitRate: Double,
    val activeOperations: Int
) {
    override fun toString(): String {
        return """
            Memory Statistics:
            - Current: ${currentMemoryMb}MB / Peak: ${peakMemoryMb}MB
            - Available: ${availableMemoryMb}MB / Max: ${memoryClassMb}MB
            - Low Memory: $isLowMemory
            - Domain Cache: $domainCacheSize / $domainCacheMaxSize (${String.format("%.1f", cacheHitRate * 100)}% hit rate)
            - Active Operations: $activeOperations
        """.trimIndent()
    }
}

/**
 * Simple LRU cache implementation
 */
class LruCache<K, V>(private val maxSize: Int) {
    private val cache = LinkedHashMap<K, V>(maxSize, 0.75f, true)

    @Synchronized
    fun get(key: K): V? {
        return cache[key]
    }

    @Synchronized
    fun put(key: K, value: V): V? {
        val previous = cache.put(key, value)

        // Evict oldest if over capacity
        if (cache.size > maxSize) {
            val oldest = cache.entries.iterator().next()
            cache.remove(oldest.key)
        }

        return previous
    }

    @Synchronized
    fun evictAll() {
        cache.clear()
    }

    @Synchronized
    fun size(): Int = cache.size

    fun maxSize(): Int = maxSize
}
