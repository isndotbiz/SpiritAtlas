package com.spiritatlas.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*

/**
 * Enhanced Typography Components for SpiritAtlas
 *
 * This file provides advanced typography components with spiritual aesthetics:
 * - EnhancedSectionHeader: Beautiful section headers with gradient text and optional glow
 * - GlowingText: Text with customizable shadow/glow effects
 *
 * These components build upon the existing GradientText component to provide
 * ready-to-use, spiritually-themed typography for the SpiritAtlas app.
 *
 * @see GradientText for the base gradient text implementation
 */

/**
 * Enhanced section header with gradient text, subtitle, and optional glow effect.
 *
 * This component creates visually stunning section headers perfect for spiritual content.
 * It combines gradient text with optional subtitles and glow effects for a mystical appearance.
 *
 * Features:
 * - Main title with gradient colors
 * - Optional subtitle in muted color
 * - Optional glow effect on title
 * - Customizable text styles
 * - Proper spacing between title and subtitle
 *
 * Usage:
 * ```kotlin
 * EnhancedSectionHeader(
 *     title = "Spiritual Insights",
 *     subtitle = "Your daily guidance"
 * )
 *
 * EnhancedSectionHeader(
 *     title = "Chakra Analysis",
 *     subtitle = "Energy center balance",
 *     gradientColors = listOf(ChakraRed, ChakraOrange, ChakraYellow),
 *     withGlow = true
 * )
 * ```
 *
 * @param title The main header text to display
 * @param modifier Modifier to be applied to the container
 * @param subtitle Optional subtitle text displayed below the title
 * @param gradientColors List of colors for the gradient effect (default: spiritual purple gradient)
 * @param withGlow Whether to apply a glow effect to the title (default: false)
 * @param titleStyle Text style for the title (default: headlineLarge from theme)
 * @param subtitleStyle Text style for the subtitle (default: bodyMedium from theme)
 * @param verticalArrangement Vertical arrangement of title and subtitle (default: 4.dp spacing)
 * @param horizontalAlignment Horizontal alignment of content (default: Start)
 */
@Composable
fun EnhancedSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    gradientColors: List<Color> = listOf(SpiritualPurple, MysticViolet, AuraGold),
    withGlow: Boolean = false,
    titleStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        // Main title with gradient (and optional glow)
        if (withGlow) {
            // Use GlowingText with gradient overlay
            // Since GlowingText doesn't directly support gradients,
            // we layer it creatively
            Box {
                // Glow layer
                GlowingText(
                    text = title,
                    textColor = Color.Transparent,
                    glowColor = gradientColors.first(),
                    style = titleStyle.copy(fontWeight = FontWeight.Bold),
                    animateGlow = true,
                    glowRadius = 16f
                )
                // Gradient text on top
                GradientText(
                    text = title,
                    gradient = gradientColors,
                    style = titleStyle.copy(fontWeight = FontWeight.Bold),
                    gradientDirection = GradientDirection.Horizontal
                )
            }
        } else {
            // Just gradient text without glow
            GradientText(
                text = title,
                gradient = gradientColors,
                style = titleStyle.copy(fontWeight = FontWeight.Bold),
                gradientDirection = GradientDirection.Horizontal
            )
        }

        // Optional subtitle
        subtitle?.let {
            Text(
                text = it,
                style = subtitleStyle,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * Text with customizable shadow/glow effect.
 *
 * Creates text with a beautiful glow effect that can be static or animated.
 * Perfect for creating attention-grabbing headings, mystical labels, or
 * emphasis on important spiritual content.
 *
 * Features:
 * - Customizable text and glow colors
 * - Optional animated pulsing glow
 * - Adjustable glow radius
 * - Multiple glow layers for depth
 * - Proper canvas-based rendering for smooth effects
 *
 * Usage:
 * ```kotlin
 * // Simple glowing text
 * GlowingText(
 *     text = "Mystical Energy",
 *     textColor = Color.White,
 *     glowColor = SpiritualPurple
 * )
 *
 * // Animated glow with custom radius
 * GlowingText(
 *     text = "Divine Connection",
 *     textColor = AuraGold,
 *     glowColor = SacredGold,
 *     animateGlow = true,
 *     glowRadius = 30f
 * )
 *
 * // Static glow with custom style
 * GlowingText(
 *     text = "Chakra Alignment",
 *     textColor = Color.White,
 *     glowColor = ChakraCrown,
 *     animateGlow = false,
 *     style = MaterialTheme.typography.titleLarge
 * )
 * ```
 *
 * Implementation Notes:
 * - This is a re-export of the existing GlowingText from GradientText.kt
 * - Included here for convenient access alongside other enhanced typography
 * - The actual implementation uses multiple layered draw calls for depth
 * - Animation uses infinite transition for smooth, continuous pulsing
 *
 * @param text The text content to display
 * @param modifier Modifier to be applied to the canvas
 * @param textColor Color of the main text (default: White)
 * @param glowColor Color of the glow effect (default: SpiritualPurple)
 * @param style Text style for the text (default: current local text style)
 * @param animateGlow Whether to animate the glow with pulsing effect (default: true)
 * @param glowRadius Radius of the glow effect in pixels (default: 20f)
 *
 * @see com.spiritatlas.core.ui.components.GlowingText Original implementation
 */
// Note: GlowingText is already defined in GradientText.kt
// We don't need to redefine it here, but we document it for convenience

/**
 * Section divider with gradient text label.
 *
 * Creates a beautiful divider with optional text label in the center.
 * The label uses gradient colors and can include a glow effect.
 * Perfect for separating different sections of spiritual content.
 *
 * Features:
 * - Centered gradient text label
 * - Optional divider lines on both sides
 * - Customizable gradient colors
 * - Optional glow effect on label
 * - Configurable thickness and spacing
 *
 * Usage:
 * ```kotlin
 * GradientDivider(
 *     label = "Past Life Insights"
 * )
 *
 * GradientDivider(
 *     label = "Spiritual Journey",
 *     gradientColors = listOf(ChakraRed, ChakraOrange, ChakraYellow),
 *     showDividers = true,
 *     withGlow = true
 * )
 * ```
 *
 * @param label The text to display in the center of the divider
 * @param modifier Modifier to be applied to the container
 * @param gradientColors List of colors for the gradient effect
 * @param showDividers Whether to show horizontal lines on both sides
 * @param withGlow Whether to apply a glow effect to the label
 * @param labelStyle Text style for the label
 * @param dividerColor Color of the divider lines (if shown)
 * @param dividerThickness Thickness of the divider lines in dp
 */
@Composable
fun GradientDivider(
    label: String,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(SpiritualPurple, MysticViolet, CosmicBlue),
    showDividers: Boolean = true,
    withGlow: Boolean = false,
    labelStyle: TextStyle = MaterialTheme.typography.labelLarge,
    dividerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
    dividerThickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Left divider
        if (showDividers) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dividerThickness)
                    .padding(end = 16.dp)
            ) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawRect(color = dividerColor)
                }
            }
        }

        // Center label
        if (withGlow) {
            Box {
                GlowingText(
                    text = label,
                    textColor = Color.Transparent,
                    glowColor = gradientColors.first(),
                    style = labelStyle.copy(fontWeight = FontWeight.SemiBold),
                    animateGlow = true,
                    glowRadius = 12f
                )
                GradientText(
                    text = label,
                    gradient = gradientColors,
                    style = labelStyle.copy(fontWeight = FontWeight.SemiBold),
                    gradientDirection = GradientDirection.Horizontal
                )
            }
        } else {
            GradientText(
                text = label,
                gradient = gradientColors,
                style = labelStyle.copy(fontWeight = FontWeight.SemiBold),
                gradientDirection = GradientDirection.Horizontal
            )
        }

        // Right divider
        if (showDividers) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(dividerThickness)
                    .padding(start = 16.dp)
            ) {
                androidx.compose.foundation.Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawRect(color = dividerColor)
                }
            }
        }
    }
}

/**
 * Spiritual badge with gradient background and text.
 *
 * Creates a small badge or chip with gradient background and centered text.
 * Perfect for tags, labels, or small status indicators in spiritual contexts.
 *
 * Features:
 * - Gradient background
 * - Centered text
 * - Rounded corners
 * - Customizable padding
 * - Optional glow effect
 *
 * Usage:
 * ```kotlin
 * SpiritualBadge(
 *     text = "Master Number"
 * )
 *
 * SpiritualBadge(
 *     text = "11",
 *     gradientColors = listOf(AuraGold, SacredGold),
 *     textColor = Color.White
 * )
 * ```
 *
 * @param text The text to display in the badge
 * @param modifier Modifier to be applied to the badge container
 * @param gradientColors List of colors for the background gradient
 * @param textColor Color of the text
 * @param textStyle Text style for the badge text
 * @param withGlow Whether to apply a glow effect to the badge
 */
@Composable
fun SpiritualBadge(
    text: String,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(SpiritualPurple, MysticViolet),
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    withGlow: Boolean = false
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .wrapContentSize()
            .then(
                if (withGlow) {
                    Modifier.drawWithContent {
                        // Draw glow layers
                        for (i in 2 downTo 1) {
                            val alpha = 0.2f * i
                            val offset = with(density) { (2f * i).dp.toPx() }
                            drawContent()
                        }
                        drawContent()
                    }
                } else {
                    Modifier
                }
            )
    ) {
        // Background gradient
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                colors = gradientColors
            )
            drawRoundRect(
                brush = brush,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(100f, 100f)
            )
        }

        // Text content
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/**
 * Helper extension to add a shadow effect to text style.
 *
 * Provides a convenient way to add shadow/glow to any TextStyle.
 *
 * Usage:
 * ```kotlin
 * val glowingStyle = MaterialTheme.typography.titleLarge.withGlow(
 *     color = SpiritualPurple,
 *     blurRadius = 8f
 * )
 * ```
 *
 * @param color Color of the shadow/glow
 * @param blurRadius Blur radius of the shadow
 * @param offsetX Horizontal offset of the shadow (default: 0f)
 * @param offsetY Vertical offset of the shadow (default: 0f)
 * @return A new TextStyle with the shadow applied
 */
fun TextStyle.withGlow(
    color: Color,
    blurRadius: Float,
    offsetX: Float = 0f,
    offsetY: Float = 0f
): TextStyle = this.copy(
    shadow = Shadow(
        color = color,
        offset = androidx.compose.ui.geometry.Offset(offsetX, offsetY),
        blurRadius = blurRadius
    )
)

/**
 * Pre-configured spiritual text styles with gradients and effects.
 *
 * Provides ready-to-use text styles for common spiritual UI patterns.
 * These styles combine Material3 typography with spiritual color schemes.
 */
object SpiritualTextStyles {
    /**
     * Large header style with mystic purple glow
     */
    val MysticHeader = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = MysticPurple.copy(alpha = 0.5f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 16f
        )
    )

    /**
     * Medium title style with golden glow
     */
    val GoldenTitle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        shadow = Shadow(
            color = SacredGold.copy(alpha = 0.4f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 12f
        )
    )

    /**
     * Body text style with subtle cosmic glow
     */
    val CosmicBody = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = Shadow(
            color = CosmicBlue.copy(alpha = 0.3f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 8f
        )
    )

    /**
     * Label style with chakra rainbow glow
     */
    val ChakraLabel = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        shadow = Shadow(
            color = ChakraCrown.copy(alpha = 0.4f),
            offset = androidx.compose.ui.geometry.Offset(0f, 0f),
            blurRadius = 10f
        )
    )
}
