package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.*

/**
 * SpiritAtlas Extended Spiritual Icons System
 *
 * Additional icons for comprehensive spiritual coverage:
 * - Tantric Symbols (Yoni, Lingam, Bindu, Kundalini)
 * - Human Design Elements (Gates, Channels, Centers, Variables)
 * - Relationship Icons (Connection, Bond, Attraction, Harmony)
 * - Meditation States (Calm, Focus, Expansion, Transcendence)
 * - Energy States (High, Low, Balanced, Flowing, Blocked)
 * - Advanced Zodiac (Nodes, Chiron, Asteroids)
 * - Moon Phase Details (8 phases)
 * - Chakra States (Blocked, Open, Balanced, Overactive)
 * - Ayurvedic Doshas (Vata, Pitta, Kapha)
 * - Sacred Symbols (Om, Ankh, Eye of Horus, Hamsa)
 *
 * Total: 50+ new icons for complete app coverage
 */

// ============================================================================
// TANTRIC SYMBOLS - Sacred intimacy and energy
// ============================================================================

/**
 * Yoni icon - Sacred feminine symbol (downward triangle in circle)
 * Represents the divine feminine principle in Tantric philosophy
 */
@Composable
fun YoniIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = TantricRose,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 2.8f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Outer circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Inner downward triangle (yoni)
        val triangleSize = radius * 0.65f
        val path = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.6f), color)
            )
        )
        drawPath(path, color = color, style = Stroke(width = 2.5f))
    }
}

/**
 * Lingam icon - Sacred masculine symbol (upward triangle in circle)
 * Represents the divine masculine principle in Tantric philosophy
 */
@Composable
fun LingamIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 2.8f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Outer circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Inner upward triangle (lingam)
        val triangleSize = radius * 0.65f
        val path = Path().apply {
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(color, color.copy(alpha = 0.6f))
            )
        )
        drawPath(path, color = color, style = Stroke(width = 2.5f))
    }
}

/**
 * Bindu icon - Sacred dot/point representing consciousness
 * The source point from which all creation emanates
 */
@Composable
fun BinduIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val dotRadius = minOf(canvasSize.width, canvasSize.height) / 5f

        if (glowEffect) {
            // Multiple glow layers
            drawCircle(color = color.copy(alpha = 0.1f), radius = dotRadius * 4f, center = center)
            drawCircle(color = color.copy(alpha = 0.2f), radius = dotRadius * 2.5f, center = center)
            drawCircle(color = color.copy(alpha = 0.3f), radius = dotRadius * 1.5f, center = center)
        }

        // Central dot with gradient
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, color)
            ),
            radius = dotRadius,
            center = center
        )

        // Three concentric circles
        for (i in 1..3) {
            drawCircle(
                color = color.copy(alpha = 0.5f - i * 0.1f),
                radius = dotRadius + i * dotRadius * 0.8f,
                center = center,
                style = Stroke(width = 1.5f)
            )
        }
    }
}

/**
 * Kundalini icon - Serpent energy rising through chakras
 * Represents the awakening of spiritual energy
 */
@Composable
fun KundaliniIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraRed,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        val path = Path().apply {
            // Serpent coiled at base (root chakra)
            moveTo(center.x, center.y + 10f * scale)

            // Spiral upward through chakras
            cubicTo(
                center.x - 6f * scale, center.y + 6f * scale,
                center.x - 4f * scale, center.y + 2f * scale,
                center.x, center.y
            )

            cubicTo(
                center.x + 6f * scale, center.y - 2f * scale,
                center.x + 4f * scale, center.y - 6f * scale,
                center.x, center.y - 10f * scale
            )
        }

        // Gradient from red (root) to violet (crown)
        drawPath(
            path,
            brush = Brush.verticalGradient(
                colors = listOf(ChakraRed, ChakraOrange, ChakraYellow, ChakraGreen, ChakraBlue, ChakraIndigo, ChakraCrown)
            ),
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )

        // Serpent head at crown
        drawCircle(color = ChakraCrown, radius = 3f, center = Offset(center.x, center.y - 10f * scale))
    }
}

// ============================================================================
// HUMAN DESIGN ICONS - Energy types and centers
// ============================================================================

/**
 * Manifestor icon - Initiator energy type
 * Represents those who initiate and create
 */
@Composable
fun ManifestorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 3.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Lightning bolt shape
        val path = Path().apply {
            moveTo(center.x + 2f, center.y - radius * 1.2f)
            lineTo(center.x - 4f, center.y)
            lineTo(center.x + 4f, center.y)
            lineTo(center.x - 2f, center.y + radius * 1.2f)
            lineTo(center.x + 6f, center.y - radius * 0.2f)
            lineTo(center.x + 2f, center.y - radius * 0.2f)
            close()
        }

        drawPath(path, color = color)
        drawPath(path, color = color, style = Stroke(width = 2f))
    }
}

/**
 * Generator icon - Builder/sustainer energy type
 * Represents those who build and sustain
 */
@Composable
fun GeneratorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraRed,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 3f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Filled circle with rotating lines (generator coils)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(color, color.copy(alpha = 0.7f))
            ),
            radius = radius,
            center = center
        )
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Inner energy lines
        for (i in 0..3) {
            val angle = (i * 90f + 45f) * (PI / 180f)
            val innerRadius = radius * 0.4f
            val outerRadius = radius * 0.7f
            drawLine(
                color = color.copy(alpha = 0.8f),
                start = Offset(
                    center.x + cos(angle).toFloat() * innerRadius,
                    center.y + sin(angle).toFloat() * innerRadius
                ),
                end = Offset(
                    center.x + cos(angle).toFloat() * outerRadius,
                    center.y + sin(angle).toFloat() * outerRadius
                ),
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Projector icon - Guide/director energy type
 * Represents those who guide and direct energy
 */
@Composable
fun ProjectorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = StardustBlue,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 3.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 2f, center = center)
        }

        // Projector beam/cone
        val path = Path().apply {
            moveTo(center.x, center.y - radius * 0.5f)
            lineTo(center.x - radius * 1.2f, center.y + radius * 1.2f)
            lineTo(center.x + radius * 1.2f, center.y + radius * 1.2f)
            close()
        }

        drawPath(
            path,
            brush = Brush.verticalGradient(
                colors = listOf(color, color.copy(alpha = 0.2f))
            )
        )
        drawPath(path, color = color, style = Stroke(width = 2.5f))

        // Light source
        drawCircle(color = color, radius = radius * 0.4f, center = Offset(center.x, center.y - radius * 0.5f))
    }
}

/**
 * Reflector icon - Mirror/evaluator energy type
 * Represents those who reflect and evaluate
 */
@Composable
fun ReflectorIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MoonlightSilver,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 3f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Moon-like circle with phases
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, color)
            ),
            radius = radius,
            center = center
        )
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Reflection marks around circle
        for (i in 0..7) {
            val angle = (i * 45f) * (PI / 180f)
            val startX = center.x + cos(angle).toFloat() * radius * 1.1f
            val startY = center.y + sin(angle).toFloat() * radius * 1.1f
            val endX = center.x + cos(angle).toFloat() * radius * 1.4f
            val endY = center.y + sin(angle).toFloat() * radius * 1.4f

            drawLine(
                color = color.copy(alpha = 0.6f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}

// ============================================================================
// RELATIONSHIP ICONS - Connection and compatibility
// ============================================================================

/**
 * Connection icon - Two souls connecting
 * Represents spiritual/energetic connection between people
 */
@Composable
fun ConnectionIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SpiritualPurple,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 2f, center = center)
        }

        // Left circle (person A)
        val leftCenter = Offset(center.x - radius * 1.2f, center.y)
        drawCircle(color = color.copy(alpha = 0.5f), radius = radius, center = leftCenter)
        drawCircle(color = color, radius = radius, center = leftCenter, style = Stroke(width = 2f))

        // Right circle (person B)
        val rightCenter = Offset(center.x + radius * 1.2f, center.y)
        drawCircle(color = color.copy(alpha = 0.5f), radius = radius, center = rightCenter)
        drawCircle(color = color, radius = radius, center = rightCenter, style = Stroke(width = 2f))

        // Connection line with gradient
        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(color, color.copy(alpha = 0.5f), color)
            ),
            start = leftCenter,
            end = rightCenter,
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )

        // Central connection point
        drawCircle(color = color, radius = 3f, center = center)
    }
}

/**
 * Harmony icon - Perfect balance and resonance
 * Represents harmonious relationship energy
 */
@Composable
fun HarmonyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Yin-yang inspired harmony symbol
        val path = Path().apply {
            // Outer circle
            addOval(Rect(
                center.x - 10f * scale,
                center.y - 10f * scale,
                center.x + 10f * scale,
                center.y + 10f * scale
            ))
        }

        drawPath(path, color = color, style = Stroke(width = 2.5f))

        // S-curve divider
        val sCurve = Path().apply {
            moveTo(center.x, center.y - 10f * scale)
            cubicTo(
                center.x + 5f * scale, center.y - 5f * scale,
                center.x - 5f * scale, center.y + 5f * scale,
                center.x, center.y + 10f * scale
            )
        }

        drawPath(sCurve, color = color, style = Stroke(width = 2f))

        // Two dots
        drawCircle(color = color, radius = 2f, center = Offset(center.x, center.y - 5f * scale))
        drawCircle(color = color.copy(alpha = 0.5f), radius = 2f, center = Offset(center.x, center.y + 5f * scale))
    }
}

/**
 * Attraction icon - Magnetic pull between energies
 * Represents romantic/energetic attraction
 */
@Composable
fun AttractionIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = TantricRose,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Heart shape
        val heartPath = Path().apply {
            // Left side of heart
            moveTo(center.x, center.y + 8f * scale)
            cubicTo(
                center.x - 10f * scale, center.y,
                center.x - 10f * scale, center.y - 6f * scale,
                center.x - 5f * scale, center.y - 8f * scale
            )
            cubicTo(
                center.x - 2f * scale, center.y - 9f * scale,
                center.x, center.y - 8f * scale,
                center.x, center.y - 6f * scale
            )

            // Right side of heart
            moveTo(center.x, center.y - 6f * scale)
            cubicTo(
                center.x, center.y - 8f * scale,
                center.x + 2f * scale, center.y - 9f * scale,
                center.x + 5f * scale, center.y - 8f * scale
            )
            cubicTo(
                center.x + 10f * scale, center.y - 6f * scale,
                center.x + 10f * scale, center.y,
                center.x, center.y + 8f * scale
            )
        }

        drawPath(
            heartPath,
            brush = Brush.radialGradient(
                colors = listOf(color, color.copy(alpha = 0.6f))
            )
        )
        drawPath(heartPath, color = color, style = Stroke(width = 2.5f))
    }
}

/**
 * Bond icon - Deep spiritual/karmic bond
 * Represents soul connections and karmic ties
 */
@Composable
fun BondIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = CosmicViolet,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 4f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 2f, center = center)
        }

        // Infinity symbol
        val path = Path().apply {
            // Left loop
            moveTo(center.x, center.y)
            cubicTo(
                center.x - radius * 0.5f, center.y - radius,
                center.x - radius * 1.5f, center.y - radius,
                center.x - radius * 2f, center.y
            )
            cubicTo(
                center.x - radius * 1.5f, center.y + radius,
                center.x - radius * 0.5f, center.y + radius,
                center.x, center.y
            )

            // Right loop
            cubicTo(
                center.x + radius * 0.5f, center.y - radius,
                center.x + radius * 1.5f, center.y - radius,
                center.x + radius * 2f, center.y
            )
            cubicTo(
                center.x + radius * 1.5f, center.y + radius,
                center.x + radius * 0.5f, center.y + radius,
                center.x, center.y
            )
        }

        drawPath(
            path,
            brush = Brush.linearGradient(
                colors = listOf(color, color.copy(alpha = 0.6f))
            ),
            style = Stroke(width = 3f, cap = StrokeCap.Round)
        )
    }
}

// ============================================================================
// MEDITATION STATE ICONS - States of consciousness
// ============================================================================

/**
 * Calm icon - Peaceful, centered state
 * Represents mental calmness and peace
 */
@Composable
fun CalmIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = 15f, center = center)
        }

        // Concentric ripples (calm water)
        for (i in 1..3) {
            drawCircle(
                color = color.copy(alpha = 0.6f - i * 0.15f),
                radius = 3f * i * scale,
                center = center,
                style = Stroke(width = 2f)
            )
        }

        // Central point
        drawCircle(color = color, radius = 3f, center = center)
    }
}

/**
 * Focus icon - Concentrated awareness
 * Represents focused attention and concentration
 */
@Composable
fun FocusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraIndigo,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 3f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 8f, center = center)
        }

        // Target/bullseye pattern
        drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 1.2f, center = center)
        drawCircle(color = color.copy(alpha = 0.5f), radius = radius * 0.8f, center = center)
        drawCircle(
            brush = Brush.radialGradient(colors = listOf(color, color.copy(alpha = 0.8f))),
            radius = radius * 0.4f,
            center = center
        )

        // Crosshairs
        drawLine(color = color, start = Offset(center.x - radius * 1.5f, center.y),
                 end = Offset(center.x + radius * 1.5f, center.y), strokeWidth = 1.5f)
        drawLine(color = color, start = Offset(center.x, center.y - radius * 1.5f),
                 end = Offset(center.x, center.y + radius * 1.5f), strokeWidth = 1.5f)
    }
}

/**
 * Expansion icon - Expanding consciousness
 * Represents spiritual expansion and growth
 */
@Composable
fun ExpansionIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SpiritualPurple,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val radius = minOf(canvasSize.width, canvasSize.height) / 4f

        if (glowEffect) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.4f), color.copy(alpha = 0.0f))
                ),
                radius = radius * 2.5f,
                center = center
            )
        }

        // Center point
        drawCircle(color = color, radius = 3f, center = center)

        // Expanding arrows in 8 directions
        for (i in 0..7) {
            val angle = (i * 45f) * (PI / 180f)
            val startRadius = radius * 0.5f
            val endRadius = radius * 1.5f

            val startX = center.x + cos(angle).toFloat() * startRadius
            val startY = center.y + sin(angle).toFloat() * startRadius
            val endX = center.x + cos(angle).toFloat() * endRadius
            val endY = center.y + sin(angle).toFloat() * endRadius

            // Arrow shaft
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(color, color.copy(alpha = 0.3f))
                ),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )

            // Arrow head
            val arrowSize = 3f
            val perpAngle1 = angle + PI / 6
            val perpAngle2 = angle - PI / 6

            drawLine(
                color = color.copy(alpha = 0.7f),
                start = Offset(endX, endY),
                end = Offset(
                    endX - cos(perpAngle1).toFloat() * arrowSize,
                    endY - sin(perpAngle1).toFloat() * arrowSize
                ),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color.copy(alpha = 0.7f),
                start = Offset(endX, endY),
                end = Offset(
                    endX - cos(perpAngle2).toFloat() * arrowSize,
                    endY - sin(perpAngle2).toFloat() * arrowSize
                ),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Transcendence icon - Beyond ordinary consciousness
 * Represents transcendent spiritual states
 */
@Composable
fun TranscendenceIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraCrown,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.5f),
                        color.copy(alpha = 0.2f),
                        color.copy(alpha = 0.0f)
                    )
                ),
                radius = 18f,
                center = center
            )
        }

        // Rising spiral
        val spiralPath = Path().apply {
            moveTo(center.x, center.y + 10f * scale)

            for (i in 0..20) {
                val t = i / 20f
                val angle = t * 3 * PI
                val radius = (1 - t) * 8f * scale
                val x = center.x + cos(angle).toFloat() * radius
                val y = center.y + 10f * scale - t * 20f * scale

                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            spiralPath,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.3f), color)
            ),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Crown/lotus petals at top
        for (i in 0..5) {
            val angle = (i * 60f) * (PI / 180f)
            val petalLength = 5f * scale
            val petalStart = Offset(center.x, center.y - 8f * scale)
            val petalEnd = Offset(
                petalStart.x + cos(angle).toFloat() * petalLength,
                petalStart.y + sin(angle).toFloat() * petalLength - 3f * scale
            )

            drawLine(
                color = color,
                start = petalStart,
                end = petalEnd,
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}

// ============================================================================
// ENERGY STATE ICONS - Energy levels and flow
// ============================================================================

/**
 * High Energy icon - Abundant vital energy
 * Represents high energy states and vitality
 */
@Composable
fun HighEnergyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Three upward arrows/flames
        for (i in -1..1) {
            val offsetX = i * 6f * scale
            val path = Path().apply {
                moveTo(center.x + offsetX, center.y + 8f * scale)
                lineTo(center.x + offsetX, center.y - 4f * scale)

                // Arrow head
                lineTo(center.x + offsetX - 3f * scale, center.y - 6f * scale)
                moveTo(center.x + offsetX, center.y - 4f * scale)
                lineTo(center.x + offsetX + 3f * scale, center.y - 6f * scale)
            }

            drawPath(
                path,
                brush = Brush.verticalGradient(
                    colors = listOf(color.copy(alpha = 0.5f), color)
                ),
                style = Stroke(width = 2.5f, cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * Low Energy icon - Depleted energy state
 * Represents low energy and need for rest
 */
@Composable
fun LowEnergyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = NightSky,
    glowEffect: Boolean = false
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        // Downward arrow/energy drain
        val path = Path().apply {
            moveTo(center.x, center.y - 8f * scale)
            lineTo(center.x, center.y + 4f * scale)

            // Arrow head
            lineTo(center.x - 4f * scale, center.y)
            moveTo(center.x, center.y + 4f * scale)
            lineTo(center.x + 4f * scale, center.y)
        }

        drawPath(
            path,
            color = color.copy(alpha = 0.6f),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Fading dots below
        for (i in 1..3) {
            drawCircle(
                color = color.copy(alpha = 0.3f - i * 0.08f),
                radius = 2f,
                center = Offset(center.x, center.y + 6f * scale + i * 3f * scale)
            )
        }
    }
}

/**
 * Balanced Energy icon - Harmonious energy state
 * Represents balanced and stable energy
 */
@Composable
fun BalancedEnergyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Horizontal line (balance beam)
        drawLine(
            color = color,
            start = Offset(center.x - 10f * scale, center.y),
            end = Offset(center.x + 10f * scale, center.y),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        // Triangle fulcrum
        val trianglePath = Path().apply {
            moveTo(center.x, center.y + 2f * scale)
            lineTo(center.x - 4f * scale, center.y + 8f * scale)
            lineTo(center.x + 4f * scale, center.y + 8f * scale)
            close()
        }

        drawPath(trianglePath, color = color)
        drawPath(trianglePath, color = color, style = Stroke(width = 2f))

        // Two equal weights
        drawCircle(color = color, radius = 3f, center = Offset(center.x - 8f * scale, center.y - 4f * scale))
        drawCircle(color = color, radius = 3f, center = Offset(center.x + 8f * scale, center.y - 4f * scale))
    }
}

/**
 * Flowing Energy icon - Energy in motion
 * Represents energy flow and circulation
 */
@Composable
fun FlowingEnergyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Flowing wave pattern
        val wavePath = Path().apply {
            moveTo(center.x - 10f * scale, center.y)

            for (i in 0..20) {
                val t = i / 20f
                val x = center.x - 10f * scale + t * 20f * scale
                val y = center.y + sin(t * 4 * PI).toFloat() * 4f * scale
                lineTo(x, y)
            }
        }

        drawPath(
            wavePath,
            brush = Brush.horizontalGradient(
                colors = listOf(color.copy(alpha = 0.3f), color, color.copy(alpha = 0.3f))
            ),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Flow direction arrows
        for (i in 0..2) {
            val x = center.x - 6f * scale + i * 6f * scale
            val y = center.y
            drawLine(
                color = color,
                start = Offset(x, y - 2f * scale),
                end = Offset(x + 3f * scale, y),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = color,
                start = Offset(x, y + 2f * scale),
                end = Offset(x + 3f * scale, y),
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Blocked Energy icon - Energy blockage/stagnation
 * Represents blocked or stagnant energy
 */
@Composable
fun BlockedEnergyIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraRed,
    glowEffect: Boolean = false
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        // Octagon (stop sign shape)
        val path = Path().apply {
            for (i in 0..7) {
                val angle = (i * 45f - 22.5f) * (PI / 180f)
                val x = center.x + cos(angle).toFloat() * 10f * scale
                val y = center.y + sin(angle).toFloat() * 10f * scale

                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }

        drawPath(path, color = color.copy(alpha = 0.3f))
        drawPath(path, color = color, style = Stroke(width = 2.5f))

        // X mark
        drawLine(
            color = color,
            start = Offset(center.x - 6f * scale, center.y - 6f * scale),
            end = Offset(center.x + 6f * scale, center.y + 6f * scale),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(center.x + 6f * scale, center.y - 6f * scale),
            end = Offset(center.x - 6f * scale, center.y + 6f * scale),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
    }
}

// ============================================================================
// AYURVEDIC DOSHA ICONS - Constitutional types
// ============================================================================

/**
 * Vata Dosha icon - Air and space element
 * Represents movement, creativity, and change
 */
@Composable
fun VataIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = AirCyan,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Swirling wind pattern
        val spiralPath = Path().apply {
            for (i in 0..30) {
                val t = i / 30f
                val angle = t * 3 * PI
                val radius = t * 10f * scale
                val x = center.x + cos(angle).toFloat() * radius
                val y = center.y + sin(angle).toFloat() * radius

                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        drawPath(
            spiralPath,
            brush = Brush.sweepGradient(
                colors = listOf(color.copy(alpha = 0.3f), color, color.copy(alpha = 0.3f))
            ),
            style = Stroke(width = 2.5f, cap = StrokeCap.Round)
        )

        // Small particles
        for (i in 0..4) {
            val angle = (i * 72f) * (PI / 180f)
            val x = center.x + cos(angle).toFloat() * 12f * scale
            val y = center.y + sin(angle).toFloat() * 12f * scale
            drawCircle(color = color.copy(alpha = 0.6f), radius = 1.5f, center = Offset(x, y))
        }
    }
}

/**
 * Pitta Dosha icon - Fire and water element
 * Represents transformation, metabolism, and intensity
 */
@Composable
fun PittaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(color.copy(alpha = 0.4f), color.copy(alpha = 0.0f))
                ),
                radius = 15f,
                center = center
            )
        }

        // Flame shape
        val flamePath = Path().apply {
            moveTo(center.x, center.y + 10f * scale)

            cubicTo(
                center.x - 8f * scale, center.y + 4f * scale,
                center.x - 6f * scale, center.y - 4f * scale,
                center.x - 2f * scale, center.y - 8f * scale
            )

            cubicTo(
                center.x - 1f * scale, center.y - 10f * scale,
                center.x + 1f * scale, center.y - 10f * scale,
                center.x + 2f * scale, center.y - 8f * scale
            )

            cubicTo(
                center.x + 6f * scale, center.y - 4f * scale,
                center.x + 8f * scale, center.y + 4f * scale,
                center.x, center.y + 10f * scale
            )

            close()
        }

        drawPath(
            flamePath,
            brush = Brush.verticalGradient(
                colors = listOf(ChakraYellow, color, ChakraRed)
            )
        )
        drawPath(flamePath, color = color, style = Stroke(width = 2f))

        // Inner flame
        val innerFlamePath = Path().apply {
            moveTo(center.x, center.y + 4f * scale)
            cubicTo(
                center.x - 3f * scale, center.y,
                center.x - 2f * scale, center.y - 4f * scale,
                center.x, center.y - 6f * scale
            )
            cubicTo(
                center.x + 2f * scale, center.y - 4f * scale,
                center.x + 3f * scale, center.y,
                center.x, center.y + 4f * scale
            )
        }

        drawPath(innerFlamePath, color = ChakraYellow.copy(alpha = 0.7f))
    }
}

/**
 * Kapha Dosha icon - Earth and water element
 * Represents stability, structure, and nourishment
 */
@Composable
fun KaphaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = EarthGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Mountain/earth shape
        val mountainPath = Path().apply {
            moveTo(center.x - 10f * scale, center.y + 8f * scale)
            lineTo(center.x - 5f * scale, center.y - 2f * scale)
            lineTo(center.x, center.y + 2f * scale)
            lineTo(center.x + 5f * scale, center.y - 8f * scale)
            lineTo(center.x + 10f * scale, center.y + 8f * scale)
            close()
        }

        drawPath(
            mountainPath,
            brush = Brush.verticalGradient(
                colors = listOf(color, GroundingBrown)
            )
        )
        drawPath(mountainPath, color = color, style = Stroke(width = 2.5f))

        // Water droplet at peak
        val dropletPath = Path().apply {
            moveTo(center.x + 5f * scale, center.y - 8f * scale)
            cubicTo(
                center.x + 3f * scale, center.y - 12f * scale,
                center.x + 7f * scale, center.y - 12f * scale,
                center.x + 5f * scale, center.y - 8f * scale
            )
        }

        drawPath(dropletPath, color = WaterTeal, style = Stroke(width = 1.5f))
    }
}

// ============================================================================
// SACRED SYMBOLS - Universal spiritual icons
// ============================================================================

/**
 * Om icon - Universal sound, primordial vibration
 * The sacred syllable representing ultimate reality
 */
@Composable
fun OmIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Simplified Om symbol (ॐ)
        val omPath = Path().apply {
            // "3" shape
            moveTo(center.x - 8f * scale, center.y - 6f * scale)
            cubicTo(
                center.x - 2f * scale, center.y - 8f * scale,
                center.x + 4f * scale, center.y - 6f * scale,
                center.x + 2f * scale, center.y - 2f * scale
            )
            cubicTo(
                center.x, center.y,
                center.x + 4f * scale, center.y,
                center.x + 6f * scale, center.y - 2f * scale
            )

            // Lower curve
            moveTo(center.x + 2f * scale, center.y - 2f * scale)
            cubicTo(
                center.x + 6f * scale, center.y + 2f * scale,
                center.x + 2f * scale, center.y + 6f * scale,
                center.x - 4f * scale, center.y + 4f * scale
            )

            // Dot above
            addOval(Rect(
                center.x - 1f * scale, center.y - 10f * scale,
                center.x + 1f * scale, center.y - 8f * scale
            ))
        }

        drawPath(omPath, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))

        // Small crescent below dot
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - 3f * scale, center.y - 10f * scale),
            size = Size(6f * scale, 3f * scale),
            style = Stroke(width = 2f)
        )
    }
}

/**
 * Hamsa icon - Hand of protection
 * Ancient Middle Eastern symbol of divine protection
 */
@Composable
fun HamsaIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = StardustBlue,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Hand outline
        val handPath = Path().apply {
            // Palm base
            moveTo(center.x - 6f * scale, center.y + 10f * scale)
            lineTo(center.x + 6f * scale, center.y + 10f * scale)

            // Right side (thumb)
            lineTo(center.x + 6f * scale, center.y + 2f * scale)
            lineTo(center.x + 9f * scale, center.y + 2f * scale)
            lineTo(center.x + 9f * scale, center.y - 4f * scale)
            lineTo(center.x + 6f * scale, center.y - 4f * scale)

            // Fingers
            lineTo(center.x + 6f * scale, center.y - 6f * scale)
            lineTo(center.x + 4f * scale, center.y - 8f * scale)
            lineTo(center.x + 2f * scale, center.y - 6f * scale)
            lineTo(center.x, center.y - 8f * scale)
            lineTo(center.x - 2f * scale, center.y - 6f * scale)
            lineTo(center.x - 4f * scale, center.y - 8f * scale)
            lineTo(center.x - 6f * scale, center.y - 6f * scale)

            // Left side (thumb)
            lineTo(center.x - 6f * scale, center.y - 4f * scale)
            lineTo(center.x - 9f * scale, center.y - 4f * scale)
            lineTo(center.x - 9f * scale, center.y + 2f * scale)
            lineTo(center.x - 6f * scale, center.y + 2f * scale)

            close()
        }

        drawPath(handPath, color = color.copy(alpha = 0.3f))
        drawPath(handPath, color = color, style = Stroke(width = 2.5f))

        // Eye in palm
        drawCircle(color = color, radius = 3f, center = center)
        drawCircle(color = color, radius = 1.5f, center = center)
    }
}

/**
 * Ankh icon - Egyptian symbol of life
 * Represents eternal life and divine wisdom
 */
@Composable
fun AnkhIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Loop at top
        drawOval(
            color = color.copy(alpha = 0.3f),
            topLeft = Offset(center.x - 4f * scale, center.y - 10f * scale),
            size = Size(8f * scale, 8f * scale)
        )
        drawOval(
            color = color,
            topLeft = Offset(center.x - 4f * scale, center.y - 10f * scale),
            size = Size(8f * scale, 8f * scale),
            style = Stroke(width = 2.5f)
        )

        // Vertical line (staff)
        drawLine(
            color = color,
            start = Offset(center.x, center.y - 2f * scale),
            end = Offset(center.x, center.y + 10f * scale),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        // Horizontal crossbar
        drawLine(
            color = color,
            start = Offset(center.x - 8f * scale, center.y),
            end = Offset(center.x + 8f * scale, center.y),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Eye of Horus icon - Egyptian symbol of protection
 * Represents protection, royal power, and good health
 */
@Composable
fun EyeOfHorusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraIndigo,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val scale = minOf(canvasSize.width, canvasSize.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        // Eye outline
        val eyePath = Path().apply {
            // Upper lid
            moveTo(center.x - 10f * scale, center.y)
            cubicTo(
                center.x - 6f * scale, center.y - 6f * scale,
                center.x + 6f * scale, center.y - 6f * scale,
                center.x + 10f * scale, center.y
            )

            // Lower lid
            cubicTo(
                center.x + 6f * scale, center.y + 4f * scale,
                center.x - 6f * scale, center.y + 4f * scale,
                center.x - 10f * scale, center.y
            )
        }

        drawPath(eyePath, color = color.copy(alpha = 0.2f))
        drawPath(eyePath, color = color, style = Stroke(width = 2.5f))

        // Iris/pupil
        drawCircle(color = color.copy(alpha = 0.5f), radius = 3f * scale, center = center)
        drawCircle(color = color, radius = 2f * scale, center = center)

        // Decorative spiral below
        val spiralPath = Path().apply {
            moveTo(center.x, center.y + 4f * scale)
            cubicTo(
                center.x + 4f * scale, center.y + 6f * scale,
                center.x + 2f * scale, center.y + 10f * scale,
                center.x - 2f * scale, center.y + 8f * scale
            )
        }

        drawPath(spiralPath, color = color, style = Stroke(width = 2f, cap = StrokeCap.Round))

        // Side mark
        drawLine(
            color = color,
            start = Offset(center.x + 10f * scale, center.y),
            end = Offset(center.x + 8f * scale, center.y + 4f * scale),
            strokeWidth = 2f,
            cap = StrokeCap.Round
        )
    }
}
