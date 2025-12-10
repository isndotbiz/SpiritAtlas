package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.*

/**
 * SpiritAtlas Spiritual Icons System
 *
 * A comprehensive collection of beautiful, hand-crafted vector icons for spiritual themes.
 * All icons are drawn using Canvas API with smooth paths, gradients, and subtle glow effects.
 *
 * Features:
 * - Customizable size and colors
 * - Gradient support for enhanced visual appeal
 * - Sacred geometry with proper mathematical precision
 * - Consistent visual style across all icons
 *
 * Categories:
 * - Profile Types (Masculine, Feminine, Non-binary)
 * - Zodiac Signs (All 12 signs)
 * - Chakras (7 energy centers)
 * - Elements (Fire, Water, Earth, Air, Spirit)
 * - Moon Phases (New, Waxing, Full, Waning)
 * - Sacred Geometry (Flower of Life, Metatron's Cube, Sri Yantra)
 * - Planets (Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn)
 */

// ============================================================================
// PROFILE TYPE ICONS - Energy archetypes
// ============================================================================

/**
 * Masculine energy icon - Upward-pointing triangle representing fire/yang energy
 *
 * @param modifier Modifier for the icon
 * @param size Size of the icon (default 24.dp)
 * @param color Primary color (default FireOrange)
 * @param glowEffect Whether to add subtle glow (default true)
 */
@Composable
fun MasculineIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val triangleSize = minOf(canvasSize.width, canvasSize.height) * 0.7f

        val path = Path().apply {
            // Upward-pointing equilateral triangle
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        // Glow effect
        if (glowEffect) {
            drawPath(
                path = path,
                color = color.copy(alpha = 0.3f),
                style = Stroke(width = 8f)
            )
        }

        // Main triangle with gradient
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(color, color.copy(alpha = 0.8f))
            )
        )

        // Outline
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3f)
        )
    }
}

/**
 * Feminine energy icon - Downward-pointing triangle representing water/yin energy
 *
 * @param modifier Modifier for the icon
 * @param size Size of the icon (default 24.dp)
 * @param color Primary color (default WaterTeal)
 * @param glowEffect Whether to add subtle glow (default true)
 */
@Composable
fun FeminineIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val triangleSize = minOf(canvasSize.width, canvasSize.height) * 0.7f

        val path = Path().apply {
            // Downward-pointing equilateral triangle
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        // Glow effect
        if (glowEffect) {
            drawPath(
                path = path,
                color = color.copy(alpha = 0.3f),
                style = Stroke(width = 8f)
            )
        }

        // Main triangle with gradient
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.8f), color)
            )
        )

        // Outline
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3f)
        )
    }
}

/**
 * Non-binary energy icon - Balanced union of masculine and feminine (hexagram)
 *
 * @param modifier Modifier for the icon
 * @param size Size of the icon (default 24.dp)
 * @param colorMasculine Color for upward triangle (default FireOrange)
 * @param colorFeminine Color for downward triangle (default WaterTeal)
 * @param glowEffect Whether to add subtle glow (default true)
 */
@Composable
fun NonBinaryIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    colorMasculine: Color = FireOrange,
    colorFeminine: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size
        val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
        val triangleSize = minOf(canvasSize.width, canvasSize.height) * 0.7f

        // Upward-pointing triangle (masculine)
        val upPath = Path().apply {
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        // Downward-pointing triangle (feminine)
        val downPath = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        // Glow effects
        if (glowEffect) {
            drawPath(upPath, color = colorMasculine.copy(alpha = 0.3f), style = Stroke(width = 6f))
            drawPath(downPath, color = colorFeminine.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }

        // Draw both triangles with transparency for overlap effect
        drawPath(upPath, color = colorMasculine.copy(alpha = 0.6f))
        drawPath(downPath, color = colorFeminine.copy(alpha = 0.6f))

        // Outlines
        drawPath(upPath, color = colorMasculine, style = Stroke(width = 2.5f))
        drawPath(downPath, color = colorFeminine, style = Stroke(width = 2.5f))
    }
}

// ============================================================================
// ZODIAC SIGN ICONS - All 12 astrological signs
// ============================================================================

/**
 * Aries icon - Ram horns symbol
 */
@Composable
fun AriesIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Left horn
            moveTo(center.x - 8f * scale, center.y + 6f * scale)
            cubicTo(
                center.x - 8f * scale, center.y,
                center.x - 6f * scale, center.y - 4f * scale,
                center.x - 3f * scale, center.y - 6f * scale
            )

            // Right horn
            moveTo(center.x + 8f * scale, center.y + 6f * scale)
            cubicTo(
                center.x + 8f * scale, center.y,
                center.x + 6f * scale, center.y - 4f * scale,
                center.x + 3f * scale, center.y - 6f * scale
            )

            // Center line
            moveTo(center.x, center.y - 6f * scale)
            lineTo(center.x, center.y + 8f * scale)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 3f, cap = StrokeCap.Round))
    }
}

/**
 * Taurus icon - Bull head and horns
 */
@Composable
fun TaurusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = EarthGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Circle (bull head)
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - 6f * scale,
                    center.y,
                    center.x + 6f * scale,
                    center.y + 12f * scale
                )
            )
        }

        // Horns
        val hornsPath = Path().apply {
            // Left horn
            moveTo(center.x - 10f * scale, center.y + 2f * scale)
            cubicTo(
                center.x - 10f * scale, center.y - 4f * scale,
                center.x - 8f * scale, center.y - 6f * scale,
                center.x - 6f * scale, center.y - 2f * scale
            )

            // Right horn
            moveTo(center.x + 10f * scale, center.y + 2f * scale)
            cubicTo(
                center.x + 10f * scale, center.y - 4f * scale,
                center.x + 8f * scale, center.y - 6f * scale,
                center.x + 6f * scale, center.y - 2f * scale
            )

            // Connect horns
            moveTo(center.x - 10f * scale, center.y + 2f * scale)
            lineTo(center.x + 10f * scale, center.y + 2f * scale)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
            drawPath(hornsPath, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }

        drawPath(path, color = color, style = Stroke(width = 2.5f))
        drawPath(hornsPath, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Gemini icon - Roman numeral II (twins)
 */
@Composable
fun GeminiIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = AirCyan,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Left vertical line
            moveTo(center.x - 5f * scale, center.y - 8f * scale)
            lineTo(center.x - 5f * scale, center.y + 8f * scale)

            // Right vertical line
            moveTo(center.x + 5f * scale, center.y - 8f * scale)
            lineTo(center.x + 5f * scale, center.y + 8f * scale)

            // Top horizontal
            moveTo(center.x - 8f * scale, center.y - 8f * scale)
            lineTo(center.x + 8f * scale, center.y - 8f * scale)

            // Bottom horizontal
            moveTo(center.x - 8f * scale, center.y + 8f * scale)
            lineTo(center.x + 8f * scale, center.y + 8f * scale)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 3f, cap = StrokeCap.Round))
    }
}

/**
 * Cancer icon - Crab claws (69 symbol)
 */
@Composable
fun CancerIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Top circle
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - 8f * scale,
                    center.y - 10f * scale,
                    center.x - 2f * scale,
                    center.y - 4f * scale
                )
            )

            // Top spiral
            moveTo(center.x - 2f * scale, center.y - 7f * scale)
            cubicTo(
                center.x + 4f * scale, center.y - 7f * scale,
                center.x + 4f * scale, center.y + 2f * scale,
                center.x - 2f * scale, center.y + 2f * scale
            )

            // Bottom circle
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x + 2f * scale,
                    center.y + 4f * scale,
                    center.x + 8f * scale,
                    center.y + 10f * scale
                )
            )

            // Bottom spiral
            moveTo(center.x + 2f * scale, center.y + 7f * scale)
            cubicTo(
                center.x - 4f * scale, center.y + 7f * scale,
                center.x - 4f * scale, center.y - 2f * scale,
                center.x + 2f * scale, center.y - 2f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Leo icon - Lion's mane and tail
 */
@Composable
fun LeoIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Circle (mane)
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - 8f * scale,
                    center.y - 8f * scale,
                    center.x - 2f * scale,
                    center.y - 2f * scale
                )
            )

            // Curve going down and around
            moveTo(center.x - 2f * scale, center.y - 5f * scale)
            cubicTo(
                center.x + 4f * scale, center.y - 5f * scale,
                center.x + 6f * scale, center.y + 2f * scale,
                center.x + 4f * scale, center.y + 6f * scale
            )

            // Tail curl
            cubicTo(
                center.x + 2f * scale, center.y + 8f * scale,
                center.x, center.y + 8f * scale,
                center.x - 2f * scale, center.y + 6f * scale
            )
            cubicTo(
                center.x - 4f * scale, center.y + 4f * scale,
                center.x - 2f * scale, center.y + 2f * scale,
                center.x + 2f * scale, center.y + 2f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Virgo icon - Maiden symbol with flourish
 */
@Composable
fun VirgoIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = EarthGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Left stem
            moveTo(center.x - 8f * scale, center.y - 8f * scale)
            lineTo(center.x - 8f * scale, center.y + 4f * scale)

            // Left loop
            cubicTo(
                center.x - 8f * scale, center.y + 8f * scale,
                center.x - 4f * scale, center.y + 8f * scale,
                center.x - 4f * scale, center.y + 4f * scale
            )
            lineTo(center.x - 4f * scale, center.y - 8f * scale)

            // Middle stem
            moveTo(center.x, center.y - 8f * scale)
            lineTo(center.x, center.y + 4f * scale)

            // Middle loop
            cubicTo(
                center.x, center.y + 8f * scale,
                center.x + 4f * scale, center.y + 8f * scale,
                center.x + 4f * scale, center.y + 4f * scale
            )
            lineTo(center.x + 4f * scale, center.y - 4f * scale)

            // Right flourish
            cubicTo(
                center.x + 6f * scale, center.y - 2f * scale,
                center.x + 8f * scale, center.y + 2f * scale,
                center.x + 6f * scale, center.y + 6f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Libra icon - Scales of justice
 */
@Composable
fun LibraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = AirCyan,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Top horizontal line
            moveTo(center.x - 8f * scale, center.y - 4f * scale)
            lineTo(center.x + 8f * scale, center.y - 4f * scale)

            // Bottom horizontal line (longer)
            moveTo(center.x - 10f * scale, center.y + 4f * scale)
            lineTo(center.x + 10f * scale, center.y + 4f * scale)

            // Center vertical support
            moveTo(center.x, center.y - 8f * scale)
            lineTo(center.x, center.y)

            // Left arc
            moveTo(center.x - 8f * scale, center.y - 4f * scale)
            cubicTo(
                center.x - 8f * scale, center.y,
                center.x - 6f * scale, center.y + 4f * scale,
                center.x - 4f * scale, center.y + 4f * scale
            )

            // Right arc
            moveTo(center.x + 8f * scale, center.y - 4f * scale)
            cubicTo(
                center.x + 8f * scale, center.y,
                center.x + 6f * scale, center.y + 4f * scale,
                center.x + 4f * scale, center.y + 4f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Scorpio icon - Scorpion tail with arrow
 */
@Composable
fun ScorpioIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Left stem
            moveTo(center.x - 8f * scale, center.y - 8f * scale)
            lineTo(center.x - 8f * scale, center.y + 4f * scale)

            // Left loop
            cubicTo(
                center.x - 8f * scale, center.y + 8f * scale,
                center.x - 4f * scale, center.y + 8f * scale,
                center.x - 4f * scale, center.y + 4f * scale
            )
            lineTo(center.x - 4f * scale, center.y - 8f * scale)

            // Middle stem
            moveTo(center.x, center.y - 8f * scale)
            lineTo(center.x, center.y + 4f * scale)

            // Middle loop
            cubicTo(
                center.x, center.y + 8f * scale,
                center.x + 4f * scale, center.y + 8f * scale,
                center.x + 4f * scale, center.y + 4f * scale
            )
            lineTo(center.x + 4f * scale, center.y - 4f * scale)

            // Arrow tail
            cubicTo(
                center.x + 6f * scale, center.y - 2f * scale,
                center.x + 8f * scale, center.y + 2f * scale,
                center.x + 10f * scale, center.y + 4f * scale
            )

            // Arrow point
            moveTo(center.x + 10f * scale, center.y + 4f * scale)
            lineTo(center.x + 9f * scale, center.y + 2f * scale)
            moveTo(center.x + 10f * scale, center.y + 4f * scale)
            lineTo(center.x + 8f * scale, center.y + 5f * scale)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Sagittarius icon - Archer's arrow
 */
@Composable
fun SagittariusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = FireOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Arrow shaft (diagonal)
            moveTo(center.x - 8f * scale, center.y + 8f * scale)
            lineTo(center.x + 8f * scale, center.y - 8f * scale)

            // Arrow head
            moveTo(center.x + 8f * scale, center.y - 8f * scale)
            lineTo(center.x + 4f * scale, center.y - 8f * scale)
            moveTo(center.x + 8f * scale, center.y - 8f * scale)
            lineTo(center.x + 8f * scale, center.y - 4f * scale)

            // Arrow feathers
            moveTo(center.x - 8f * scale, center.y + 8f * scale)
            lineTo(center.x - 6f * scale, center.y + 4f * scale)
            moveTo(center.x - 8f * scale, center.y + 8f * scale)
            lineTo(center.x - 4f * scale, center.y + 6f * scale)

            // Cross line
            moveTo(center.x - 4f * scale, center.y)
            lineTo(center.x + 4f * scale, center.y)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 3f, cap = StrokeCap.Round))
    }
}

/**
 * Capricorn icon - Goat with fish tail
 */
@Composable
fun CapricornIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = EarthGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Vertical stem
            moveTo(center.x - 4f * scale, center.y - 8f * scale)
            lineTo(center.x - 4f * scale, center.y + 8f * scale)

            // Horn curve
            moveTo(center.x - 4f * scale, center.y - 8f * scale)
            cubicTo(
                center.x - 8f * scale, center.y - 6f * scale,
                center.x - 8f * scale, center.y - 2f * scale,
                center.x - 4f * scale, center.y
            )

            // Fish tail
            moveTo(center.x - 4f * scale, center.y + 2f * scale)
            cubicTo(
                center.x + 2f * scale, center.y + 2f * scale,
                center.x + 4f * scale, center.y + 6f * scale,
                center.x + 2f * scale, center.y + 8f * scale
            )
            cubicTo(
                center.x, center.y + 10f * scale,
                center.x - 2f * scale, center.y + 8f * scale,
                center.x - 4f * scale, center.y + 6f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Aquarius icon - Water bearer waves
 */
@Composable
fun AquariusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = AirCyan,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Top wave
            moveTo(center.x - 10f * scale, center.y - 3f * scale)
            cubicTo(
                center.x - 6f * scale, center.y - 6f * scale,
                center.x - 2f * scale, center.y,
                center.x + 2f * scale, center.y - 3f * scale
            )
            cubicTo(
                center.x + 6f * scale, center.y - 6f * scale,
                center.x + 10f * scale, center.y,
                center.x + 10f * scale, center.y - 3f * scale
            )

            // Bottom wave
            moveTo(center.x - 10f * scale, center.y + 3f * scale)
            cubicTo(
                center.x - 6f * scale, center.y,
                center.x - 2f * scale, center.y + 6f * scale,
                center.x + 2f * scale, center.y + 3f * scale
            )
            cubicTo(
                center.x + 6f * scale, center.y,
                center.x + 10f * scale, center.y + 6f * scale,
                center.x + 10f * scale, center.y + 3f * scale
            )
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 3f, cap = StrokeCap.Round))
    }
}

/**
 * Pisces icon - Two fish connected
 */
@Composable
fun PiscesIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = WaterTeal,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        val path = Path().apply {
            // Left arc (fish)
            moveTo(center.x - 10f * scale, center.y - 8f * scale)
            cubicTo(
                center.x - 6f * scale, center.y - 4f * scale,
                center.x - 6f * scale, center.y + 4f * scale,
                center.x - 10f * scale, center.y + 8f * scale
            )

            // Right arc (fish)
            moveTo(center.x + 10f * scale, center.y - 8f * scale)
            cubicTo(
                center.x + 6f * scale, center.y - 4f * scale,
                center.x + 6f * scale, center.y + 4f * scale,
                center.x + 10f * scale, center.y + 8f * scale
            )

            // Connecting line
            moveTo(center.x - 6f * scale, center.y)
            lineTo(center.x + 6f * scale, center.y)
        }

        if (glowEffect) {
            drawPath(path, color = color.copy(alpha = 0.3f), style = Stroke(width = 6f))
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

// ============================================================================
// CHAKRA ICONS - Seven energy centers
// ============================================================================

/**
 * Root Chakra (Muladhara) - Four-petaled lotus with square
 */
@Composable
fun RootChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraRed,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        // Glow
        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        // Outer circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Inner square
        val squareSize = radius * 0.6f
        drawRect(
            color = color,
            topLeft = Offset(center.x - squareSize / 2f, center.y - squareSize / 2f),
            size = Size(squareSize, squareSize),
            style = Stroke(width = 2.5f)
        )

        // Four petals (simplified as lines)
        for (i in 0..3) {
            val angle = (i * 90f + 45f) * (PI / 180f)
            val startX = center.x + cos(angle).toFloat() * radius * 0.7f
            val startY = center.y + sin(angle).toFloat() * radius * 0.7f
            val endX = center.x + cos(angle).toFloat() * radius
            val endY = center.y + sin(angle).toFloat() * radius
            drawLine(color, Offset(startX, startY), Offset(endX, endY), strokeWidth = 2f)
        }
    }
}

/**
 * Sacral Chakra (Svadhisthana) - Six-petaled lotus with crescent moon
 */
@Composable
fun SacralChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraOrange,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Crescent moon
        val crescentRadius = radius * 0.5f
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - crescentRadius, center.y - crescentRadius),
            size = Size(crescentRadius * 2, crescentRadius * 2),
            style = Stroke(width = 2.5f)
        )

        // Six petals
        for (i in 0..5) {
            val angle = (i * 60f) * (PI / 180f)
            val petalX = center.x + cos(angle).toFloat() * radius
            val petalY = center.y + sin(angle).toFloat() * radius
            drawCircle(color = color, radius = 4f, center = Offset(petalX, petalY))
        }
    }
}

/**
 * Solar Plexus Chakra (Manipura) - Ten-petaled lotus with downward triangle
 */
@Composable
fun SolarPlexusChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraYellow,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Downward triangle
        val triangleSize = radius * 0.6f
        val path = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }
        drawPath(path, color = color, style = Stroke(width = 2.5f))
    }
}

/**
 * Heart Chakra (Anahata) - Twelve-petaled lotus with two triangles (hexagram)
 */
@Composable
fun HeartChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraGreen,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Hexagram (Star of David)
        val triangleSize = radius * 0.6f

        // Upward triangle
        val upPath = Path().apply {
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        // Downward triangle
        val downPath = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        drawPath(upPath, color = color, style = Stroke(width = 2f))
        drawPath(downPath, color = color, style = Stroke(width = 2f))
    }
}

/**
 * Throat Chakra (Vishuddha) - Sixteen-petaled lotus with circle and inverted triangle
 */
@Composable
fun ThroatChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraBlue,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Inner circle
        drawCircle(color = color, radius = radius * 0.5f, center = center, style = Stroke(width = 2f))

        // Downward triangle
        val triangleSize = radius * 0.4f
        val path = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }
        drawPath(path, color = color, style = Stroke(width = 2f))
    }
}

/**
 * Third Eye Chakra (Ajna) - Two-petaled lotus with Om symbol
 */
@Composable
fun ThirdEyeChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraIndigo,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Simplified Om/third eye symbol - dot and two petals
        drawCircle(color = color, radius = 4f, center = center)

        // Left petal
        drawArc(
            color = color,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - radius * 0.8f, center.y - radius * 0.3f),
            size = Size(radius * 0.6f, radius * 0.6f),
            style = Stroke(width = 2f)
        )

        // Right petal
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x + radius * 0.2f, center.y - radius * 0.3f),
            size = Size(radius * 0.6f, radius * 0.6f),
            style = Stroke(width = 2f)
        )
    }
}

/**
 * Crown Chakra (Sahasrara) - Thousand-petaled lotus (represented as radiating lines)
 */
@Composable
fun CrownChakraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraCrown,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Radiating lines (representing thousand petals)
        for (i in 0..11) {
            val angle = (i * 30f) * (PI / 180f)
            val startX = center.x + cos(angle).toFloat() * radius * 0.4f
            val startY = center.y + sin(angle).toFloat() * radius * 0.4f
            val endX = center.x + cos(angle).toFloat() * radius
            val endY = center.y + sin(angle).toFloat() * radius
            drawLine(color, Offset(startX, startY), Offset(endX, endY), strokeWidth = 2f)
        }

        // Center circle
        drawCircle(color = color, radius = radius * 0.2f, center = center)
    }
}

// ============================================================================
// ELEMENT ICONS - Fire, Water, Earth, Air, Spirit
// ============================================================================

/**
 * Fire Element icon - Upward-pointing triangle with flame gradient
 */
@Composable
fun FireElementIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val triangleSize = minOf(this.size.width, this.size.height) * 0.7f

        val path = Path().apply {
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        if (glowEffect) {
            drawPath(path, color = FireOrange.copy(alpha = 0.3f), style = Stroke(width = 8f))
        }

        // Gradient fill
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFFC107),  // Yellow
                    FireOrange,
                    ChakraRed
                )
            )
        )

        drawPath(path, color = FireOrange, style = Stroke(width = 3f))
    }
}

/**
 * Water Element icon - Downward-pointing triangle with water gradient
 */
@Composable
fun WaterElementIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val triangleSize = minOf(this.size.width, this.size.height) * 0.7f

        val path = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        if (glowEffect) {
            drawPath(path, color = WaterTeal.copy(alpha = 0.3f), style = Stroke(width = 8f))
        }

        // Gradient fill
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    StardustBlue,
                    WaterTeal,
                    DeepTeal
                )
            )
        )

        drawPath(path, color = WaterTeal, style = Stroke(width = 3f))
    }
}

/**
 * Earth Element icon - Downward-pointing triangle with horizontal line
 */
@Composable
fun EarthElementIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val triangleSize = minOf(this.size.width, this.size.height) * 0.7f

        val path = Path().apply {
            moveTo(center.x, center.y + triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y - triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y - triangleSize / 4f)
            close()
        }

        if (glowEffect) {
            drawPath(path, color = EarthGreen.copy(alpha = 0.3f), style = Stroke(width = 8f))
        }

        // Gradient fill
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF84CC16),  // Lime
                    EarthGreen,
                    GroundingBrown
                )
            )
        )

        drawPath(path, color = EarthGreen, style = Stroke(width = 3f))

        // Horizontal line
        drawLine(
            color = EarthGreen,
            start = Offset(center.x - triangleSize / 2.5f, center.y),
            end = Offset(center.x + triangleSize / 2.5f, center.y),
            strokeWidth = 3f
        )
    }
}

/**
 * Air Element icon - Upward-pointing triangle with horizontal line
 */
@Composable
fun AirElementIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val triangleSize = minOf(this.size.width, this.size.height) * 0.7f

        val path = Path().apply {
            moveTo(center.x, center.y - triangleSize / 2f)
            lineTo(center.x + triangleSize / 2f, center.y + triangleSize / 4f)
            lineTo(center.x - triangleSize / 2f, center.y + triangleSize / 4f)
            close()
        }

        if (glowEffect) {
            drawPath(path, color = AirCyan.copy(alpha = 0.3f), style = Stroke(width = 8f))
        }

        // Gradient fill
        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE0F7FA),  // Light cyan
                    AirCyan,
                    StardustBlue
                )
            )
        )

        drawPath(path, color = AirCyan, style = Stroke(width = 3f))

        // Horizontal line
        drawLine(
            color = AirCyan,
            start = Offset(center.x - triangleSize / 2.5f, center.y),
            end = Offset(center.x + triangleSize / 2.5f, center.y),
            strokeWidth = 3f
        )
    }
}

/**
 * Spirit Element icon - Circle with radiating points (quintessence)
 */
@Composable
fun SpiritElementIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 3f

        if (glowEffect) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        MysticPurple.copy(alpha = 0.4f),
                        MysticPurple.copy(alpha = 0.0f)
                    )
                ),
                radius = radius + 12f,
                center = center
            )
        }

        // Outer circle with gradient
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    SpiritualPurple,
                    MysticPurple
                )
            ),
            radius = radius,
            center = center
        )

        drawCircle(color = SpiritualPurple, radius = radius, center = center, style = Stroke(width = 3f))

        // Radiating points (8-pointed star)
        for (i in 0..7) {
            val angle = (i * 45f) * (PI / 180f)
            val startX = center.x + cos(angle).toFloat() * radius * 0.6f
            val startY = center.y + sin(angle).toFloat() * radius * 0.6f
            val endX = center.x + cos(angle).toFloat() * (radius + 8f)
            val endY = center.y + sin(angle).toFloat() * (radius + 8f)
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(SpiritualPurple, SpiritualPurple.copy(alpha = 0.3f))
                ),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )
        }

        // Center dot
        drawCircle(color = SpiritualPurple, radius = 4f, center = center)
    }
}

// ============================================================================
// MOON PHASE ICONS
// ============================================================================

/**
 * New Moon icon - Dark circle with subtle outline
 */
@Composable
fun NewMoonIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = NightSky,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(
                color = MoonlightSilver.copy(alpha = 0.2f),
                radius = radius + 6f,
                center = center
            )
        }

        drawCircle(color = color, radius = radius, center = center)
        drawCircle(
            color = MoonlightSilver.copy(alpha = 0.4f),
            radius = radius,
            center = center,
            style = Stroke(width = 2f)
        )
    }
}

/**
 * Waxing Crescent Moon icon
 */
@Composable
fun WaxingMoonIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MoonlightSilver,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 6f, center = center)
        }

        // Dark circle
        drawCircle(color = NightSky, radius = radius, center = center)

        // Crescent
        val crescentPath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - radius,
                    center.y - radius,
                    center.x + radius,
                    center.y + radius
                )
            )
        }

        val shadowPath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - radius * 0.5f,
                    center.y - radius,
                    center.x + radius * 1.5f,
                    center.y + radius
                )
            )
        }

        drawPath(crescentPath, color = color)
        drawPath(shadowPath, color = NightSky, blendMode = BlendMode.SrcIn)

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))
    }
}

/**
 * Full Moon icon - Bright circle with glow
 */
@Composable
fun FullMoonIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MoonlightSilver,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            // Multiple glow layers
            drawCircle(color = color.copy(alpha = 0.1f), radius = radius + 12f, center = center)
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 4f, center = center)
        }

        // Gradient fill
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.White,
                    color
                )
            ),
            radius = radius,
            center = center
        )

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))
    }
}

/**
 * Waning Crescent Moon icon
 */
@Composable
fun WaningMoonIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MoonlightSilver,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 6f, center = center)
        }

        // Dark circle
        drawCircle(color = NightSky, radius = radius, center = center)

        // Crescent (opposite side)
        val crescentPath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - radius,
                    center.y - radius,
                    center.x + radius,
                    center.y + radius
                )
            )
        }

        val shadowPath = Path().apply {
            addOval(
                androidx.compose.ui.geometry.Rect(
                    center.x - radius * 1.5f,
                    center.y - radius,
                    center.x + radius * 0.5f,
                    center.y + radius
                )
            )
        }

        drawPath(crescentPath, color = color)
        drawPath(shadowPath, color = NightSky, blendMode = BlendMode.SrcIn)

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))
    }
}

// ============================================================================
// SACRED GEOMETRY ICONS
// ============================================================================

/**
 * Flower of Life - Sacred geometric pattern
 */
@Composable
fun FlowerOfLifeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 6f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius * 3f, center = center)
        }

        // Center circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))

        // Six surrounding circles
        for (i in 0..5) {
            val angle = (i * 60f) * (PI / 180f)
            val circleCenter = Offset(
                center.x + cos(angle).toFloat() * radius,
                center.y + sin(angle).toFloat() * radius
            )
            drawCircle(color = color, radius = radius, center = circleCenter, style = Stroke(width = 2f))
        }

        // Outer ring circles (partial)
        for (i in 0..5) {
            val angle = (i * 60f + 30f) * (PI / 180f)
            val circleCenter = Offset(
                center.x + cos(angle).toFloat() * radius * 1.732f,
                center.y + sin(angle).toFloat() * radius * 1.732f
            )
            drawCircle(
                color = color.copy(alpha = 0.6f),
                radius = radius,
                center = circleCenter,
                style = Stroke(width = 1.5f)
            )
        }
    }
}

/**
 * Metatron's Cube - Complex sacred geometry
 */
@Composable
fun MetatronsCubeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SpiritualPurple,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 3f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        // Outer circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))

        // 13 circles of Metatron's Cube (simplified to 7 for clarity)
        val points = mutableListOf<Offset>()
        points.add(center)

        // Six points around center
        for (i in 0..5) {
            val angle = (i * 60f) * (PI / 180f)
            val point = Offset(
                center.x + cos(angle).toFloat() * radius * 0.6f,
                center.y + sin(angle).toFloat() * radius * 0.6f
            )
            points.add(point)
            drawCircle(color = color, radius = 3f, center = point)
        }

        // Connect all points
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                drawLine(
                    color = color.copy(alpha = 0.4f),
                    start = points[i],
                    end = points[j],
                    strokeWidth = 1.5f
                )
            }
        }

        // Center point
        drawCircle(color = color, radius = 4f, center = center)
    }
}

/**
 * Sri Yantra - Sacred tantric diagram
 */
@Composable
fun SriYantraIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 2.8f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        // Outer square (simplified)
        val squareSize = radius * 1.8f
        drawRect(
            color = color.copy(alpha = 0.6f),
            topLeft = Offset(center.x - squareSize / 2f, center.y - squareSize / 2f),
            size = Size(squareSize, squareSize),
            style = Stroke(width = 2f)
        )

        // Outer circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))

        // Nine interlocking triangles (simplified to 5 for clarity)
        val triangles = listOf(
            0.9f to true,   // Large upward
            0.7f to false,  // Large downward
            0.5f to true,   // Medium upward
            0.4f to false,  // Medium downward
            0.2f to true    // Small upward (center)
        )

        triangles.forEach { (scale, isUpward) ->
            val path = Path().apply {
                val triSize = radius * scale
                if (isUpward) {
                    moveTo(center.x, center.y - triSize)
                    lineTo(center.x + triSize * 0.866f, center.y + triSize / 2f)
                    lineTo(center.x - triSize * 0.866f, center.y + triSize / 2f)
                } else {
                    moveTo(center.x, center.y + triSize)
                    lineTo(center.x + triSize * 0.866f, center.y - triSize / 2f)
                    lineTo(center.x - triSize * 0.866f, center.y - triSize / 2f)
                }
                close()
            }
            drawPath(
                path = path,
                color = color.copy(alpha = 0.3f + scale * 0.4f),
                style = Stroke(width = 1.5f)
            )
        }

        // Center dot (bindu)
        drawCircle(color = color, radius = 4f, center = center)
    }
}

/**
 * Vesica Piscis - Two overlapping circles
 */
@Composable
fun VesicaPiscisIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = MysticPurple,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 3.5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.2f), radius = radius + 8f, center = center)
        }

        // Left circle
        val leftCenter = Offset(center.x - radius / 2f, center.y)
        drawCircle(color = color.copy(alpha = 0.3f), radius = radius, center = leftCenter)
        drawCircle(color = color, radius = radius, center = leftCenter, style = Stroke(width = 2f))

        // Right circle
        val rightCenter = Offset(center.x + radius / 2f, center.y)
        drawCircle(color = color.copy(alpha = 0.3f), radius = radius, center = rightCenter)
        drawCircle(color = color, radius = radius, center = rightCenter, style = Stroke(width = 2f))
    }
}

// ============================================================================
// PLANETARY ICONS
// ============================================================================

/**
 * Sun icon - Circle with rays
 */
@Composable
fun SunIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SacredGold,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 4f

        if (glowEffect) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.4f),
                        color.copy(alpha = 0.0f)
                    )
                ),
                radius = radius + 12f,
                center = center
            )
        }

        // Center circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFFF9C4),
                    color
                )
            ),
            radius = radius,
            center = center
        )

        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))

        // Rays
        for (i in 0..7) {
            val angle = (i * 45f) * (PI / 180f)
            val startX = center.x + cos(angle).toFloat() * (radius + 2f)
            val startY = center.y + sin(angle).toFloat() * (radius + 2f)
            val endX = center.x + cos(angle).toFloat() * (radius + 10f)
            val endY = center.y + sin(angle).toFloat() * (radius + 10f)

            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(color, color.copy(alpha = 0.3f))
                ),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }

        // Center dot
        drawCircle(color = color, radius = 3f, center = center)
    }
}

/**
 * Venus icon - Female symbol (circle with cross below)
 */
@Composable
fun VenusIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = TantricRose,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 6f, center = center)
        }

        // Circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2.5f))

        // Vertical line
        drawLine(
            color = color,
            start = Offset(center.x, center.y + radius),
            end = Offset(center.x, center.y + radius + 10f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        // Horizontal line
        drawLine(
            color = color,
            start = Offset(center.x - 6f, center.y + radius + 6f),
            end = Offset(center.x + 6f, center.y + radius + 6f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Mars icon - Male symbol (circle with arrow)
 */
@Composable
fun MarsIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = ChakraRed,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 5f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius + 6f, center = center)
        }

        // Circle
        val circleCenter = Offset(center.x - 3f, center.y + 3f)
        drawCircle(color = color, radius = radius, center = circleCenter, style = Stroke(width = 2.5f))

        // Arrow line
        val arrowStart = Offset(
            circleCenter.x + cos(-45f * PI / 180f).toFloat() * radius,
            circleCenter.y + sin(-45f * PI / 180f).toFloat() * radius
        )
        val arrowEnd = Offset(center.x + 10f, center.y - 10f)

        drawLine(
            color = color,
            start = arrowStart,
            end = arrowEnd,
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )

        // Arrow head
        drawLine(
            color = color,
            start = arrowEnd,
            end = Offset(arrowEnd.x - 6f, arrowEnd.y),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = arrowEnd,
            end = Offset(arrowEnd.x, arrowEnd.y + 6f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Mercury icon - Circle with cross above and crescent on top
 */
@Composable
fun MercuryIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = AirCyan,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val radius = minOf(this.size.width, this.size.height) / 6f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = radius * 2f, center = center)
        }

        // Circle
        drawCircle(color = color, radius = radius, center = center, style = Stroke(width = 2f))

        // Cross below
        drawLine(
            color = color,
            start = Offset(center.x, center.y + radius),
            end = Offset(center.x, center.y + radius + 8f),
            strokeWidth = 2f
        )
        drawLine(
            color = color,
            start = Offset(center.x - 5f, center.y + radius + 5f),
            end = Offset(center.x + 5f, center.y + radius + 5f),
            strokeWidth = 2f
        )

        // Crescent on top
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - radius * 0.7f, center.y - radius - 8f),
            size = Size(radius * 1.4f, radius * 0.8f),
            style = Stroke(width = 2f)
        )
    }
}

/**
 * Jupiter icon - Stylized "4" symbol
 */
@Composable
fun JupiterIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = SpiritualPurple,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        val path = Path().apply {
            // Vertical line
            moveTo(center.x + 4f * scale, center.y - 10f * scale)
            lineTo(center.x + 4f * scale, center.y + 10f * scale)

            // Horizontal line
            moveTo(center.x - 8f * scale, center.y)
            lineTo(center.x + 8f * scale, center.y)

            // Left curve
            moveTo(center.x - 6f * scale, center.y - 10f * scale)
            cubicTo(
                center.x - 2f * scale, center.y - 6f * scale,
                center.x - 2f * scale, center.y - 2f * scale,
                center.x + 4f * scale, center.y
            )
        }

        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}

/**
 * Saturn icon - Cross with curved tail
 */
@Composable
fun SaturnIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = NightSky,
    glowEffect: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val scale = minOf(this.size.width, this.size.height) / 24f

        if (glowEffect) {
            drawCircle(color = color.copy(alpha = 0.3f), radius = 15f, center = center)
        }

        val path = Path().apply {
            // Vertical line
            moveTo(center.x - 4f * scale, center.y - 10f * scale)
            lineTo(center.x - 4f * scale, center.y + 6f * scale)

            // Horizontal line
            moveTo(center.x - 10f * scale, center.y - 4f * scale)
            lineTo(center.x + 4f * scale, center.y - 4f * scale)

            // Curved tail
            moveTo(center.x - 4f * scale, center.y + 6f * scale)
            cubicTo(
                center.x, center.y + 10f * scale,
                center.x + 6f * scale, center.y + 8f * scale,
                center.x + 8f * scale, center.y + 4f * scale
            )
        }

        drawPath(path, color = color, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}
