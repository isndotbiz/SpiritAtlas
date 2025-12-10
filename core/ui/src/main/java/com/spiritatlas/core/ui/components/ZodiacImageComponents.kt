package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Zodiac sign image component.
 * Replaces Canvas-drawn zodiac icons with high-quality FLUX images.
 *
 * @param zodiacResourceId The drawable resource ID from app module
 */
@Composable
fun ZodiacImage(
    zodiacResourceId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    alpha: Float = 1f,
    contentDescription: String = "Zodiac sign"
) {
    Image(
        painter = painterResource(zodiacResourceId),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        alpha = alpha
    )
}

/**
 * Moon phase image component.
 * Replaces Canvas-drawn moon phase icons with high-quality FLUX images.
 *
 * @param moonPhaseResourceId The drawable resource ID from app module
 */
@Composable
fun MoonPhaseImage(
    moonPhaseResourceId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    alpha: Float = 1f,
    contentDescription: String = "Moon phase"
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(moonPhaseResourceId),
            contentDescription = contentDescription,
            modifier = Modifier.size(size),
            alpha = alpha
        )
    }
}

/**
 * Element symbol image component.
 *
 * @param elementResourceId The drawable resource ID from app module
 */
@Composable
fun ElementImage(
    elementResourceId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    alpha: Float = 1f,
    contentDescription: String = "Element"
) {
    Image(
        painter = painterResource(elementResourceId),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        alpha = alpha
    )
}

/**
 * Spiritual symbol image component.
 *
 * @param symbolResourceId The drawable resource ID from app module
 */
@Composable
fun SpiritualSymbolImage(
    symbolResourceId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    alpha: Float = 1f,
    contentDescription: String = "Spiritual symbol"
) {
    Image(
        painter = painterResource(symbolResourceId),
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        alpha = alpha
    )
}
