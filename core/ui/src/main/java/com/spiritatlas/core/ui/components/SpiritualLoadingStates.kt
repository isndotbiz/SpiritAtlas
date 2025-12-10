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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * SPIRITUAL LOADING STATES
 *
 * Beautiful, not boring loading animations that feel magical.
 *
 * Features:
 * - Sacred geometry loaders
 * - Particle system animations
 * - Breathing/pulsing effects
 * - Spiritual color gradients
 * - Multiple loading states for different contexts
 */

// ============================================================================
// MAIN SPIRITUAL LOADER
// ============================================================================

/**
 * Primary spiritual loader with sacred geometry
 *
 * Features:
 * - Rotating sacred geometry pattern
 * - Pulsing center
 * - Particle orbits
 * - Gradient colors
 *
 * Usage:
 * ```kotlin
 * if (isLoading) {
 *     SpiritualLoader(
 *         size = 64.dp,
 *         message = "Loading your cosmic blueprint..."
 *     )
 * }
 * ```
 */
@Composable
fun SpiritualLoader(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    message: String? = null,
    colors: List<Color> = listOf(SpiritualPurple, MysticViolet, CosmicBlue)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SacredGeometryLoader(
            size = size,
            colors = colors
        )

        message?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Sacred geometry loader animation
 */
@Composable
private fun SacredGeometryLoader(
    size: Dp,
    colors: List<Color>
) {
    val infiniteTransition = rememberInfiniteTransition(label = "sacredLoader")

    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Pulse animation
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Particle orbit
    val particleAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleAngle"
    )

    Canvas(modifier = Modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = this.size.minDimension / 2.5f

        // Outer rotating ring
        rotate(rotation, pivot = center) {
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = colors + colors.first(), // Complete the gradient loop
                    center = center
                ),
                radius = radius,
                center = center,
                style = Stroke(width = 3f, cap = StrokeCap.Round)
            )

            // Sacred geometry petals
            for (i in 0 until 6) {
                val angle = (i * 60f) * PI.toFloat() / 180f
                val petalCenter = Offset(
                    center.x + cos(angle) * radius * 0.7f,
                    center.y + sin(angle) * radius * 0.7f
                )

                drawCircle(
                    color = colors[i % colors.size].copy(alpha = 0.3f),
                    radius = radius * 0.4f,
                    center = petalCenter,
                    style = Stroke(width = 2f)
                )
            }
        }

        // Middle ring (counter-rotating)
        rotate(-rotation * 1.5f, pivot = center) {
            drawCircle(
                color = colors[1].copy(alpha = 0.6f),
                radius = radius * 0.7f,
                center = center,
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )
        }

        // Pulsing center
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    colors[2].copy(alpha = 0.8f),
                    colors[2].copy(alpha = 0.4f),
                    Color.Transparent
                ),
                center = center,
                radius = radius * 0.5f * pulse
            ),
            radius = radius * 0.5f * pulse,
            center = center
        )

        // Solid center
        drawCircle(
            color = colors[2],
            radius = radius * 0.25f,
            center = center
        )

        // Orbiting particles
        for (i in 0 until 3) {
            val angle = particleAngle + (i * 2 * PI.toFloat() / 3)
            val particleX = center.x + cos(angle) * radius * 1.2f
            val particleY = center.y + sin(angle) * radius * 1.2f

            drawCircle(
                color = AuraGold.copy(alpha = 0.8f),
                radius = 4f,
                center = Offset(particleX, particleY)
            )

            // Particle glow
            drawCircle(
                color = AuraGold.copy(alpha = 0.3f),
                radius = 8f,
                center = Offset(particleX, particleY)
            )
        }
    }
}

// ============================================================================
// CHAKRA LOADER
// ============================================================================

/**
 * Chakra energy loader
 *
 * Shows chakra colors cycling through an energy visualization
 */
@Composable
fun ChakraLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "chakraLoader")

    val colorIndex by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "colorIndex"
    )

    val chakraColors = SpiritualColors.ChakraColors

    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = this.size.minDimension / 3f

        // Draw all 7 chakras
        for (i in 0 until 7) {
            val angle = (i * (360f / 7f) - 90f) * PI.toFloat() / 180f
            val chakraX = center.x + cos(angle) * radius
            val chakraY = center.y + sin(angle) * radius

            val isActive = i.toFloat() <= colorIndex % 7f
            val alpha = if (isActive) 1f else 0.3f
            val size = if (isActive) 12f else 8f

            // Chakra point
            drawCircle(
                color = chakraColors[i].copy(alpha = alpha),
                radius = size,
                center = Offset(chakraX, chakraY)
            )

            // Glow effect for active chakra
            if (isActive) {
                drawCircle(
                    color = chakraColors[i].copy(alpha = 0.3f),
                    radius = size * 2f,
                    center = Offset(chakraX, chakraY)
                )
            }
        }

        // Energy flow lines
        val currentColor = chakraColors[(colorIndex % 7f).toInt()]
        for (i in 0 until 7) {
            val startAngle = (i * (360f / 7f) - 90f) * PI.toFloat() / 180f
            val endAngle = ((i + 1) * (360f / 7f) - 90f) * PI.toFloat() / 180f

            val startX = center.x + cos(startAngle) * radius
            val startY = center.y + sin(startAngle) * radius
            val endX = center.x + cos(endAngle) * radius
            val endY = center.y + sin(endAngle) * radius

            drawLine(
                color = currentColor.copy(alpha = 0.2f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2f
            )
        }

        // Center energy core
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    currentColor.copy(alpha = 0.6f),
                    Color.Transparent
                ),
                center = center
            ),
            radius = radius * 0.6f,
            center = center
        )
    }
}

// ============================================================================
// ZODIAC WHEEL LOADER
// ============================================================================

/**
 * Zodiac wheel loader
 *
 * Rotating zodiac wheel with constellation points
 */
@Composable
fun ZodiacWheelLoader(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "zodiacLoader")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "zodiacRotation"
    )

    val twinkle by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "twinkle"
    )

    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = this.size.minDimension / 2.5f

        rotate(rotation, pivot = center) {
            // Outer zodiac ring
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        SpiritualPurple,
                        CosmicBlue,
                        AuraGold,
                        SpiritualPurple
                    ),
                    center = center
                ),
                radius = radius,
                center = center,
                style = Stroke(width = 2f)
            )

            // 12 zodiac points
            for (i in 0 until 12) {
                val angle = (i * 30f) * PI.toFloat() / 180f
                val pointX = center.x + cos(angle) * radius
                val pointY = center.y + sin(angle) * radius

                // Constellation point
                drawCircle(
                    color = AuraGold.copy(alpha = twinkle),
                    radius = 4f,
                    center = Offset(pointX, pointY)
                )

                // Star glow
                drawCircle(
                    color = AuraGold.copy(alpha = twinkle * 0.3f),
                    radius = 8f,
                    center = Offset(pointX, pointY)
                )
            }

            // Connecting constellation lines
            for (i in 0 until 12) {
                val startAngle = (i * 30f) * PI.toFloat() / 180f
                val endAngle = ((i + 1) * 30f) * PI.toFloat() / 180f

                val startX = center.x + cos(startAngle) * radius
                val startY = center.y + sin(startAngle) * radius
                val endX = center.x + cos(endAngle) * radius
                val endY = center.y + sin(endAngle) * radius

                drawLine(
                    color = CosmicBlue.copy(alpha = 0.2f),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 1f
                )
            }
        }

        // Central star
        drawStar(
            center = center,
            radius = radius * 0.3f,
            color = AuraGold.copy(alpha = twinkle),
            points = 5
        )
    }
}

// ============================================================================
// MEDITATION BREATH LOADER
// ============================================================================

/**
 * Meditation breath loader
 *
 * Breathing animation that's calming and beautiful
 */
@Composable
fun MeditationBreathLoader(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    showText: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathLoader")

    val breathPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathPhase"
    )

    val scale = 0.7f + (breathPhase * 0.3f) // Scale from 0.7 to 1.0

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val center = Offset(this.size.width / 2f, this.size.height / 2f)
            val radius = this.size.minDimension / 3f * scale

            // Outer breath ring
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CosmicBlue.copy(alpha = 0.6f * breathPhase),
                        CosmicBlue.copy(alpha = 0.1f * breathPhase),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius * 2f
                ),
                radius = radius * 2f,
                center = center
            )

            // Middle ring
            drawCircle(
                color = SpiritualPurple.copy(alpha = 0.4f + breathPhase * 0.4f),
                radius = radius * 1.5f,
                center = center,
                style = Stroke(width = 2f)
            )

            // Inner core
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = breathPhase),
                        SpiritualPurple.copy(alpha = 0.8f),
                        MysticViolet
                    ),
                    center = center
                ),
                radius = radius,
                center = center
            )
        }

        if (showText) {
            Text(
                text = if (breathPhase > 0.5f) "Breathe In" else "Breathe Out",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ============================================================================
// SKELETON LOADER
// ============================================================================

/**
 * Skeleton screen loader for content placeholders
 *
 * Usage:
 * ```kotlin
 * if (isLoading) {
 *     ProfileCardSkeleton()
 * } else {
 *     ProfileCard(profile)
 * }
 * ```
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    shimmer: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton")

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (shimmer) {
                    Modifier.drawShimmer(shimmerOffset)
                } else {
                    Modifier
                }
            )
    )
}

private fun Modifier.drawShimmer(offset: Float): Modifier = this.drawWithContent {
    drawContent()

    // Shimmer gradient overlay
    val shimmerWidth = size.width * 0.3f
    val shimmerX = (size.width + shimmerWidth) * offset - shimmerWidth

    drawRect(
        brush = Brush.horizontalGradient(
            colors = listOf(
                Color.Transparent,
                Color.White.copy(alpha = 0.3f),
                Color.Transparent
            ),
            startX = shimmerX,
            endX = shimmerX + shimmerWidth
        )
    )
}

// ============================================================================
// HELPER FUNCTIONS
// ============================================================================

/**
 * Draw a star shape
 */
private fun DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color,
    points: Int = 5
) {
    val path = Path()
    val angleStep = PI.toFloat() / points

    for (i in 0 until points * 2) {
        val angle = i * angleStep - PI.toFloat() / 2
        val r = if (i % 2 == 0) radius else radius * 0.4f
        val x = center.x + r * cos(angle)
        val y = center.y + r * sin(angle)

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    drawPath(path = path, color = color)
}
