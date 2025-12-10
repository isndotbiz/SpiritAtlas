package com.spiritatlas.core.ui.interaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.ChakraGreen
import com.spiritatlas.core.ui.theme.ChakraRed
import com.spiritatlas.core.ui.theme.ChakraYellow
import com.spiritatlas.core.ui.theme.SemanticColors
import com.spiritatlas.core.ui.theme.StardustBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// ============================================================================
// FEEDBACK ANIMATION TYPES
// ============================================================================

/**
 * Feedback types for visual and haptic responses
 */
enum class FeedbackType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO
}

// ============================================================================
// SUCCESS FEEDBACK
// ============================================================================

/**
 * Success feedback animation with green checkmark and confetti
 *
 * @param visible Whether the feedback is visible
 * @param modifier Modifier for the feedback
 * @param message Optional success message
 * @param onComplete Callback when animation completes
 */
@Composable
fun SuccessFeedback(
    visible: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    onComplete: (() -> Unit)? = null
) {
    val haptic = rememberHapticFeedback()

    LaunchedEffect(visible) {
        if (visible) {
            haptic.performHaptic(HapticFeedbackType.SUCCESS)
            delay(2000)
            onComplete?.invoke()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = SemanticColors.SuccessContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            // Confetti background
            ConfettiEffect(
                colors = listOf(
                    ChakraGreen,
                    AuraGold,
                    StardustBlue
                ),
                modifier = Modifier.size(120.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Success checkmark with bounce
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = ChakraGreen,
                    modifier = Modifier
                        .size(48.dp)
                        .scale(1f)
                )

                if (message != null) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ChakraGreen
                    )
                }
            }
        }
    }
}

/**
 * Confetti particle effect for success animations
 */
@Composable
private fun ConfettiEffect(
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    val particles = remember {
        List(20) {
            ConfettiParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                color = colors.random(),
                size = Random.nextFloat() * 4 + 2,
                velocityX = Random.nextFloat() * 4 - 2,
                velocityY = Random.nextFloat() * 4 + 2
            )
        }
    }

    val time = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        time.animateTo(
            targetValue = 1f,
            animationSpec = tween(1500)
        )
    }

    Canvas(modifier = modifier) {
        particles.forEach { particle ->
            val progress = time.value
            val x = size.width * particle.x + particle.velocityX * progress * 30
            val y = size.height * particle.y - particle.velocityY * progress * 30
            val alpha = 1f - progress

            if (x in 0f..size.width && y in 0f..size.height) {
                drawCircle(
                    color = particle.color.copy(alpha = alpha),
                    radius = particle.size,
                    center = Offset(x, y)
                )
            }
        }
    }
}

private data class ConfettiParticle(
    val x: Float,
    val y: Float,
    val color: Color,
    val size: Float,
    val velocityX: Float,
    val velocityY: Float
)

// ============================================================================
// ERROR FEEDBACK
// ============================================================================

/**
 * Error feedback animation with red X and shake
 *
 * @param visible Whether the feedback is visible
 * @param modifier Modifier for the feedback
 * @param message Optional error message
 * @param onComplete Callback when animation completes
 */
@Composable
fun ErrorFeedback(
    visible: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    onComplete: (() -> Unit)? = null
) {
    val haptic = rememberHapticFeedback()
    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(visible) {
        if (visible) {
            haptic.performHaptic(HapticFeedbackType.ERROR)

            // Shake animation
            scope.launch {
                repeat(3) {
                    shakeOffset.animateTo(10f, tween(50))
                    shakeOffset.animateTo(-10f, tween(50))
                }
                shakeOffset.animateTo(0f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
            }

            delay(2000)
            onComplete?.invoke()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = SemanticColors.ErrorContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .alpha(1f)
            ) {
                // Error X icon with shake
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .alpha(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Error",
                        tint = ChakraRed,
                        modifier = Modifier.size(48.dp)
                    )
                }

                if (message != null) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ChakraRed
                    )
                }
            }
        }
    }
}

// ============================================================================
// WARNING FEEDBACK
// ============================================================================

/**
 * Warning feedback animation with yellow warning icon and pulse
 *
 * @param visible Whether the feedback is visible
 * @param modifier Modifier for the feedback
 * @param message Optional warning message
 * @param onComplete Callback when animation completes
 */
@Composable
fun WarningFeedback(
    visible: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    onComplete: (() -> Unit)? = null
) {
    val haptic = rememberHapticFeedback()
    val pulse = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(visible) {
        if (visible) {
            haptic.performHaptic(HapticFeedbackType.WARNING)

            // Pulse animation
            scope.launch {
                repeat(3) {
                    pulse.animateTo(
                        targetValue = 1.15f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                    pulse.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )
                    delay(200)
                }
            }

            delay(2000)
            onComplete?.invoke()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = SemanticColors.WarningContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.scale(pulse.value)
            ) {
                // Warning icon with pulse
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Warning",
                    tint = ChakraYellow,
                    modifier = Modifier.size(48.dp)
                )

                if (message != null) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ChakraYellow
                    )
                }
            }
        }
    }
}

// ============================================================================
// INFO FEEDBACK
// ============================================================================

/**
 * Info feedback animation with blue info icon and glow
 *
 * @param visible Whether the feedback is visible
 * @param modifier Modifier for the feedback
 * @param message Optional info message
 * @param onComplete Callback when animation completes
 */
@Composable
fun InfoFeedback(
    visible: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    onComplete: (() -> Unit)? = null
) {
    val haptic = rememberHapticFeedback()
    val glow = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(visible) {
        if (visible) {
            haptic.performHaptic(HapticFeedbackType.LIGHT)

            // Glow animation
            scope.launch {
                glow.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(500)
                )
                glow.animateTo(
                    targetValue = 0.5f,
                    animationSpec = tween(500)
                )
            }

            delay(2000)
            onComplete?.invoke()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = SemanticColors.InfoContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            // Glow effect
            Canvas(modifier = Modifier.size(80.dp)) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            StardustBlue.copy(alpha = 0.3f * glow.value),
                            StardustBlue.copy(alpha = 0f)
                        )
                    ),
                    radius = size.minDimension / 2
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Info icon with glow
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = StardustBlue,
                    modifier = Modifier.size(48.dp)
                )

                if (message != null) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = StardustBlue
                    )
                }
            }
        }
    }
}

// ============================================================================
// UNIFIED FEEDBACK COMPONENT
// ============================================================================

/**
 * Unified feedback component that handles all feedback types
 *
 * @param type Type of feedback to display
 * @param visible Whether the feedback is visible
 * @param modifier Modifier for the feedback
 * @param message Optional message to display
 * @param onComplete Callback when animation completes
 */
@Composable
fun SpiritualFeedback(
    type: FeedbackType,
    visible: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    onComplete: (() -> Unit)? = null
) {
    when (type) {
        FeedbackType.SUCCESS -> SuccessFeedback(
            visible = visible,
            modifier = modifier,
            message = message,
            onComplete = onComplete
        )
        FeedbackType.ERROR -> ErrorFeedback(
            visible = visible,
            modifier = modifier,
            message = message,
            onComplete = onComplete
        )
        FeedbackType.WARNING -> WarningFeedback(
            visible = visible,
            modifier = modifier,
            message = message,
            onComplete = onComplete
        )
        FeedbackType.INFO -> InfoFeedback(
            visible = visible,
            modifier = modifier,
            message = message,
            onComplete = onComplete
        )
    }
}

// ============================================================================
// INLINE FEEDBACK INDICATORS
// ============================================================================

/**
 * Inline feedback indicator for forms and inputs
 * Small, subtle feedback shown next to fields
 *
 * @param type Type of feedback
 * @param visible Whether the indicator is visible
 * @param modifier Modifier for the indicator
 */
@Composable
fun InlineFeedbackIndicator(
    type: FeedbackType,
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    val (icon, color) = when (type) {
        FeedbackType.SUCCESS -> Icons.Default.Check to ChakraGreen
        FeedbackType.ERROR -> Icons.Default.Close to ChakraRed
        FeedbackType.WARNING -> Icons.Default.Warning to ChakraYellow
        FeedbackType.INFO -> Icons.Default.Info to StardustBlue
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut(),
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = type.name,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
}
