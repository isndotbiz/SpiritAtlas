package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest

/**
 * Progressive Image Loading with LQIP (Low-Quality Image Placeholder)
 *
 * This component implements the blur-up loading technique:
 * 1. Shows tiny low-quality placeholder immediately (from drawable-lqip)
 * 2. Blurs the placeholder
 * 3. Loads full-quality image in background
 * 4. Crossfades to full image when ready
 *
 * Benefits:
 * - Instant visual feedback (LQIP is <1KB)
 * - Perceived performance improvement
 * - Smooth loading experience
 * - Bandwidth efficient
 *
 * @param imageResourceId Full-quality image resource ID
 * @param lqipResourceId Low-quality placeholder resource ID (from drawable-lqip)
 * @param contentDescription Accessibility description
 * @param modifier Modifier for layout
 * @param contentScale How to scale the image
 * @param alpha Image transparency
 */
@Composable
fun ProgressiveImage(
    imageResourceId: Int,
    lqipResourceId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    // Animate blur removal
    val blurRadius by animateFloatAsState(
        targetValue = if (isLoading) 20f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "blur_animation"
    )

    // Animate opacity
    val imageAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0f else alpha,
        animationSpec = tween(durationMillis = 400),
        label = "alpha_animation"
    )

    val lqipAlpha by animateFloatAsState(
        targetValue = if (isLoading) alpha else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "lqip_alpha_animation"
    )

    Box(modifier = modifier) {
        // LQIP - shown immediately, blurred
        Image(
            painter = painterResource(lqipResourceId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = blurRadius.dp),
            contentScale = contentScale,
            alpha = lqipAlpha
        )

        // Full-quality image - loaded asynchronously
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageResourceId)
                .crossfade(300)
                .build(),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = imageAlpha,
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
                isError = state is AsyncImagePainter.State.Error
            }
        )
    }
}

/**
 * Progressive Background Image with LQIP
 *
 * Similar to ProgressiveImage but designed for backgrounds with dimming.
 *
 * @param backgroundResourceId Full-quality background resource ID
 * @param lqipResourceId Low-quality placeholder resource ID
 * @param modifier Modifier for layout
 * @param alpha Background transparency
 * @param dimAmount Amount of dimming (0-1)
 * @param contentScale How to scale the image
 * @param content Content to overlay on background
 */
@Composable
fun ProgressiveBackgroundImage(
    backgroundResourceId: Int,
    lqipResourceId: Int,
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    dimAmount: Float = 1.0f,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    val blurRadius by animateFloatAsState(
        targetValue = if (isLoading) 15f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "bg_blur_animation"
    )

    val imageAlpha by animateFloatAsState(
        targetValue = if (isLoading) 0f else alpha,
        animationSpec = tween(durationMillis = 400),
        label = "bg_alpha_animation"
    )

    val lqipAlpha by animateFloatAsState(
        targetValue = if (isLoading) alpha else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "bg_lqip_alpha"
    )

    // Color matrix for dimming
    val colorMatrix = ColorMatrix().apply {
        setToScale(dimAmount, dimAmount, dimAmount, 1f)
    }

    Box(modifier = modifier.fillMaxSize()) {
        // LQIP background
        Image(
            painter = painterResource(lqipResourceId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(radius = blurRadius.dp),
            contentScale = contentScale,
            alpha = lqipAlpha,
            colorFilter = ColorFilter.colorMatrix(colorMatrix)
        )

        // Full-quality background
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backgroundResourceId)
                .crossfade(300)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = imageAlpha,
            colorFilter = ColorFilter.colorMatrix(colorMatrix),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            }
        )

        // Content on top
        content()
    }
}

/**
 * Fast Image Loader - no LQIP, direct load with fade
 *
 * Use for small images or when LQIP overhead isn't needed.
 *
 * @param imageResourceId Image resource ID
 * @param contentDescription Accessibility description
 * @param modifier Modifier for layout
 * @param contentScale How to scale the image
 * @param alpha Image transparency
 */
@Composable
fun FastImage(
    imageResourceId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1f
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageResourceId)
            .crossfade(200)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        alpha = alpha
    )
}

/**
 * Lazy Image - only loads when visible
 *
 * Combine with LazyColumn/LazyRow for efficient scrolling.
 *
 * @param imageResourceId Image resource ID
 * @param contentDescription Accessibility description
 * @param modifier Modifier for layout
 * @param contentScale How to scale the image
 */
@Composable
fun LazyLoadImage(
    imageResourceId: Int,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Coil automatically handles lazy loading when used in LazyColumn
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageResourceId)
            .crossfade(200)
            .memoryCacheKey(imageResourceId.toString())
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}
