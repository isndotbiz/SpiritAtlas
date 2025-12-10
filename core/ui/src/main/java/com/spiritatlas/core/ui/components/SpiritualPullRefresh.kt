package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.ChakraGradient
import com.spiritatlas.core.ui.theme.SpiritualPurple
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Spiritual pull-to-refresh indicator style
 */
enum class SpiritualRefreshStyle {
    /** Default Material indicator */
    DEFAULT,
    /** Spinning chakra indicator */
    CHAKRA,
    /** Mandala bloom indicator */
    MANDALA,
    /** Yin-Yang rotation indicator */
    YIN_YANG
}

/**
 * A spiritual-themed pull-to-refresh component
 * Wraps the standard Material pull-to-refresh with custom styling
 *
 * @param isRefreshing Whether the content is currently refreshing
 * @param onRefresh Callback when refresh is triggered
 * @param modifier Modifier for the component
 * @param style Style of the refresh indicator
 * @param content Content to display
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpiritualPullRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    style: SpiritualRefreshStyle = SpiritualRefreshStyle.CHAKRA,
    content: @Composable () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
    ) {
        content()

        when (style) {
            SpiritualRefreshStyle.DEFAULT -> {
                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    contentColor = SpiritualPurple,
                    scale = true
                )
            }
            SpiritualRefreshStyle.CHAKRA -> {
                ChakraRefreshIndicator(
                    isRefreshing = isRefreshing,
                    progress = pullRefreshState.progress,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            SpiritualRefreshStyle.MANDALA -> {
                MandalaRefreshIndicator(
                    isRefreshing = isRefreshing,
                    progress = pullRefreshState.progress,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
            SpiritualRefreshStyle.YIN_YANG -> {
                YinYangRefreshIndicator(
                    isRefreshing = isRefreshing,
                    progress = pullRefreshState.progress,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

/**
 * Spinning chakra refresh indicator
 * Shows chakra colors in a spinning pattern
 */
@Composable
private fun ChakraRefreshIndicator(
    isRefreshing: Boolean,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "chakraRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val size = 48.dp
    val offsetY by animateFloatAsState(
        targetValue = if (progress > 0f) 40f * progress.coerceAtMost(1f) else 0f,
        label = "offsetY"
    )

    Box(
        modifier = modifier
            .offset(y = offsetY.dp)
            .size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val centerX = this.size.width / 2
            val centerY = this.size.height / 2
            val radius = this.size.width / 3
            val chakraCount = 7

            rotate(degrees = if (isRefreshing) rotation else 0f) {
                ChakraGradient.forEachIndexed { index, color ->
                    val angle = (2 * PI / chakraCount * index).toFloat()
                    val distance = radius * progress.coerceAtMost(1f)

                    val x = centerX + distance * cos(angle)
                    val y = centerY + distance * sin(angle)

                    // Draw chakra dot
                    drawCircle(
                        color = color.copy(alpha = 0.8f),
                        radius = this@Canvas.size.width / 15,
                        center = Offset(x, y)
                    )

                    // Draw glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color.copy(alpha = 0.3f),
                                color.copy(alpha = 0f)
                            ),
                            center = Offset(x, y),
                            radius = this@Canvas.size.width / 10
                        ),
                        radius = this@Canvas.size.width / 10,
                        center = Offset(x, y)
                    )
                }

                // Draw center
                drawCircle(
                    brush = Brush.radialGradient(ChakraGradient),
                    radius = this@Canvas.size.width / 12 * progress.coerceAtMost(1f),
                    center = Offset(centerX, centerY)
                )
            }
        }
    }
}

/**
 * Mandala bloom refresh indicator
 * Blooms open as user pulls down
 */
@Composable
private fun MandalaRefreshIndicator(
    isRefreshing: Boolean,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mandalaRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val size = 48.dp
    val offsetY by animateFloatAsState(
        targetValue = if (progress > 0f) 40f * progress.coerceAtMost(1f) else 0f,
        label = "offsetY"
    )

    Box(
        modifier = modifier
            .offset(y = offsetY.dp)
            .size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val centerX = this.size.width / 2
            val centerY = this.size.height / 2
            val baseRadius = this.size.width / 6
            val petals = 8

            rotate(degrees = if (isRefreshing) rotation else 0f) {
                // Draw petals
                for (i in 0 until petals) {
                    val angle = (2 * PI / petals * i).toFloat()
                    val petalRadius = baseRadius * progress.coerceAtMost(1f)
                    val distance = baseRadius * (1 + progress.coerceAtMost(1f) * 0.5f)

                    val x = centerX + distance * cos(angle)
                    val y = centerY + distance * sin(angle)

                    val color = ChakraGradient[i % ChakraGradient.size]

                    // Draw petal
                    drawCircle(
                        color = color.copy(alpha = 0.6f),
                        radius = petalRadius,
                        center = Offset(x, y)
                    )

                    // Draw petal outline
                    drawCircle(
                        color = color,
                        radius = petalRadius,
                        center = Offset(x, y),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                // Draw center circle
                drawCircle(
                    brush = Brush.radialGradient(ChakraGradient),
                    radius = baseRadius * 0.8f * progress.coerceAtMost(1f),
                    center = Offset(centerX, centerY)
                )
            }
        }
    }
}

/**
 * Yin-Yang rotation refresh indicator
 * Classic balance symbol rotating
 */
@Composable
private fun YinYangRefreshIndicator(
    isRefreshing: Boolean,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "yinYangRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val size = 48.dp
    val offsetY by animateFloatAsState(
        targetValue = if (progress > 0f) 40f * progress.coerceAtMost(1f) else 0f,
        label = "offsetY"
    )

    Box(
        modifier = modifier
            .offset(y = offsetY.dp)
            .size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val centerX = this.size.width / 2
            val centerY = this.size.height / 2
            val radius = this.size.width / 2.5f * progress.coerceAtMost(1f)

            rotate(degrees = if (isRefreshing) rotation else 0f, pivot = Offset(centerX, centerY)) {
                // Draw white half
                drawArc(
                    color = Color.White,
                    startAngle = -90f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )

                // Draw black half
                drawArc(
                    color = SpiritualPurple,
                    startAngle = 90f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )

                // Draw white circle in purple half
                drawCircle(
                    color = Color.White,
                    radius = radius / 5,
                    center = Offset(centerX, centerY - radius / 2)
                )

                // Draw purple circle in white half
                drawCircle(
                    color = SpiritualPurple,
                    radius = radius / 5,
                    center = Offset(centerX, centerY + radius / 2)
                )

                // Draw white dot
                drawCircle(
                    color = SpiritualPurple,
                    radius = radius / 10,
                    center = Offset(centerX, centerY - radius / 2)
                )

                // Draw purple dot
                drawCircle(
                    color = Color.White,
                    radius = radius / 10,
                    center = Offset(centerX, centerY + radius / 2)
                )

                // Draw outer circle
                drawCircle(
                    color = SpiritualPurple,
                    radius = radius,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}
