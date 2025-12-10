package com.spiritatlas.core.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Visual Polish System
 *
 * A comprehensive system for adding subtle animations, visual hierarchy,
 * and beautiful details to every screen in the app.
 *
 * Features:
 * - Screen entrance/exit animations
 * - Element reveal animations
 * - Hover/press effects
 * - Visual hierarchy (elevation, shadows, glows)
 * - Decorative elements (sacred geometry, dividers)
 * - Typography enhancements
 * - Success/error/loading states
 */

// ============================================================================
// SCREEN ANIMATIONS - For full screen transitions
// ============================================================================

/**
 * Standard screen entrance animation
 * Fade in + subtle slide up
 */
@Composable
fun rememberScreenEntranceAnimation(): Boolean {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    return visible
}

/**
 * Modifier for screen entrance animation
 */
fun Modifier.screenEntrance(
    visible: Boolean = true,
    delay: Int = 0
): Modifier = composed {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "screenEntranceAlpha"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 30f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "screenEntranceOffset"
    )

    this
        .alpha(alpha)
        .offset(y = offsetY.dp)
}

/**
 * Staggered entrance for list items
 */
fun Modifier.staggeredEntrance(
    index: Int,
    baseDelay: Int = 50
): Modifier = composed {
    val delay = index * baseDelay
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "staggeredAlpha_$index"
    )

    val offsetY by animateFloatAsState(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = 400,
            delayMillis = delay,
            easing = FastOutSlowInEasing
        ),
        label = "staggeredOffset_$index"
    )

    this
        .alpha(alpha)
        .offset(y = offsetY.dp)
}

// ============================================================================
// CARD EFFECTS - For interactive elements
// ============================================================================

/**
 * Card hover effect with scale and shadow
 */
fun Modifier.cardHoverEffect(
    hovered: Boolean = false
): Modifier = composed {
    val scale by animateFloatAsState(
        targetValue = if (hovered) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "cardScale"
    )

    val elevation by animateDpAsState(
        targetValue = if (hovered) 8.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "cardElevation"
    )

    this
        .scale(scale)
        .shadow(
            elevation = elevation,
            shape = RoundedCornerShape(20.dp),
            ambientColor = SpiritualPurple.copy(alpha = 0.1f),
            spotColor = SpiritualPurple.copy(alpha = 0.2f)
        )
}

/**
 * Button press effect
 */
fun Modifier.buttonPressEffect(
    pressed: Boolean = false,
    enabled: Boolean = true
): Modifier = composed {
    val scale by animateFloatAsState(
        targetValue = when {
            !enabled -> 0.95f
            pressed -> 0.92f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    this.scale(scale)
}

// ============================================================================
// GLOW EFFECTS - For emphasis and spiritual energy
// ============================================================================

/**
 * Soft glow effect around elements
 */
fun Modifier.softGlow(
    glowColor: Color = SpiritualPurple,
    blurRadius: Dp = 16.dp,
    glowAlpha: Float = 0.5f
): Modifier = this.then(
    Modifier
        .drawBehind {
            drawCircle(
                color = glowColor.copy(alpha = glowAlpha),
                radius = size.minDimension / 2
            )
        }
        .blur(blurRadius)
)

/**
 * Pulsing glow for active elements
 */
fun Modifier.pulsingGlow(
    glowColor: Color = SpiritualPurple,
    minAlpha: Float = 0.3f,
    maxAlpha: Float = 0.7f
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "pulsingGlow")
    val alpha by infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = maxAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    this.drawBehind {
        drawCircle(
            color = glowColor.copy(alpha = alpha),
            radius = size.minDimension * 0.6f
        )
    }
}

/**
 * Border glow effect
 */
fun Modifier.borderGlow(
    glowColor: Color = SpiritualPurple,
    cornerRadius: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    glowAlpha: Float = 0.6f
): Modifier = this.drawBehind {
    drawRoundRect(
        color = glowColor.copy(alpha = glowAlpha),
        size = size,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(width = strokeWidth.toPx())
    )
}

// ============================================================================
// DECORATIVE ELEMENTS - Sacred geometry and dividers
// ============================================================================

/**
 * Sacred geometry corner decoration
 */
@Composable
fun SacredGeometryCorner(
    modifier: Modifier = Modifier,
    color: Color = SpiritualPurple.copy(alpha = 0.3f),
    size: Dp = 32.dp
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val sizeF = size.toPx()

        // Draw decorative corner arc
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            size = Size(sizeF, sizeF),
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw inner arc
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 90f,
            useCenter = false,
            size = Size(sizeF * 0.6f, sizeF * 0.6f),
            topLeft = Offset(sizeF * 0.2f, sizeF * 0.2f),
            style = Stroke(width = 1.5.dp.toPx())
        )

        // Draw connecting lines
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(sizeF * 0.3f, sizeF * 0.3f),
            strokeWidth = 1.dp.toPx()
        )
    }
}

/**
 * Spiritual divider with symbol
 */
@Composable
fun SpiritualDivider(
    modifier: Modifier = Modifier,
    color: Color = SpiritualPurple.copy(alpha = 0.3f),
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            color,
                            color
                        )
                    )
                )
        )

        // Center symbol (diamond)
        Canvas(
            modifier = Modifier
                .size(12.dp)
                .padding(horizontal = 12.dp)
        ) {
            val path = Path().apply {
                moveTo(size.width / 2, 0f)
                lineTo(size.width, size.height / 2)
                lineTo(size.width / 2, size.height)
                lineTo(0f, size.height / 2)
                close()
            }
            drawPath(path, color)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(thickness)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            color,
                            color,
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

/**
 * Golden ratio accent line
 */
@Composable
fun GoldenRatioAccent(
    modifier: Modifier = Modifier,
    color: Color = AuraGold
) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.618f) // Golden ratio
            .height(2.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.8f),
                        color.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )
            )
    )
}

// ============================================================================
// STATE INDICATORS - Success, Error, Loading
// ============================================================================

/**
 * Success state with checkmark and glow
 */
@Composable
fun SuccessIndicator(
    modifier: Modifier = Modifier,
    message: String = "Success!"
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ChakraGreen.copy(alpha = 0.3f),
                            ChakraGreen.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .border(2.dp, ChakraGreen, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(32.dp)) {
                val path = Path().apply {
                    moveTo(size.width * 0.2f, size.height * 0.5f)
                    lineTo(size.width * 0.4f, size.height * 0.7f)
                    lineTo(size.width * 0.8f, size.height * 0.3f)
                }
                drawPath(
                    path = path,
                    color = ChakraGreen,
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = ChakraGreen
        )
    }
}

/**
 * Error state with X and shake animation
 */
@Composable
fun ErrorIndicator(
    modifier: Modifier = Modifier,
    message: String = "Error"
) {
    var shake by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        shake = true
    }

    val offsetX by animateFloatAsState(
        targetValue = if (shake) 0f else 10f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "errorShake"
    )

    Column(
        modifier = modifier.offset(x = offsetX.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            ChakraRed.copy(alpha = 0.3f),
                            ChakraRed.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
                .border(2.dp, ChakraRed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(32.dp)) {
                // Draw X
                drawLine(
                    color = ChakraRed,
                    start = Offset(size.width * 0.25f, size.height * 0.25f),
                    end = Offset(size.width * 0.75f, size.height * 0.75f),
                    strokeWidth = 4.dp.toPx()
                )
                drawLine(
                    color = ChakraRed,
                    start = Offset(size.width * 0.75f, size.height * 0.25f),
                    end = Offset(size.width * 0.25f, size.height * 0.75f),
                    strokeWidth = 4.dp.toPx()
                )
            }
        }

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = ChakraRed
        )
    }
}

/**
 * Loading state with spinning circle
 */
@Composable
fun SpiritualLoadingIndicator(
    modifier: Modifier = Modifier,
    message: String? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loadingRotation"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Canvas(
            modifier = Modifier.size(48.dp)
        ) {
            rotate(rotation) {
                val colors = listOf(
                    SpiritualPurple,
                    MysticViolet,
                    CosmicBlue,
                    AuraGold
                )

                for (i in 0 until 4) {
                    val angle = i * 90f
                    val radian = Math.toRadians(angle.toDouble())
                    val offset = Offset(
                        x = (size.width / 2) + (size.width / 3) * cos(radian).toFloat(),
                        y = (size.height / 2) + (size.height / 3) * sin(radian).toFloat()
                    )

                    drawCircle(
                        color = colors[i],
                        radius = 6.dp.toPx(),
                        center = offset
                    )
                }
            }
        }

        message?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ============================================================================
// TYPOGRAPHY ENHANCEMENTS
// ============================================================================

/**
 * Text with subtle shadow for readability
 */
fun Modifier.textShadow(
    color: Color = Color.Black.copy(alpha = 0.5f),
    offsetX: Dp = 0.dp,
    offsetY: Dp = 2.dp,
    blurRadius: Dp = 4.dp
): Modifier = this.drawBehind {
    drawContext.canvas.save()
    drawContext.canvas.translate(offsetX.toPx(), offsetY.toPx())
    // Note: Actual text shadow requires platform-specific implementation
    drawContext.canvas.restore()
}

/**
 * Gradient text (compose function)
 */
@Composable
fun EnhancedGradientText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    gradient: List<Color> = listOf(SpiritualPurple, MysticViolet, CosmicBlue)
) {
    GradientText(
        text = text,
        gradient = gradient,
        style = style,
        modifier = modifier
    )
}

// ============================================================================
// BACKGROUND PATTERNS - Subtle texture
// ============================================================================

/**
 * Subtle dot pattern background
 */
@Composable
fun DotPatternBackground(
    modifier: Modifier = Modifier,
    dotColor: Color = SpiritualPurple.copy(alpha = 0.05f),
    spacing: Dp = 32.dp
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val spacingPx = spacing.toPx()
        val rows = (size.height / spacingPx).toInt() + 1
        val cols = (size.width / spacingPx).toInt() + 1

        for (row in 0..rows) {
            for (col in 0..cols) {
                drawCircle(
                    color = dotColor,
                    radius = 2.dp.toPx(),
                    center = Offset(
                        x = col * spacingPx,
                        y = row * spacingPx
                    )
                )
            }
        }
    }
}

/**
 * Geometric grid pattern
 */
@Composable
fun GeometricGridPattern(
    modifier: Modifier = Modifier,
    lineColor: Color = SpiritualPurple.copy(alpha = 0.08f),
    spacing: Dp = 48.dp
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val spacingPx = spacing.toPx()

        // Vertical lines
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = 1.dp.toPx()
            )
            x += spacingPx
        }

        // Horizontal lines
        var y = 0f
        while (y < size.height) {
            drawLine(
                color = lineColor,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.dp.toPx()
            )
            y += spacingPx
        }
    }
}

// ============================================================================
// ELEVATION SYSTEM - Consistent depth
// ============================================================================

/**
 * Standard card elevation with spiritual shadows
 */
fun Modifier.spiritualElevation(
    level: Int = 2, // 1=low, 2=medium, 3=high
    glowColor: Color = SpiritualPurple
): Modifier {
    val elevation = when (level) {
        1 -> 4.dp
        2 -> 8.dp
        3 -> 16.dp
        else -> 8.dp
    }

    return this.shadow(
        elevation = elevation,
        shape = RoundedCornerShape(20.dp),
        ambientColor = glowColor.copy(alpha = 0.1f),
        spotColor = glowColor.copy(alpha = 0.2f)
    )
}

// ============================================================================
// SELECTION STATES - Visual feedback
// ============================================================================

/**
 * Selection highlight with border
 */
fun Modifier.selectionHighlight(
    selected: Boolean = false,
    color: Color = SpiritualPurple
): Modifier = composed {
    val borderWidth by animateDpAsState(
        targetValue = if (selected) 3.dp else 1.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "selectionBorder"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (selected) color.copy(alpha = 0.1f) else Color.Transparent,
        animationSpec = tween(300),
        label = "selectionBackground"
    )

    this
        .background(backgroundColor, RoundedCornerShape(16.dp))
        .border(
            width = borderWidth,
            color = if (selected) color else color.copy(alpha = 0.3f),
            shape = RoundedCornerShape(16.dp)
        )
}

/**
 * Focus ring for accessibility
 */
fun Modifier.focusRing(
    focused: Boolean = false,
    color: Color = SpiritualPurple
): Modifier = this.then(
    if (focused) {
        Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(color, color.copy(alpha = 0.5f))
            ),
            shape = RoundedCornerShape(20.dp)
        )
    } else {
        Modifier
    }
)
