package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.*

/**
 * Comprehensive loading indicators for SpiritAtlas
 *
 * Features:
 * 1. Mandala Bloom - Expanding mandala from center
 * 2. Chakra Sequence - 7 chakras lighting up
 * 3. Constellation Connect - Stars connecting with lines
 * 4. Energy Flow - Flowing particles in circle
 * 5. Sacred Geometry - Rotating geometry patterns
 * 6. Yin Yang Spin - Smooth yin-yang rotation
 * 7. Moon Phase Cycle - Moon phases cycling
 *
 * All animations target 60 FPS performance.
 */

// ============================================================================
// MANDALA BLOOM LOADER
// ============================================================================

/**
 * Mandala Bloom: Mandala expanding from center with spiritual colors
 *
 * Perfect for: Profile generation, spiritual calculations
 */
@Composable
fun MandalaBloomLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    colors: List<Color> = listOf(
        SpiritualPurple,
        MysticViolet,
        AuroraPink,
        CosmicBlue
    ),
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mandalaBloom")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mandalaRotation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mandalaScale"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() / 2 * scale
            val petalCount = 8

            rotate(rotation, center) {
                // Draw outer petals
                for (i in 0 until petalCount) {
                    val angle = (i * 360f / petalCount) * (PI / 180f)
                    val color = colors[i % colors.size]

                    drawMandalaPattern(
                        center = center,
                        radius = radius,
                        angle = angle.toFloat(),
                        color = color,
                        alpha = 0.8f
                    )
                }

                // Draw center circle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = colors.map { it.copy(alpha = 0.7f) },
                        center = center,
                        radius = radius * 0.3f
                    ),
                    radius = radius * 0.3f,
                    center = center
                )
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun DrawScope.drawMandalaPattern(
    center: Offset,
    radius: Float,
    angle: Float,
    color: Color,
    alpha: Float
) {
    val petalPath = Path().apply {
        moveTo(center.x, center.y)

        val petalRadius = radius * 0.6f
        val petalX = center.x + petalRadius * cos(angle)
        val petalY = center.y + petalRadius * sin(angle)

        quadraticTo(
            petalX + petalRadius * 0.3f * cos(angle + PI.toFloat() / 2),
            petalY + petalRadius * 0.3f * sin(angle + PI.toFloat() / 2),
            center.x + radius * cos(angle),
            center.y + radius * sin(angle)
        )

        quadraticTo(
            petalX + petalRadius * 0.3f * cos(angle - PI.toFloat() / 2),
            petalY + petalRadius * 0.3f * sin(angle - PI.toFloat() / 2),
            center.x,
            center.y
        )

        close()
    }

    drawPath(
        path = petalPath,
        color = color.copy(alpha = alpha)
    )
}

// ============================================================================
// CHAKRA SEQUENCE LOADER
// ============================================================================

/**
 * Chakra Sequence: 7 chakras lighting up in sequence from root to crown
 *
 * Perfect for: Energy alignment, profile calculations
 */
@Composable
fun ChakraSequenceLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    message: String? = null
) {
    val chakraColors = remember {
        listOf(
            ChakraRed,    // Root
            ChakraOrange, // Sacral
            ChakraYellow, // Solar Plexus
            ChakraGreen,  // Heart
            ChakraBlue,   // Throat
            ChakraIndigo, // Third Eye
            ChakraCrown   // Crown
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "chakraSequence")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "chakraProgress"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val chakraRadius = size.toPx() * 0.08f
            val spacing = size.toPx() / 8

            chakraColors.forEachIndexed { index, color ->
                val y = center.y - (3 * spacing) + (index * spacing)
                val isActive = progress >= index && progress < index + 1
                val wasActive = progress >= index + 1

                val alpha = when {
                    isActive -> {
                        val localProgress = progress - index
                        0.3f + 0.7f * localProgress
                    }
                    wasActive -> 0.9f
                    else -> 0.3f
                }

                val currentRadius = when {
                    isActive -> {
                        val localProgress = progress - index
                        chakraRadius * (1f + 0.3f * sin(localProgress * PI.toFloat()))
                    }
                    wasActive -> chakraRadius
                    else -> chakraRadius * 0.8f
                }

                // Draw glow
                if (isActive) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                color.copy(alpha = 0.4f),
                                Color.Transparent
                            ),
                            center = Offset(center.x, y),
                            radius = currentRadius * 2
                        ),
                        radius = currentRadius * 2,
                        center = Offset(center.x, y)
                    )
                }

                // Draw chakra circle
                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = currentRadius,
                    center = Offset(center.x, y)
                )

                // Draw inner circle for contrast
                drawCircle(
                    color = Color.White.copy(alpha = alpha * 0.3f),
                    radius = currentRadius * 0.5f,
                    center = Offset(center.x, y)
                )
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// CONSTELLATION CONNECT LOADER
// ============================================================================

/**
 * Constellation Connect: Stars connecting with animated lines
 *
 * Perfect for: AI insights, compatibility calculations
 */
@Composable
fun ConstellationConnectLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    starCount: Int = 6,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "constellation")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "constellationProgress"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "constellationRotation"
    )

    val stars = remember {
        List(starCount) { i ->
            val angle = (i * 2 * PI / starCount).toFloat()
            val radius = 0.7f
            Offset(
                x = 0.5f + radius * cos(angle) * 0.5f,
                y = 0.5f + radius * sin(angle) * 0.5f
            )
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)

            rotate(rotation, center) {
                // Draw connecting lines
                val visibleConnections = (progress * starCount).toInt()
                for (i in 0 until visibleConnections) {
                    val start = Offset(
                        stars[i].x * size.toPx(),
                        stars[i].y * size.toPx()
                    )
                    val end = Offset(
                        stars[(i + 1) % starCount].x * size.toPx(),
                        stars[(i + 1) % starCount].y * size.toPx()
                    )

                    val lineProgress = if (i == visibleConnections - 1) {
                        (progress * starCount) - visibleConnections
                    } else {
                        1f
                    }

                    val actualEnd = Offset(
                        start.x + (end.x - start.x) * lineProgress,
                        start.y + (end.y - start.y) * lineProgress
                    )

                    drawLine(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                StardustBlue.copy(alpha = 0.8f),
                                SpiritualPurple.copy(alpha = 0.8f)
                            ),
                            start = start,
                            end = actualEnd
                        ),
                        start = start,
                        end = actualEnd,
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                // Draw stars
                stars.take((progress * starCount).toInt() + 1).forEachIndexed { index, star ->
                    val starPos = Offset(
                        star.x * size.toPx(),
                        star.y * size.toPx()
                    )

                    // Draw glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                AuraGold.copy(alpha = 0.6f),
                                Color.Transparent
                            ),
                            center = starPos,
                            radius = 12.dp.toPx()
                        ),
                        radius = 12.dp.toPx(),
                        center = starPos
                    )

                    // Draw star
                    drawCircle(
                        color = AuraGold,
                        radius = 4.dp.toPx(),
                        center = starPos
                    )
                }
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// ENERGY FLOW LOADER
// ============================================================================

/**
 * Energy Flow: Flowing particles in circular pattern
 *
 * Perfect for: Compatibility calculations between two profiles
 */
@Composable
fun EnergyFlowLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    particleCount: Int = 12,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "energyFlow")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "energyPhase"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() * 0.35f

            for (i in 0 until particleCount) {
                val particlePhase = phase + (i * 2 * PI / particleCount).toFloat()

                // Position on circle
                val x = center.x + radius * cos(particlePhase)
                val y = center.y + radius * sin(particlePhase)

                // Color based on position
                val colorIndex = (i.toFloat() / particleCount * 4).toInt()
                val color = when (colorIndex % 4) {
                    0 -> ChakraRed
                    1 -> ChakraYellow
                    2 -> ChakraBlue
                    else -> ChakraCrown
                }

                // Size based on position in cycle
                val sizeMultiplier = 0.7f + 0.3f * sin(particlePhase)
                val particleRadius = 4.dp.toPx() * sizeMultiplier

                // Draw particle glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            color.copy(alpha = 0.6f),
                            Color.Transparent
                        ),
                        center = Offset(x, y),
                        radius = particleRadius * 2
                    ),
                    radius = particleRadius * 2,
                    center = Offset(x, y)
                )

                // Draw particle
                drawCircle(
                    color = color,
                    radius = particleRadius,
                    center = Offset(x, y)
                )

                // Draw energy trail
                val trailLength = 5
                for (j in 1..trailLength) {
                    val trailPhase = particlePhase - (j * 0.1f)
                    val trailX = center.x + radius * cos(trailPhase)
                    val trailY = center.y + radius * sin(trailPhase)
                    val trailAlpha = (1f - j.toFloat() / trailLength) * 0.4f

                    drawCircle(
                        color = color.copy(alpha = trailAlpha),
                        radius = particleRadius * (1f - j.toFloat() / trailLength),
                        center = Offset(trailX, trailY)
                    )
                }
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// SACRED GEOMETRY LOADER
// ============================================================================

/**
 * Sacred Geometry: Rotating sacred geometry patterns
 *
 * Perfect for: Data sync, general loading
 */
@Composable
fun SacredGeometryLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sacredGeometry")

    val outerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "outerRotation"
    )

    val innerRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "innerRotation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "geometryScale"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() / 2 * scale

            // Outer hexagon
            rotate(outerRotation, center) {
                drawPolygon(
                    center = center,
                    radius = radius * 0.8f,
                    sides = 6,
                    color = SpiritualPurple,
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Middle triangle
            rotate(innerRotation, center) {
                drawPolygon(
                    center = center,
                    radius = radius * 0.55f,
                    sides = 3,
                    color = AuroraPink,
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Inner circle
            rotate(outerRotation * 0.5f, center) {
                drawCircle(
                    color = AuraGold,
                    radius = 3.dp.toPx(),
                    center = center
                )

                // Draw dots around circle
                val dotCount = 6
                for (i in 0 until dotCount) {
                    val angle = (i * 360f / dotCount + outerRotation) * (PI / 180f)
                    val dotX = center.x + radius * 0.35f * cos(angle).toFloat()
                    val dotY = center.y + radius * 0.35f * sin(angle).toFloat()

                    drawCircle(
                        color = CosmicBlue,
                        radius = 2.dp.toPx(),
                        center = Offset(dotX, dotY)
                    )
                }
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun DrawScope.drawPolygon(
    center: Offset,
    radius: Float,
    sides: Int,
    color: Color,
    strokeWidth: Float
) {
    val path = Path()
    val angleStep = 2 * PI / sides
    val startAngle = -PI / 2 // Start from top

    for (i in 0..sides) {
        val angle = startAngle + (i * angleStep)
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth)
    )
}

// ============================================================================
// YIN YANG SPIN LOADER
// ============================================================================

/**
 * Yin Yang Spin: Smooth yin-yang rotation
 *
 * Perfect for: Balance calculations, Ayurvedic analysis
 */
@Composable
fun YinYangSpinLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "yinYang")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "yinYangRotation"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() / 2 * 0.8f

            rotate(rotation, center) {
                // Draw outer circle
                drawCircle(
                    color = Color.Black,
                    radius = radius,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Draw Yang (white side)
                val yangPath = Path().apply {
                    moveTo(center.x, center.y - radius)
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius / 2,
                            top = center.y - radius,
                            right = center.x + radius / 2,
                            bottom = center.y
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius / 2,
                            top = center.y,
                            right = center.x + radius / 2,
                            bottom = center.y + radius
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 180f,
                        forceMoveTo = false
                    )
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius,
                            top = center.y - radius,
                            right = center.x + radius,
                            bottom = center.y + radius
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )
                    close()
                }

                drawPath(
                    path = yangPath,
                    color = Color.White
                )

                // Draw Yin (black side)
                val yinPath = Path().apply {
                    moveTo(center.x, center.y - radius)
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius / 2,
                            top = center.y - radius,
                            right = center.x + radius / 2,
                            bottom = center.y
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 180f,
                        forceMoveTo = false
                    )
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius / 2,
                            top = center.y,
                            right = center.x + radius / 2,
                            bottom = center.y + radius
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )
                    arcTo(
                        rect = androidx.compose.ui.geometry.Rect(
                            left = center.x - radius,
                            top = center.y - radius,
                            right = center.x + radius,
                            bottom = center.y + radius
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )
                    close()
                }

                drawPath(
                    path = yinPath,
                    color = Color.Black
                )

                // Draw small circles
                drawCircle(
                    color = Color.Black,
                    radius = radius * 0.15f,
                    center = Offset(center.x, center.y - radius / 2)
                )

                drawCircle(
                    color = Color.White,
                    radius = radius * 0.15f,
                    center = Offset(center.x, center.y + radius / 2)
                )
            }
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// MOON PHASE CYCLE LOADER
// ============================================================================

/**
 * Moon Phase Cycle: Moon phases cycling through
 *
 * Perfect for: Astrological calculations, timing-related operations
 */
@Composable
fun MoonPhaseCycleLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "moonPhase")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "moonPhase"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {
            val center = Offset(size.toPx() / 2, size.toPx() / 2)
            val radius = size.toPx() / 2 * 0.7f

            // Draw moon circle
            drawCircle(
                color = MoonlightSilver,
                radius = radius,
                center = center
            )

            // Draw shadow to create phase effect
            val shadowOffset = (phase % 8) - 4
            val shadowX = center.x + (shadowOffset / 4) * radius

            if (shadowOffset.absoluteValue <= 4) {
                drawCircle(
                    color = NightSky,
                    radius = radius,
                    center = Offset(shadowX, center.y)
                )
            }

            // Draw outer glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Transparent,
                        MoonlightSilver.copy(alpha = 0.3f)
                    ),
                    center = center,
                    radius = radius * 1.3f
                ),
                radius = radius * 1.3f,
                center = center
            )
        }

        if (message != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// CONTEXT-SPECIFIC LOADER CONVENIENCE FUNCTIONS
// ============================================================================

/**
 * Loading indicator for profile generation
 */
@Composable
fun ProfileGenerationLoader(
    modifier: Modifier = Modifier,
    message: String = "Calculating your spiritual profile..."
) {
    ChakraSequenceLoader(
        modifier = modifier,
        message = message
    )
}

/**
 * Loading indicator for compatibility calculations
 */
@Composable
fun CompatibilityCalculationLoader(
    modifier: Modifier = Modifier,
    message: String = "Analyzing compatibility..."
) {
    EnergyFlowLoader(
        modifier = modifier,
        message = message
    )
}

/**
 * Loading indicator for AI insights
 */
@Composable
fun AIInsightsLoader(
    modifier: Modifier = Modifier,
    message: String = "Gathering insights..."
) {
    ConstellationConnectLoader(
        modifier = modifier,
        message = message
    )
}

/**
 * Loading indicator for general data operations
 */
@Composable
fun DataSyncLoader(
    modifier: Modifier = Modifier,
    message: String = "Syncing..."
) {
    SacredGeometryLoader(
        modifier = modifier,
        message = message
    )
}

/**
 * Loading indicator for balance/Ayurvedic calculations
 */
@Composable
fun BalanceCalculationLoader(
    modifier: Modifier = Modifier,
    message: String = "Calculating balance..."
) {
    YinYangSpinLoader(
        modifier = modifier,
        message = message
    )
}

/**
 * Loading indicator for astrological calculations
 */
@Composable
fun AstrologicalCalculationLoader(
    modifier: Modifier = Modifier,
    message: String = "Reading the stars..."
) {
    MoonPhaseCycleLoader(
        modifier = modifier,
        message = message
    )
}
