package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*

/**
 * Skeleton screens for SpiritAtlas
 *
 * Beautiful, spiritually-themed skeleton loaders with shimmer effects
 * to replace generic loading spinners.
 *
 * Features:
 * 1. Spiritual gradient shimmer effect
 * 2. Profile card skeleton
 * 3. Compatibility report skeleton
 * 4. List item skeleton
 * 5. Chart/Graph skeleton
 * 6. Image placeholder skeleton
 */

// ============================================================================
// SPIRITUAL SHIMMER EFFECT
// ============================================================================

/**
 * Spiritual shimmer animation with chakra colors
 */
@Composable
fun rememberSpiritualShimmer(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "spiritualShimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )
    return shimmerOffset
}

/**
 * Modifier for spiritual shimmer effect
 */
fun Modifier.spiritualShimmer(
    baseColors: List<Color> = listOf(
        SpiritualPurple.copy(alpha = 0.2f),
        AuroraPink.copy(alpha = 0.35f),
        CosmicBlue.copy(alpha = 0.25f),
        SpiritualPurple.copy(alpha = 0.2f)
    )
): Modifier = composed {
    val shimmerOffset = rememberSpiritualShimmer()

    this.background(
        brush = Brush.linearGradient(
            colors = baseColors,
            start = Offset(-1000f + shimmerOffset * 3000f, -1000f + shimmerOffset * 3000f),
            end = Offset(shimmerOffset * 3000f, shimmerOffset * 3000f)
        )
    )
}

/**
 * Alternative gold shimmer for special elements
 */
fun Modifier.goldShimmer(): Modifier = composed {
    val shimmerOffset = rememberSpiritualShimmer()

    this.background(
        brush = Brush.linearGradient(
            colors = listOf(
                SacredGold.copy(alpha = 0.15f),
                AuraGold.copy(alpha = 0.3f),
                UnionGold.copy(alpha = 0.25f),
                SacredGold.copy(alpha = 0.15f)
            ),
            start = Offset(-1000f + shimmerOffset * 3000f, -1000f + shimmerOffset * 3000f),
            end = Offset(shimmerOffset * 3000f, shimmerOffset * 3000f)
        )
    )
}

/**
 * Chakra rainbow shimmer
 */
fun Modifier.chakraShimmer(): Modifier = composed {
    val shimmerOffset = rememberSpiritualShimmer()

    this.background(
        brush = Brush.linearGradient(
            colors = ChakraGradient.map { it.copy(alpha = 0.25f) },
            start = Offset(-1000f + shimmerOffset * 3000f, -1000f + shimmerOffset * 3000f),
            end = Offset(shimmerOffset * 3000f, shimmerOffset * 3000f)
        )
    )
}

// ============================================================================
// SKELETON BUILDING BLOCKS
// ============================================================================

/**
 * Basic skeleton box with rounded corners
 */
@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    shimmerType: ShimmerType = ShimmerType.SPIRITUAL
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .then(
                when (shimmerType) {
                    ShimmerType.SPIRITUAL -> Modifier.spiritualShimmer()
                    ShimmerType.GOLD -> Modifier.goldShimmer()
                    ShimmerType.CHAKRA -> Modifier.chakraShimmer()
                }
            )
    )
}

/**
 * Skeleton circle (for avatars, chakras)
 */
@Composable
fun SkeletonCircle(
    size: Dp,
    modifier: Modifier = Modifier,
    shimmerType: ShimmerType = ShimmerType.SPIRITUAL
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .then(
                when (shimmerType) {
                    ShimmerType.SPIRITUAL -> Modifier.spiritualShimmer()
                    ShimmerType.GOLD -> Modifier.goldShimmer()
                    ShimmerType.CHAKRA -> Modifier.chakraShimmer()
                }
            )
    )
}

/**
 * Skeleton text line
 */
@Composable
fun SkeletonText(
    modifier: Modifier = Modifier,
    width: Dp = 120.dp,
    height: Dp = 16.dp,
    shimmerType: ShimmerType = ShimmerType.SPIRITUAL
) {
    SkeletonBox(
        modifier = modifier
            .width(width)
            .height(height),
        shimmerType = shimmerType
    )
}

enum class ShimmerType {
    SPIRITUAL,
    GOLD,
    CHAKRA
}

// ============================================================================
// PROFILE CARD SKELETON
// ============================================================================

/**
 * Skeleton loader for profile cards
 *
 * Shows placeholder for:
 * - Avatar circle
 * - Name text
 * - Birth info
 * - Key spiritual attributes
 */
@Composable
fun ProfileCardSkeleton(
    modifier: Modifier = Modifier,
    showDetails: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            SkeletonCircle(
                size = 80.dp,
                shimmerType = ShimmerType.CHAKRA
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name
            SkeletonText(
                width = 140.dp,
                height = 20.dp,
                shimmerType = ShimmerType.GOLD
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Birth info
            SkeletonText(
                width = 180.dp,
                height = 14.dp
            )

            if (showDetails) {
                Spacer(modifier = Modifier.height(16.dp))

                // Attributes row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(3) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SkeletonCircle(
                                size = 32.dp,
                                shimmerType = ShimmerType.SPIRITUAL
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            SkeletonText(
                                width = 50.dp,
                                height = 12.dp
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================================================
// COMPATIBILITY REPORT SKELETON
// ============================================================================

/**
 * Skeleton loader for compatibility report
 *
 * Shows placeholder for:
 * - Two profile headers
 * - Overall score
 * - Dimension scores
 * - Insights sections
 */
@Composable
fun CompatibilityReportSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with two profiles
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile 1
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SkeletonCircle(
                    size = 60.dp,
                    shimmerType = ShimmerType.CHAKRA
                )
                Spacer(modifier = Modifier.height(8.dp))
                SkeletonText(width = 80.dp, height = 14.dp)
            }

            // Heart/Connection icon
            SkeletonCircle(
                size = 40.dp,
                shimmerType = ShimmerType.GOLD
            )

            // Profile 2
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SkeletonCircle(
                    size = 60.dp,
                    shimmerType = ShimmerType.CHAKRA
                )
                Spacer(modifier = Modifier.height(8.dp))
                SkeletonText(width = 80.dp, height = 14.dp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Overall score circle
        SkeletonCircle(
            size = 120.dp,
            shimmerType = ShimmerType.GOLD
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Dimension scores
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                repeat(5) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SkeletonText(
                            width = 100.dp,
                            height = 14.dp
                        )
                        SkeletonBox(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                                .height(8.dp),
                            shimmerType = ShimmerType.SPIRITUAL
                        )
                        SkeletonText(
                            width = 40.dp,
                            height = 14.dp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Insights section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                SkeletonText(
                    width = 120.dp,
                    height = 18.dp,
                    shimmerType = ShimmerType.GOLD
                )

                Spacer(modifier = Modifier.height(12.dp))

                repeat(3) {
                    SkeletonText(
                        modifier = Modifier.padding(vertical = 4.dp),
                        width = 280.dp,
                        height = 14.dp
                    )
                }
            }
        }
    }
}

// ============================================================================
// LIST ITEM SKELETON
// ============================================================================

/**
 * Skeleton loader for list items
 *
 * Used for profile lists, history lists, etc.
 */
@Composable
fun ListItemSkeleton(
    modifier: Modifier = Modifier,
    showAvatar: Boolean = true,
    showTrailing: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showAvatar) {
            SkeletonCircle(
                size = 48.dp,
                shimmerType = ShimmerType.CHAKRA
            )
            Spacer(modifier = Modifier.width(12.dp))
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            SkeletonText(
                width = 160.dp,
                height = 16.dp
            )
            Spacer(modifier = Modifier.height(6.dp))
            SkeletonText(
                width = 220.dp,
                height = 12.dp
            )
        }

        if (showTrailing) {
            Spacer(modifier = Modifier.width(12.dp))
            SkeletonText(
                width = 40.dp,
                height = 16.dp,
                shimmerType = ShimmerType.GOLD
            )
        }
    }
}

// ============================================================================
// CHART/GRAPH SKELETON
// ============================================================================

/**
 * Skeleton loader for charts and graphs
 *
 * Used for radar charts, bar charts, etc.
 */
@Composable
fun ChartSkeleton(
    modifier: Modifier = Modifier,
    height: Dp = 200.dp
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Center circle for radar chart
            SkeletonCircle(
                size = 120.dp,
                shimmerType = ShimmerType.CHAKRA
            )

            // Outer ring
            SkeletonCircle(
                size = 160.dp,
                modifier = Modifier.padding(20.dp),
                shimmerType = ShimmerType.SPIRITUAL
            )
        }
    }
}

// ============================================================================
// IMAGE PLACEHOLDER SKELETON
// ============================================================================

/**
 * Skeleton loader for images
 *
 * Used for zodiac images, backgrounds, etc.
 */
@Composable
fun ImagePlaceholderSkeleton(
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f,
    shimmerType: ShimmerType = ShimmerType.SPIRITUAL
) {
    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(RoundedCornerShape(12.dp))
            .then(
                when (shimmerType) {
                    ShimmerType.SPIRITUAL -> Modifier.spiritualShimmer()
                    ShimmerType.GOLD -> Modifier.goldShimmer()
                    ShimmerType.CHAKRA -> Modifier.chakraShimmer()
                }
            )
    )
}

// ============================================================================
// COMPOSITE SKELETONS
// ============================================================================

/**
 * Full screen profile loading skeleton
 */
@Composable
fun ProfileScreenSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        ProfileCardSkeleton(showDetails = true)

        Spacer(modifier = Modifier.height(16.dp))

        // Sections
        repeat(3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SkeletonText(
                        width = 140.dp,
                        height = 18.dp,
                        shimmerType = ShimmerType.GOLD
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    repeat(2) {
                        SkeletonText(
                            modifier = Modifier.padding(vertical = 4.dp),
                            width = 260.dp,
                            height = 14.dp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Profile list loading skeleton
 */
@Composable
fun ProfileListSkeleton(
    modifier: Modifier = Modifier,
    itemCount: Int = 5
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(itemCount) {
            ListItemSkeleton(
                showAvatar = true,
                showTrailing = true
            )
        }
    }
}

/**
 * Compatibility list loading skeleton
 */
@Composable
fun CompatibilityListSkeleton(
    modifier: Modifier = Modifier,
    itemCount: Int = 3
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(itemCount) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile 1
                    SkeletonCircle(
                        size = 40.dp,
                        shimmerType = ShimmerType.CHAKRA
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        SkeletonText(width = 120.dp, height = 14.dp)
                        Spacer(modifier = Modifier.height(6.dp))
                        SkeletonText(width = 180.dp, height = 12.dp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Score
                    SkeletonCircle(
                        size = 48.dp,
                        shimmerType = ShimmerType.GOLD
                    )
                }
            }
        }
    }
}

/**
 * Insights section skeleton
 */
@Composable
fun InsightsSectionSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            SkeletonText(
                width = 160.dp,
                height = 20.dp,
                shimmerType = ShimmerType.GOLD
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content lines
            repeat(4) { index ->
                SkeletonText(
                    modifier = Modifier.padding(vertical = 4.dp),
                    width = when (index) {
                        0 -> 300.dp
                        1 -> 280.dp
                        2 -> 260.dp
                        else -> 240.dp
                    },
                    height = 14.dp
                )
            }
        }
    }
}
