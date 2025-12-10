package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Spiritual background image with proper loading and caching.
 * Replaces Canvas-based backgrounds for higher visual quality.
 *
 * @param backgroundResourceId The drawable resource ID from app module
 */
@Composable
fun SpiritualBackgroundImage(
    backgroundResourceId: Int,
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image using Coil for caching
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(backgroundResourceId)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha
        )

        // Content on top
        content()
    }
}

/**
 * Simple background image using painterResource (faster for frequently used images).
 *
 * @param backgroundResourceId The drawable resource ID from app module
 */
@Composable
fun SimpleSpiritualBackground(
    backgroundResourceId: Int,
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(backgroundResourceId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha
        )

        // Content on top
        content()
    }
}

/**
 * Background with dimming effect for better text readability.
 *
 * @param backgroundResourceId The drawable resource ID from app module
 */
@Composable
fun DimmedSpiritualBackground(
    backgroundResourceId: Int,
    modifier: Modifier = Modifier,
    alpha: Float = 0.2f,
    dimAmount: Float = 0.5f,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image with color matrix for dimming
        val colorMatrix = ColorMatrix().apply {
            setToScale(dimAmount, dimAmount, dimAmount, 1f)
        }

        Image(
            painter = painterResource(backgroundResourceId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = ColorFilter.colorMatrix(colorMatrix)
        )

        // Content on top
        content()
    }
}
