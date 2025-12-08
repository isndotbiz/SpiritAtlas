package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*

/**
 * Shimmer Loading Effects for SpiritAtlas
 *
 * Beautiful, performant shimmer animations with spiritual purple/violet gradients.
 * Uses hardware acceleration for smooth 60fps animations.
 * Supports both light and dark modes automatically.
 *
 * Example usage:
 * ```kotlin
 * if (isLoading) {
 *     ShimmerProfileCard()
 * } else {
 *     ActualProfileCard(profile)
 * }
 * ```
 */

/**
 * Shimmer theme configuration with spiritual colors
 */
object ShimmerTheme {
    /**
     * Light mode shimmer colors - subtle spiritual purple gradient
     */
    val shimmerColorsLight = listOf(
        Color(0xFFE9D5FF),  // Light lavender
        Color(0xFFD8B4FE),  // Medium lavender
        Color(0xFFC4B5FD),  // Spiritual purple light
        Color(0xFFD8B4FE),  // Medium lavender (return)
        Color(0xFFE9D5FF)   // Light lavender (return)
    )

    /**
     * Dark mode shimmer colors - deeper spiritual purple gradient
     */
    val shimmerColorsDark = listOf(
        Color(0xFF1F1B2E),  // Deep violet dark
        Color(0xFF2D2440),  // Medium violet dark
        Color(0xFF3D2E52),  // Spiritual purple dark
        Color(0xFF2D2440),  // Medium violet dark (return)
        Color(0xFF1F1B2E)   // Deep violet dark (return)
    )

    /**
     * Animation duration in milliseconds
     * 1300ms provides smooth, noticeable shimmer without being distracting
     */
    const val animationDuration = 1300

    /**
     * Shimmer gradient width multiplier
     * Controls how wide the shimmer band is relative to the component
     */
    const val shimmerWidth = 1.5f
}

/**
 * Creates an infinite shimmer animation for the gradient offset
 */
@Composable
fun rememberShimmerAnimation(
    durationMillis: Int = ShimmerTheme.animationDuration
): State<Float> {
    val transition = rememberInfiniteTransition(label = "shimmer")
    return transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_offset"
    )
}

/**
 * Creates a shimmer brush based on current theme
 */
@Composable
fun getShimmerBrush(
    animationOffset: Float,
    isDarkTheme: Boolean = MaterialTheme.colorScheme.background == Color.Black
): Brush {
    val shimmerColors = if (isDarkTheme) {
        ShimmerTheme.shimmerColorsDark
    } else {
        ShimmerTheme.shimmerColorsLight
    }

    // Calculate the shimmer position
    val offset = animationOffset * 2000f // Movement range

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(offset - 1000f, offset - 1000f),
        end = Offset(offset + 1000f, offset + 1000f)
    )
}

/**
 * Base shimmer box component
 *
 * @param modifier Modifier for the shimmer box
 * @param shape Shape of the shimmer (default: rounded rectangle)
 * @param width Width of the shimmer box (null = fill max width)
 * @param height Height of the shimmer box
 * @param animationDuration Duration of shimmer animation in milliseconds
 */
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    width: Dp? = null,
    height: Dp = 100.dp,
    animationDuration: Int = ShimmerTheme.animationDuration
) {
    val animationOffset by rememberShimmerAnimation(animationDuration)
    val shimmerBrush = getShimmerBrush(animationOffset)

    Box(
        modifier = modifier
            .then(
                if (width != null) Modifier.width(width)
                else Modifier.fillMaxWidth()
            )
            .height(height)
            .clip(shape)
            .background(shimmerBrush)
    )
}

/**
 * Shimmer placeholder for text content
 *
 * @param modifier Modifier for the shimmer text
 * @param lines Number of text lines to show
 * @param lineHeight Height of each text line
 * @param lastLineWidth Width of the last line (null = same as others)
 */
@Composable
fun ShimmerText(
    modifier: Modifier = Modifier,
    lines: Int = 3,
    lineHeight: Dp = 16.dp,
    lineSpacing: Dp = 8.dp,
    lastLineWidth: Dp? = null
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(lineSpacing)
    ) {
        repeat(lines) { index ->
            val isLastLine = index == lines - 1
            val lineWidth = if (isLastLine && lastLineWidth != null) {
                lastLineWidth
            } else {
                null
            }

            Box(
                modifier = Modifier
                    .then(
                        if (lineWidth != null) Modifier.width(lineWidth)
                        else Modifier.fillMaxWidth()
                    )
                    .height(lineHeight)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
        }
    }
}

/**
 * Generic shimmer card placeholder
 *
 * @param modifier Modifier for the card
 * @param height Height of the card
 * @param contentPadding Internal padding for content
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    contentPadding: Dp = 16.dp
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(shimmerBrush)
    )
}

/**
 * Shimmer placeholder specifically designed for profile cards
 * Mimics the structure of a typical profile card with avatar and text
 *
 * @param modifier Modifier for the profile card
 * @param showAvatar Whether to show the avatar placeholder
 * @param avatarSize Size of the avatar placeholder
 */
@Composable
fun ShimmerProfileCard(
    modifier: Modifier = Modifier,
    showAvatar: Boolean = true,
    avatarSize: Dp = 64.dp
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar shimmer
                if (showAvatar) {
                    Box(
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .background(shimmerBrush)
                    )
                }

                // Name and subtitle shimmers
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Name shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(shimmerBrush)
                    )

                    // Subtitle shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(shimmerBrush)
                    )
                }
            }

            // Content lines shimmer
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(if (index == 2) 0.7f else 1f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(shimmerBrush)
                    )
                }
            }

            // Action buttons shimmer
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(shimmerBrush)
                    )
                }
            }
        }
    }
}

/**
 * Shimmer placeholder for list items
 * Perfect for LazyColumn/LazyRow loading states
 *
 * @param modifier Modifier for the list item
 * @param showLeadingIcon Whether to show leading icon placeholder
 * @param showTrailingIcon Whether to show trailing icon placeholder
 * @param iconSize Size of the icon placeholders
 */
@Composable
fun ShimmerListItem(
    modifier: Modifier = Modifier,
    showLeadingIcon: Boolean = true,
    showTrailingIcon: Boolean = false,
    iconSize: Dp = 40.dp
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading icon
        if (showLeadingIcon) {
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .background(shimmerBrush)
            )
        }

        // Text content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Title
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(18.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )

            // Subtitle
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush)
            )
        }

        // Trailing icon
        if (showTrailingIcon) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(shimmerBrush)
            )
        }
    }
}

/**
 * Circular shimmer placeholder for avatars/profile pictures
 *
 * @param modifier Modifier for the avatar
 * @param size Size of the circular avatar
 * @param hasBorder Whether to show a border around the avatar
 * @param borderWidth Width of the border if shown
 */
@Composable
fun ShimmerAvatar(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    hasBorder: Boolean = false,
    borderWidth: Dp = 2.dp
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Box(
        modifier = modifier
            .size(size)
            .then(
                if (hasBorder) {
                    Modifier.padding(borderWidth)
                } else {
                    Modifier
                }
            )
            .clip(CircleShape)
            .background(shimmerBrush)
    )
}

/**
 * Shimmer placeholder for buttons
 * Matches the shape and size of typical buttons
 *
 * @param modifier Modifier for the button
 * @param width Width of the button (null = fill max width)
 * @param height Height of the button
 * @param cornerRadius Corner radius of the button
 */
@Composable
fun ShimmerButton(
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp = 56.dp,
    cornerRadius: Dp = 28.dp
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Box(
        modifier = modifier
            .then(
                if (width != null) Modifier.width(width)
                else Modifier.fillMaxWidth()
            )
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(shimmerBrush)
    )
}

/**
 * Specialized shimmer for spiritual profile sections
 * Includes sections for astrology, numerology, and other spiritual data
 *
 * @param modifier Modifier for the spiritual profile
 * @param showAvatar Whether to show avatar placeholder
 * @param sections Number of content sections to display
 */
@Composable
fun ShimmerSpiritualProfile(
    modifier: Modifier = Modifier,
    showAvatar: Boolean = true,
    sections: Int = 3
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header with avatar
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showAvatar) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(shimmerBrush)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(shimmerBrush)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmerBrush)
                )
            }
        }

        // Spiritual data sections
        repeat(sections) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Section title
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmerBrush)
                )

                // Section content grid
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(shimmerBrush)
                        )
                    }
                }
            }
        }

        // Progress indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(shimmerBrush)
        )
    }
}

/**
 * Shimmer placeholder for chart/graph components
 *
 * @param modifier Modifier for the chart
 * @param height Height of the chart
 * @param bars Number of bars to show in the chart
 */
@Composable
fun ShimmerChart(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp,
    bars: Int = 7
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Chart title
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(shimmerBrush)
        )

        // Chart bars
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(bars) { index ->
                val heightFraction = (0.4f + (index % 3) * 0.2f)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(heightFraction)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .background(shimmerBrush)
                )
            }
        }
    }
}

/**
 * Shimmer placeholder for compatibility cards
 * Shows two profiles with a connection indicator
 *
 * @param modifier Modifier for the compatibility card
 */
@Composable
fun ShimmerCompatibilityCard(
    modifier: Modifier = Modifier
) {
    val animationOffset by rememberShimmerAnimation()
    val shimmerBrush = getShimmerBrush(animationOffset)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Two profile sections
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(2) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(shimmerBrush)
                    )

                    // Name
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(shimmerBrush)
                    )
                }
            }
        }

        // Compatibility score circle
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(shimmerBrush)
        )

        // Description lines
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(if (index == 1) 0.8f else 1f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(shimmerBrush)
                )
            }
        }
    }
}
