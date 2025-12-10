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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.animation.pulsingAnimation
import com.spiritatlas.core.ui.haptics.HapticFeedbackController
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.core.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * CELEBRATION EFFECTS
 *
 * Magical celebration animations for success moments, completions, and special events.
 *
 * Features:
 * - Confetti particle systems
 * - Sparkle effects
 * - Starburst animations
 * - Glow pulses
 * - Success ripples
 * - Profile completion celebrations
 */

// ============================================================================
// CONFETTI CELEBRATION
// ============================================================================

/**
 * Full-screen confetti celebration
 *
 * Usage:
 * ```kotlin
 * if (showCelebration) {
 *     ConfettiCelebration(
 *         onComplete = { showCelebration = false }
 *     )
 * }
 * ```
 */
@Composable
fun ConfettiCelebration(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    durationMillis: Int = 3000,
    particleCount: Int = 100
) {
    val haptic = rememberHapticFeedback()

    LaunchedEffect(Unit) {
        // Success haptic pattern
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        delay(durationMillis.toLong())
        onComplete()
    }

    val particles = remember {
        List(particleCount) {
            ConfettiParticle(
                startX = Random.nextFloat(),
                startY = -0.1f,
                color = SpiritualColors.ChakraColors.random(),
                size = Random.nextFloat() * 8f + 4f,
                rotation = Random.nextFloat() * 360f,
                velocity = Random.nextFloat() * 2f + 1f,
                wobble = Random.nextFloat() * 2f - 1f
            )
        }
    }

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis, easing = LinearEasing)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val x = size.width * particle.startX +
                    sin(animationProgress.value * 4 * PI.toFloat()) * particle.wobble * 50f
            val y = size.height * (particle.startY + animationProgress.value * (1.2f + particle.velocity * 0.5f))
            val rotation = particle.rotation + animationProgress.value * 720f

            if (y < size.height) {
                drawConfettiParticle(
                    center = Offset(x, y),
                    size = particle.size,
                    color = particle.color,
                    rotation = rotation
                )
            }
        }
    }
}

private data class ConfettiParticle(
    val startX: Float,
    val startY: Float,
    val color: Color,
    val size: Float,
    val rotation: Float,
    val velocity: Float,
    val wobble: Float
)

private fun DrawScope.drawConfettiParticle(
    center: Offset,
    size: Float,
    color: Color,
    rotation: Float
) {
    val shapes = listOf("circle", "square", "triangle", "star")
    val shape = shapes[Random.nextInt(shapes.size)]

    when (shape) {
        "circle" -> drawCircle(
            color = color,
            radius = size / 2,
            center = center
        )
        "square" -> drawRect(
            color = color,
            topLeft = Offset(center.x - size / 2, center.y - size / 2),
            size = androidx.compose.ui.geometry.Size(size, size)
        )
        "triangle", "star" -> {
            // Simplified for performance
            drawCircle(
                color = color,
                radius = size / 2,
                center = center
            )
        }
    }
}

// ============================================================================
// SPARKLE BURST
// ============================================================================

/**
 * Sparkle burst effect for button taps and special moments
 *
 * Usage:
 * ```kotlin
 * var showSparkles by remember { mutableStateOf(false) }
 *
 * Box {
 *     Button(onClick = { showSparkles = true }) {
 *         Text("Save")
 *     }
 *
 *     if (showSparkles) {
 *         SparkleBurst(
 *             modifier = Modifier.align(Alignment.Center),
 *             onComplete = { showSparkles = false }
 *         )
 *     }
 * }
 * ```
 */
@Composable
fun SparkleBurst(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    sparkleCount: Int = 20,
    durationMillis: Int = 800
) {
    val haptic = rememberHapticFeedback()

    LaunchedEffect(Unit) {
        haptic.performHaptic(HapticFeedbackType.LIGHT)
        delay(durationMillis.toLong())
        onComplete()
    }

    val sparkles = remember {
        List(sparkleCount) {
            val angle = (it * 360f / sparkleCount) * PI.toFloat() / 180f
            SparkleParticle(
                angle = angle,
                distance = Random.nextFloat() * 100f + 50f,
                size = Random.nextFloat() * 4f + 2f,
                color = listOf(AuraGold, Color.White, SpiritualPurple).random()
            )
        }
    }

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
        )
    }

    Canvas(modifier = modifier.size(200.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)

        sparkles.forEach { sparkle ->
            val distance = sparkle.distance * animationProgress.value
            val x = center.x + cos(sparkle.angle) * distance
            val y = center.y + sin(sparkle.angle) * distance
            val alpha = 1f - animationProgress.value

            // Sparkle glow
            drawCircle(
                color = sparkle.color.copy(alpha = alpha * 0.3f),
                radius = sparkle.size * 3f,
                center = Offset(x, y)
            )

            // Sparkle core
            drawCircle(
                color = sparkle.color.copy(alpha = alpha),
                radius = sparkle.size,
                center = Offset(x, y)
            )
        }
    }
}

private data class SparkleParticle(
    val angle: Float,
    val distance: Float,
    val size: Float,
    val color: Color
)

// ============================================================================
// SUCCESS RIPPLE
// ============================================================================

/**
 * Success ripple effect that emanates from center
 *
 * Perfect for form submissions, saves, and completions
 */
@Composable
fun SuccessRipple(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    color: Color = CosmicBlue,
    durationMillis: Int = 1000
) {
    val haptic = rememberHapticFeedback()

    LaunchedEffect(Unit) {
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        delay(durationMillis.toLong())
        onComplete()
    }

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
        )
    }

    Canvas(modifier = modifier.size(200.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = size.minDimension / 2f

        // Multiple ripples
        for (i in 0 until 3) {
            val rippleProgress = (animationProgress.value - i * 0.2f).coerceIn(0f, 1f)
            val radius = maxRadius * rippleProgress
            val alpha = (1f - rippleProgress) * 0.5f

            drawCircle(
                color = color.copy(alpha = alpha),
                radius = radius,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
            )
        }

        // Center pulse
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    color.copy(alpha = 1f - animationProgress.value),
                    Color.Transparent
                ),
                center = center
            ),
            radius = maxRadius * 0.3f * (1f + animationProgress.value),
            center = center
        )
    }
}

// ============================================================================
// STARBURST
// ============================================================================

/**
 * Starburst explosion effect
 *
 * Dramatic celebration for major achievements
 */
@Composable
fun Starburst(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    rayCount: Int = 12,
    colors: List<Color> = listOf(AuraGold, SpiritualPurple, CosmicBlue),
    durationMillis: Int = 1200
) {
    val haptic = rememberHapticFeedback()

    LaunchedEffect(Unit) {
        // Dramatic haptic pattern
        haptic.performCustomPattern(
            timings = longArrayOf(0, 50, 100, 50, 100, 100),
            amplitudes = intArrayOf(0, 150, 0, 200, 0, 255)
        )
        delay(durationMillis.toLong())
        onComplete()
    }

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
        )
    }

    val rotation by rememberInfiniteTransition(label = "starburst").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier.size(300.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxLength = size.minDimension / 2f

        rotate(rotation, pivot = center) {
            for (i in 0 until rayCount) {
                val angle = (i * 360f / rayCount) * PI.toFloat() / 180f
                val length = maxLength * animationProgress.value
                val color = colors[i % colors.size]

                // Ray gradient
                val endX = center.x + cos(angle) * length
                val endY = center.y + sin(angle) * length

                // Draw ray with gradient
                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color.copy(alpha = 0.8f),
                            Color.Transparent
                        ),
                        start = center,
                        end = Offset(endX, endY)
                    ),
                    start = center,
                    end = Offset(endX, endY),
                    strokeWidth = 4f,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )

                // Ray tip glow
                drawCircle(
                    color = color.copy(alpha = 0.6f * (1f - animationProgress.value)),
                    radius = 8f,
                    center = Offset(endX, endY)
                )
            }
        }

        // Center star
        drawStar(
            center = center,
            radius = 30f * (0.5f + animationProgress.value * 0.5f),
            color = Color.White.copy(alpha = 1f - animationProgress.value),
            points = 5
        )
    }
}

// ============================================================================
// PROFILE COMPLETION CELEBRATION
// ============================================================================

/**
 * Special celebration for profile completion
 *
 * Combines multiple effects for maximum delight
 */
@Composable
fun ProfileCompletionCelebration(
    modifier: Modifier = Modifier,
    profileName: String,
    onComplete: () -> Unit = {}
) {
    val haptic = rememberHapticFeedback()
    var showConfetti by remember { mutableStateOf(false) }
    var showStarburst by remember { mutableStateOf(false) }
    var showMessage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Sequence celebration effects
        showStarburst = true
        delay(200)
        showConfetti = true
        delay(300)
        showMessage = true

        // Victory haptic pattern
        haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE)

        delay(3000)
        onComplete()
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (showStarburst) {
            Starburst(
                modifier = Modifier.align(Alignment.Center),
                onComplete = { showStarburst = false }
            )
        }

        if (showConfetti) {
            ConfettiCelebration(
                onComplete = { showConfetti = false }
            )
        }

        if (showMessage) {
            CelebrationMessage(
                modifier = Modifier.align(Alignment.Center),
                title = "Profile Complete!",
                subtitle = "$profileName's cosmic blueprint is ready"
            )
        }
    }
}

/**
 * Celebration message with animations
 */
@Composable
private fun CelebrationMessage(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "messageScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(500),
        label = "messageAlpha"
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Success icon
        Text(
            text = "✨",
            fontSize = 64.sp
        )

        // Title
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = AuraGold
        )

        // Subtitle
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

// ============================================================================
// ACHIEVEMENT UNLOCK
// ============================================================================

/**
 * Achievement unlock animation
 *
 * For easter eggs and special milestones
 */
@Composable
fun AchievementUnlock(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: String = "🏆",
    onComplete: () -> Unit = {}
) {
    val haptic = rememberHapticFeedback()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        delay(100)
        visible = true
        delay(3000)
        onComplete()
    }

    val slideIn by animateDpAsState(
        targetValue = if (visible) 0.dp else (-100).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "achievementSlide"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .offset(y = slideIn)
    ) {
        GlassmorphCard(elevation = 3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon with pulse
                Text(
                    text = icon,
                    fontSize = 48.sp,
                    modifier = Modifier.pulsingAnimation()
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Achievement Unlocked!",
                        style = MaterialTheme.typography.labelSmall,
                        color = AuraGold,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
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
