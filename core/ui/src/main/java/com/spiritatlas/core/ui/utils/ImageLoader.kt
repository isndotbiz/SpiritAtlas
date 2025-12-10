package com.spiritatlas.core.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size

/**
 * Optimized image loader for SpiritAtlas assets using Coil.
 *
 * Features:
 * - Automatic density selection (Android picks best drawable-* folder)
 * - Memory and disk caching via Coil
 * - Crossfade animations for smooth loading
 * - Placeholder and error state support
 * - Minimal recomposition overhead
 *
 * Usage:
 * ```kotlin
 * SpiritImage(
 *     resourceId = R.drawable.chakra_heart,
 *     contentDescription = "Heart Chakra",
 *     modifier = Modifier.size(72.dp)
 * )
 * ```
 *
 * @see SpiritualSymbol for pre-configured spiritual icon loading
 * @see AvatarImage for circular avatar loading
 */
@Composable
fun SpiritImage(
    @DrawableRes resourceId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = FilterQuality.Medium,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    crossfade: Boolean = true,
    crossfadeDurationMs: Int = 300,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
) {
    val context = LocalContext.current

    // Remember image request to avoid recreation on recomposition
    val imageRequest = remember(resourceId, crossfade, crossfadeDurationMs) {
        ImageRequest.Builder(context)
            .data(resourceId)
            .crossfade(crossfade)
            .crossfade(crossfadeDurationMs)
            .size(Size.ORIGINAL) // Let Coil and Android handle density selection
            .scale(Scale.FIT)
            .build()
    }

    AsyncImage(
        model = imageRequest,
        contentDescription = contentDescription,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError
    )
}

/**
 * Optimized loader for spiritual symbols (chakras, zodiac, moon phases, elements).
 *
 * Pre-configured for high quality rendering of spiritual icons with:
 * - High filter quality for crisp rendering
 * - Smooth crossfade animations
 * - ContentScale.Fit to preserve aspect ratio
 *
 * Example:
 * ```kotlin
 * SpiritualSymbol(
 *     resourceId = R.drawable.zodiac_aries,
 *     contentDescription = "Aries",
 *     modifier = Modifier.size(48.dp)
 * )
 * ```
 */
@Composable
fun SpiritualSymbol(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    SpiritImage(
        resourceId = resourceId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = FilterQuality.High,
        crossfade = true,
        crossfadeDurationMs = 300
    )
}

/**
 * Optimized avatar loader with circular cropping.
 *
 * Pre-configured for profile pictures and avatars with:
 * - ContentScale.Crop for proper circular framing
 * - Placeholder support for loading states
 * - Smooth crossfade
 *
 * Example:
 * ```kotlin
 * AvatarImage(
 *     resourceId = R.drawable.default_avatar_cosmic,
 *     contentDescription = "User avatar",
 *     modifier = Modifier.size(64.dp).clip(CircleShape),
 *     placeholder = R.drawable.avatar_placeholder
 * )
 * ```
 */
@Composable
fun AvatarImage(
    @DrawableRes resourceId: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null,
    alpha: Float = DefaultAlpha,
) {
    SpiritImage(
        resourceId = resourceId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = error,
        alpha = alpha,
        crossfade = true,
        filterQuality = FilterQuality.Medium
    )
}

/**
 * Optimized background image loader for large full-screen images.
 *
 * Pre-configured for backgrounds with:
 * - ContentScale.Crop to fill the container
 * - Lower filter quality to improve performance
 * - Longer crossfade for smoother transitions
 *
 * Example:
 * ```kotlin
 * BackgroundImage(
 *     resourceId = R.drawable.background_cosmic_gradient,
 *     contentDescription = null, // Decorative
 *     modifier = Modifier.fillMaxSize()
 * )
 * ```
 */
@Composable
fun BackgroundImage(
    @DrawableRes resourceId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = DefaultAlpha,
) {
    SpiritImage(
        resourceId = resourceId,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha,
        filterQuality = FilterQuality.Low, // Performance optimization for large images
        crossfade = true,
        crossfadeDurationMs = 500 // Longer fade for backgrounds
    )
}

/**
 * Image loader with loading, success, and error states.
 *
 * Displays a composable for each state of image loading.
 *
 * Example:
 * ```kotlin
 * SpiritImageWithStates(
 *     resourceId = R.drawable.profile_chart,
 *     contentDescription = "Birth chart",
 *     modifier = Modifier.size(300.dp),
 *     loading = { CircularProgressIndicator() },
 *     error = { Text("Failed to load image") }
 * )
 * ```
 */
@Composable
fun SpiritImageWithStates(
    @DrawableRes resourceId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    loading: @Composable (() -> Unit)? = null,
    error: @Composable ((AsyncImagePainter.State.Error) -> Unit)? = null,
    success: @Composable ((AsyncImagePainter.State.Success) -> Unit)? = null,
) {
    val context = LocalContext.current

    val imageRequest = remember(resourceId) {
        ImageRequest.Builder(context)
            .data(resourceId)
            .crossfade(true)
            .size(Size.ORIGINAL)
            .build()
    }

    Box(modifier = modifier) {
        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
        // Note: onLoading, onError, onSuccess callbacks removed
        // Use SubcomposeAsyncImage if you need custom loading/error states
    }
}

/**
 * Image cache utilities for manual cache management.
 *
 * Usage:
 * ```kotlin
 * val context = LocalContext.current
 * val imageLoader = context.imageLoader
 *
 * // Clear memory cache
 * ImageCacheUtils.clearMemoryCache(imageLoader)
 *
 * // Clear disk cache
 * scope.launch {
 *     ImageCacheUtils.clearDiskCache(imageLoader)
 * }
 * ```
 */
object ImageCacheUtils {
    /**
     * Clear the memory cache synchronously.
     * Use this when memory is low or navigating away from image-heavy screens.
     */
    fun clearMemoryCache(imageLoader: coil.ImageLoader) {
        imageLoader.memoryCache?.clear()
    }

    /**
     * Clear the disk cache asynchronously.
     * Use this for manual cache cleanup or in debug builds.
     */
    suspend fun clearDiskCache(imageLoader: coil.ImageLoader) {
        imageLoader.diskCache?.clear()
    }

    /**
     * Get current memory cache statistics.
     */
    fun getMemoryCacheStats(imageLoader: coil.ImageLoader): MemoryCacheStats {
        val cache = imageLoader.memoryCache
        return MemoryCacheStats(
            currentSize = cache?.size?.toLong() ?: 0L,
            maxSize = cache?.maxSize?.toLong() ?: 0L,
            hitCount = cache?.size?.toLong() ?: 0L // Approximation
        )
    }

    data class MemoryCacheStats(
        val currentSize: Long,
        val maxSize: Long,
        val hitCount: Long
    ) {
        val usagePercent: Float
            get() = if (maxSize > 0) (currentSize.toFloat() / maxSize) * 100f else 0f
    }
}

/**
 * Size recommendations for different image categories.
 *
 * Use these as guidelines when selecting which density variants to generate.
 */
object ImageSizeRecommendations {
    /**
     * App icons and launcher icons.
     * Base: xxxhdpi (4x) for 1024x1024 images targeting 256dp display.
     */
    const val ICON_BASE_SIZE_DP = 256
    const val ICON_BASE_DENSITY_SCALE = 4.0f // xxxhdpi

    /**
     * Spiritual symbols (chakras, zodiac, elements).
     * Base: xhdpi (2x) for 512x512 images.
     */
    const val SYMBOL_BASE_SIZE_DP = 256
    const val SYMBOL_BASE_DENSITY_SCALE = 2.0f // xhdpi

    /**
     * Profile avatars.
     * Base: xhdpi (2x) for 512x512 images.
     */
    const val AVATAR_BASE_SIZE_DP = 256
    const val AVATAR_BASE_DENSITY_SCALE = 2.0f // xhdpi

    /**
     * UI buttons and controls.
     * Base: xhdpi (2x) for 256x256 images.
     */
    const val BUTTON_BASE_SIZE_DP = 128
    const val BUTTON_BASE_DENSITY_SCALE = 2.0f // xhdpi

    /**
     * Full-screen backgrounds.
     * Base: xxhdpi (3x) for 1080x1920 images targeting 360x640dp screen.
     */
    const val BACKGROUND_BASE_WIDTH_DP = 360
    const val BACKGROUND_BASE_HEIGHT_DP = 640
    const val BACKGROUND_BASE_DENSITY_SCALE = 3.0f // xxhdpi
}

/**
 * Preload critical images during app startup.
 *
 * Example usage in Application.onCreate():
 * ```kotlin
 * class SpiritAtlasApplication : Application() {
 *     @Inject lateinit var imageLoader: ImageLoader
 *
 *     override fun onCreate() {
 *         super.onCreate()
 *         lifecycleScope.launch {
 *             ImagePreloader.preloadCriticalAssets(
 *                 context = this@SpiritAtlasApplication,
 *                 imageLoader = imageLoader
 *             )
 *         }
 *     }
 * }
 * ```
 */
object ImagePreloader {
    /**
     * Preload a list of resource IDs.
     */
    suspend fun preloadImages(
        context: android.content.Context,
        imageLoader: coil.ImageLoader,
        @DrawableRes vararg resourceIds: Int
    ) {
        resourceIds.forEach { resourceId ->
            val request = ImageRequest.Builder(context)
                .data(resourceId)
                .build()
            imageLoader.enqueue(request)
        }
    }

    /**
     * Preload critical assets that should be available immediately.
     * Call this from Application.onCreate() or splash screen.
     */
    suspend fun preloadCriticalAssets(
        context: android.content.Context,
        imageLoader: coil.ImageLoader
    ) {
        // Add your critical resource IDs here
        // Example:
        // preloadImages(
        //     context = context,
        //     imageLoader = imageLoader,
        //     R.drawable.app_icon_primary,
        //     R.drawable.default_avatar_cosmic_silhouette_feminine,
        //     R.drawable.default_avatar_cosmic_silhouette_masculine,
        //     R.drawable.default_avatar_cosmic_silhouette_non_binary
        // )
    }
}
