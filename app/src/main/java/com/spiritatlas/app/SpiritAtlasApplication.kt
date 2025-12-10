package com.spiritatlas.app

import android.app.Application
import android.content.ComponentCallbacks2
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.spiritatlas.app.BuildConfig
import com.spiritatlas.data.cache.MemoryManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class SpiritAtlasApplication : Application(), Configuration.Provider, ImageLoaderFactory {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var memoryManager: MemoryManager

    private lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()

        // Initialize Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.d("SpiritAtlas Application initialized")

        // Log initial memory state
        if (BuildConfig.DEBUG) {
            val stats = memoryManager.getMemoryStats()
            Timber.d("Initial memory state:\n$stats")
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        // Delegate memory trimming to MemoryManager
        memoryManager.onTrimMemory(level)

        // Log memory state after trim
        if (BuildConfig.DEBUG) {
            val stats = memoryManager.getMemoryStats()
            Timber.d("Memory state after trim (level=$level):\n$stats")
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()

        Timber.w("System low memory warning - clearing all caches")
        memoryManager.clearAllCaches()

        // Log warning
        val stats = memoryManager.getMemoryStats()
        Timber.w("Critical low memory state:\n$stats")
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun newImageLoader(): ImageLoader {
        // Adjust memory cache based on device memory
        val memoryClassMb = memoryManager.getMemoryClassMb()
        val memoryPercent = when {
            memoryClassMb < 128 -> 0.15  // Low-end devices: 15%
            memoryClassMb < 256 -> 0.20  // Mid-range devices: 20%
            else -> 0.25                  // High-end devices: 25%
        }

        imageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    // Adaptive memory cache based on device capabilities
                    .maxSizePercent(memoryPercent)
                    // Weak references for better GC
                    .weakReferencesEnabled(true)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    // 250MB disk cache
                    .maxSizeBytes(250L * 1024 * 1024)
                    .build()
            }
            // Enable aggressive caching for better performance
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            // Preload images in parallel for faster loading
            .crossfade(true)
            .crossfade(300)
            // Enable debug logging in debug builds
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                    Timber.d("Image cache configured: ${(memoryPercent * 100).toInt()}% of available memory")
                }
            }
            .build()

        // Register with MemoryManager for coordinated memory management
        memoryManager.registerImageLoader(imageLoader)

        return imageLoader
    }
}


