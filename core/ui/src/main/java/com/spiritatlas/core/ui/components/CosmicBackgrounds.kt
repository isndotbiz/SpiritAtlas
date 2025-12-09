package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.spiritatlas.core.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Professional cosmic background components for SpiritAtlas screens
 * Uses only Canvas API for optimal performance - no raster images
 */

// ============================================================================
// 1. STARFIELD BACKGROUND - For Home Screen
// ============================================================================

/**
 * Animated starfield with celestial bodies
 * Subtle, professional design with twinkling stars
 */
@Composable
fun StarfieldBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Create stable star positions
    val stars = remember {
        List(120) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3f + 1f,
                twinkleSpeed = Random.nextFloat() * 2f + 1f,
                twinkleOffset = Random.nextFloat() * 360f
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "starfield")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Base gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Deep space gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NightSky,
                        CosmicViolet.copy(alpha = 0.6f),
                        NightSky.copy(alpha = 0.8f)
                    )
                )
            )

            // Draw stars with twinkling effect
            stars.forEach { star ->
                val x = size.width * star.x
                val y = size.height * star.y
                val twinkle = (cos(Math.toRadians((time * star.twinkleSpeed + star.twinkleOffset).toDouble())) + 1f) / 2f
                val alpha = 0.3f + (twinkle * 0.7f)

                // Star glow
                drawCircle(
                    color = Color.White.copy(alpha = alpha * 0.2f),
                    radius = star.size * 2f,
                    center = Offset(x, y)
                )

                // Star core
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = star.size,
                    center = Offset(x, y)
                )
            }

            // Subtle nebula effects
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.15f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.3f, size.height * 0.2f),
                    radius = size.width * 0.4f
                ),
                radius = size.width * 0.4f,
                center = Offset(size.width * 0.3f, size.height * 0.2f)
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        StardustBlue.copy(alpha = 0.1f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.7f, size.height * 0.6f),
                    radius = size.width * 0.35f
                ),
                radius = size.width * 0.35f,
                center = Offset(size.width * 0.7f, size.height * 0.6f)
            )
        }

        // Content
        content()
    }
}

private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val twinkleSpeed: Float,
    val twinkleOffset: Float
)

// ============================================================================
// 2. SACRED GEOMETRY BACKGROUND - For Profile Library Screen
// ============================================================================

/**
 * Sacred geometry patterns with subtle rotation
 * Flower of Life and Metatron's Cube inspired
 */
@Composable
fun SacredGeometryBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sacred_geometry")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Base gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Spiritual gradient background
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SpiritualPurple.copy(alpha = 0.05f),
                        MysticViolet.copy(alpha = 0.08f),
                        AuraGold.copy(alpha = 0.03f)
                    )
                )
            )

            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw sacred geometry patterns
            rotate(rotation, pivot = Offset(centerX, centerY)) {
                // Flower of Life inspired pattern
                drawFlowerOfLife(
                    center = Offset(centerX, centerY),
                    radius = size.minDimension * 0.25f,
                    color = SpiritualPurple.copy(alpha = pulseAlpha)
                )

                // Outer circles
                val outerRadius = size.minDimension * 0.4f
                for (i in 0 until 6) {
                    val angle = (i * 60f - 90f) * PI / 180f
                    val x = centerX + outerRadius * cos(angle).toFloat()
                    val y = centerY + outerRadius * sin(angle).toFloat()

                    drawCircle(
                        color = MysticViolet.copy(alpha = pulseAlpha * 0.5f),
                        radius = size.minDimension * 0.08f,
                        center = Offset(x, y),
                        style = Stroke(width = 1f)
                    )
                }
            }

            // Corner sacred symbols
            drawSacredSymbol(
                center = Offset(size.width * 0.15f, size.height * 0.15f),
                radius = size.minDimension * 0.08f,
                alpha = pulseAlpha * 0.6f
            )

            drawSacredSymbol(
                center = Offset(size.width * 0.85f, size.height * 0.85f),
                radius = size.minDimension * 0.08f,
                alpha = pulseAlpha * 0.6f
            )
        }

        // Content
        content()
    }
}

private fun DrawScope.drawFlowerOfLife(
    center: Offset,
    radius: Float,
    color: Color
) {
    // Center circle
    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = 2f)
    )

    // Six surrounding circles
    for (i in 0 until 6) {
        val angle = (i * 60f - 90f) * PI / 180f
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()

        drawCircle(
            color = color,
            radius = radius,
            center = Offset(x, y),
            style = Stroke(width = 2f)
        )
    }
}

private fun DrawScope.drawSacredSymbol(
    center: Offset,
    radius: Float,
    alpha: Float
) {
    // Draw a simple sacred geometry symbol (vesica piscis)
    for (i in 0 until 3) {
        val angle = (i * 120f) * PI / 180f
        val x = center.x + radius * 0.5f * cos(angle).toFloat()
        val y = center.y + radius * 0.5f * sin(angle).toFloat()

        drawCircle(
            color = AuraGold.copy(alpha = alpha),
            radius = radius * 0.6f,
            center = Offset(x, y),
            style = Stroke(width = 1f)
        )
    }
}

// ============================================================================
// 3. COSMIC CONNECTION BACKGROUND - For Compatibility Screen
// ============================================================================

/**
 * Dual-tone energy visualization with flowing connections
 * Represents the merging of two energies
 */
@Composable
fun CosmicConnectionBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cosmic_connection")

    val flow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flow"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Create stable energy particles
    val particles = remember {
        List(30) {
            EnergyParticle(
                startX = if (it % 2 == 0) 0.2f else 0.8f,
                y = Random.nextFloat(),
                speed = Random.nextFloat() * 0.3f + 0.1f,
                size = Random.nextFloat() * 4f + 2f,
                color = if (it % 2 == 0) TantricRose else SpiritualPurple
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Dual gradient background
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        TantricRose.copy(alpha = 0.08f),
                        Color.Transparent,
                        SpiritualPurple.copy(alpha = 0.08f)
                    )
                )
            )

            // Center connection area
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Draw flowing energy lines
            for (i in 0 until 5) {
                val offset = (flow + i * 0.2f) % 1f
                val y = centerY + (i - 2) * size.height * 0.1f

                val path = Path().apply {
                    moveTo(0f, y)

                    val controlPoints = 4
                    for (j in 0..controlPoints) {
                        val x = (j.toFloat() / controlPoints) * size.width
                        val waveY = y + sin((offset + j * 0.25f) * 2 * PI).toFloat() * 30f

                        if (j == 0) {
                            lineTo(x, waveY)
                        } else {
                            val prevX = ((j - 1).toFloat() / controlPoints) * size.width
                            val prevWaveY = y + sin((offset + (j - 1) * 0.25f) * 2 * PI).toFloat() * 30f
                            val cpX = (prevX + x) / 2
                            quadraticTo(cpX, prevWaveY, x, waveY)
                        }
                    }
                }

                drawPath(
                    path = path,
                    color = AuraGold.copy(alpha = 0.1f * pulse),
                    style = Stroke(width = 2f, cap = StrokeCap.Round)
                )
            }

            // Draw energy particles
            particles.forEach { particle ->
                val particleX = if (particle.startX < 0.5f) {
                    // Moving from left to right
                    size.width * (particle.startX + flow * particle.speed) % size.width
                } else {
                    // Moving from right to left
                    size.width * (particle.startX - flow * particle.speed) % size.width
                }
                val particleY = size.height * particle.y

                // Particle glow
                drawCircle(
                    color = particle.color.copy(alpha = 0.3f * pulse),
                    radius = particle.size * 2f,
                    center = Offset(particleX, particleY)
                )

                // Particle core
                drawCircle(
                    color = particle.color.copy(alpha = 0.6f * pulse),
                    radius = particle.size,
                    center = Offset(particleX, particleY)
                )
            }

            // Central energy convergence
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.1f * pulse),
                        TantricRose.copy(alpha = 0.15f * pulse),
                        SpiritualPurple.copy(alpha = 0.15f * pulse),
                        Color.Transparent
                    ),
                    center = Offset(centerX, centerY),
                    radius = size.minDimension * 0.4f
                ),
                radius = size.minDimension * 0.4f,
                center = Offset(centerX, centerY)
            )
        }

        // Content
        content()
    }
}

private data class EnergyParticle(
    val startX: Float,
    val y: Float,
    val speed: Float,
    val size: Float,
    val color: Color
)

// ============================================================================
// 4. ENHANCED SPIRITUAL GRADIENT BACKGROUND
// ============================================================================

/**
 * Enhanced version of SpiritualGradientBackground with optional cosmic overlay
 * Can be used as a drop-in replacement with additional visual effects
 */
@Composable
fun EnhancedSpiritualBackground(
    gradientType: SpiritualGradientType = SpiritualGradientType.MYSTIC,
    enableCosmicOverlay: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Base gradient
        val gradient = when (gradientType) {
            SpiritualGradientType.MYSTIC -> Brush.verticalGradient(
                colors = listOf(
                    NightSky.copy(alpha = 0.3f),
                    CosmicViolet.copy(alpha = 0.15f),
                    SpiritualPurple.copy(alpha = 0.1f)
                )
            )
            SpiritualGradientType.SUNSET -> Brush.verticalGradient(
                colors = listOf(
                    TantricRose.copy(alpha = 0.1f),
                    AuraGold.copy(alpha = 0.08f),
                    FireOrange.copy(alpha = 0.05f)
                )
            )
            SpiritualGradientType.OCEAN -> Brush.verticalGradient(
                colors = listOf(
                    CosmicBlue.copy(alpha = 0.1f),
                    WaterTeal.copy(alpha = 0.08f),
                    DeepTeal.copy(alpha = 0.05f)
                )
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(brush = gradient)
        }

        // Optional cosmic overlay
        if (enableCosmicOverlay) {
            val infiniteTransition = rememberInfiniteTransition(label = "cosmic_overlay")
            val shimmer by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "shimmer"
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                // Subtle shimmer effect
                for (i in 0 until 3) {
                    val offset = (shimmer + i * 0.33f) % 1f
                    val alpha = (1f - kotlin.math.abs(offset - 0.5f) * 2f) * 0.05f

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = alpha),
                                Color.Transparent
                            ),
                            center = Offset(
                                size.width * offset,
                                size.height * (0.3f + i * 0.2f)
                            ),
                            radius = size.width * 0.3f
                        ),
                        radius = size.width * 0.3f,
                        center = Offset(
                            size.width * offset,
                            size.height * (0.3f + i * 0.2f)
                        )
                    )
                }
            }
        }

        // Content
        content()
    }
}
