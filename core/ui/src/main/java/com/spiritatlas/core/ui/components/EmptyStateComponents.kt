package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*

/**
 * Empty state components for SpiritAtlas
 * Beautiful, encouraging empty states that maintain the spiritual theme
 */

// ═══════════════════════════════════════════════════════════════════════════════
// EMPTY STATE PRESETS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Predefined empty state configurations for common scenarios
 */
sealed class EmptyStateType(
    val icon: ImageVector,
    val title: String,
    val message: String,
    val color: Color,
    val actionText: String?
) {
    data object NoProfiles : EmptyStateType(
        icon = Icons.Default.Person,
        title = "Your Spiritual Journey Awaits",
        message = "Create your first profile to unlock personalized insights into your spiritual blueprint.",
        color = SpiritualPurple,
        actionText = "Create Profile"
    )

    data object NoCompatibility : EmptyStateType(
        icon = Icons.Default.Favorite,
        title = "Discover Connections",
        message = "Compare profiles to explore spiritual compatibility and deepen your understanding of relationships.",
        color = TantricRose,
        actionText = "Compare Profiles"
    )

    data object NoHistory : EmptyStateType(
        icon = Icons.Default.DateRange,
        title = "Your Path Begins Here",
        message = "Your compatibility history will appear here as you explore connections with others.",
        color = AuraGold,
        actionText = null
    )

    data object NoSearch : EmptyStateType(
        icon = Icons.Default.Search,
        title = "No Results Found",
        message = "The cosmos couldn't find what you're looking for. Try adjusting your search.",
        color = CosmicBlue,
        actionText = "Clear Filters"
    )

    data object NoFavorites : EmptyStateType(
        icon = Icons.Default.Star,
        title = "No Favorites Yet",
        message = "Mark profiles as favorites to quickly access the connections that matter most to you.",
        color = AuraGold,
        actionText = null
    )

    data object Offline : EmptyStateType(
        icon = Icons.Default.WifiOff,
        title = "Offline Mode",
        message = "You're currently offline. Some features may be limited until your connection is restored.",
        color = SensualCoral,
        actionText = "View Cached Data"
    )

    data class Custom(
        val customIcon: ImageVector,
        val customTitle: String,
        val customMessage: String,
        val customColor: Color,
        val customActionText: String? = null
    ) : EmptyStateType(
        icon = customIcon,
        title = customTitle,
        message = customMessage,
        color = customColor,
        actionText = customActionText
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// MAIN EMPTY STATE COMPONENT
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Full-screen empty state with animated icon
 */
@Composable
fun EmptyState(
    emptyStateType: EmptyStateType,
    modifier: Modifier = Modifier,
    onAction: (() -> Unit)? = null,
    secondaryActionText: String? = null,
    onSecondaryAction: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        emptyStateType.color.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .semantics {
                contentDescription = "Empty state: ${emptyStateType.title}"
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated icon
            AnimatedEmptyStateIcon(
                icon = emptyStateType.icon,
                color = emptyStateType.color
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = emptyStateType.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = emptyStateType.color,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Message
            Text(
                text = emptyStateType.message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            // Action buttons
            if (emptyStateType.actionText != null && onAction != null) {
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onAction,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = emptyStateType.color,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        text = emptyStateType.actionText,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Secondary action
            if (secondaryActionText != null && onSecondaryAction != null) {
                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onSecondaryAction,
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = secondaryActionText,
                        color = emptyStateType.color,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Compact empty state for use within cards or sections
 */
@Composable
fun CompactEmptyState(
    emptyStateType: EmptyStateType,
    modifier: Modifier = Modifier,
    onAction: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = emptyStateType.color.copy(alpha = 0.08f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Icon(
                imageVector = emptyStateType.icon,
                contentDescription = null,
                tint = emptyStateType.color,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = emptyStateType.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = emptyStateType.color,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Message
            Text(
                text = emptyStateType.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            // Action button
            if (emptyStateType.actionText != null && onAction != null) {
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onAction,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = emptyStateType.color
                    )
                ) {
                    Text(
                        text = emptyStateType.actionText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SPECIALIZED EMPTY STATES
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Empty profile list state
 */
@Composable
fun EmptyProfileListState(
    modifier: Modifier = Modifier,
    onCreateProfile: () -> Unit
) {
    EmptyState(
        emptyStateType = EmptyStateType.NoProfiles,
        modifier = modifier,
        onAction = onCreateProfile,
        secondaryActionText = "Learn More",
        onSecondaryAction = { /* Navigate to help/tutorial */ }
    )
}

/**
 * Empty compatibility history state
 */
@Composable
fun EmptyCompatibilityState(
    modifier: Modifier = Modifier,
    onCompareProfiles: () -> Unit
) {
    EmptyState(
        emptyStateType = EmptyStateType.NoCompatibility,
        modifier = modifier,
        onAction = onCompareProfiles
    )
}

/**
 * No search results state
 */
@Composable
fun NoSearchResultsState(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onClearSearch: () -> Unit
) {
    EmptyState(
        emptyStateType = EmptyStateType.Custom(
            customIcon = Icons.Default.Search,
            customTitle = "No Results for \"$searchQuery\"",
            customMessage = "The cosmos couldn't find any profiles matching your search. Try a different term or create a new profile.",
            customColor = CosmicBlue,
            customActionText = "Clear Search"
        ),
        modifier = modifier,
        onAction = onClearSearch,
        secondaryActionText = "Create Profile",
        onSecondaryAction = { /* Navigate to create profile */ }
    )
}

/**
 * Offline state with sync indicator
 */
@Composable
fun OfflineState(
    queuedOperations: Int = 0,
    modifier: Modifier = Modifier,
    onViewCached: (() -> Unit)? = null
) {
    val message = if (queuedOperations > 0) {
        "You're currently offline. $queuedOperations ${if (queuedOperations == 1) "operation" else "operations"} queued for sync."
    } else {
        EmptyStateType.Offline.message
    }

    EmptyState(
        emptyStateType = EmptyStateType.Custom(
            customIcon = Icons.Default.WifiOff,
            customTitle = EmptyStateType.Offline.title,
            customMessage = message,
            customColor = SensualCoral,
            customActionText = "View Cached Data"
        ),
        modifier = modifier,
        onAction = onViewCached
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// LOADING STATES
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Loading state with spiritual animation
 */
@Composable
fun SpiritualLoadingState(
    message: String = "Channeling cosmic energies...",
    modifier: Modifier = Modifier,
    color: Color = SpiritualPurple
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "Loading: $message"
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated loading indicator
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = color,
                strokeWidth = 4.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = color,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Skeleton loading state for lists
 */
@Composable
fun SkeletonLoadingCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Title skeleton
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            // Content skeleton
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }
    }
}

/**
 * Shimmer effect modifier for skeleton loading
 */
fun Modifier.shimmerEffect(): Modifier = this.background(
    brush = Brush.horizontalGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.3f)
        )
    )
)

// ═══════════════════════════════════════════════════════════════════════════════
// HELPER COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Animated empty state icon with gentle breathing animation
 */
@Composable
private fun AnimatedEmptyStateIcon(
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "empty_icon")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .size(140.dp)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = alpha * 0.2f))
        )

        // Inner circle
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color.copy(alpha = alpha),
                modifier = Modifier.size(56.dp)
            )
        }
    }
}
