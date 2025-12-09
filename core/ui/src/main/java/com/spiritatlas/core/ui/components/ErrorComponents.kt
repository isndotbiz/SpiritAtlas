package com.spiritatlas.core.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
 * Spiritual-themed error handling UI components for SpiritAtlas
 * Designed with calming colors, encouraging messages, and mystical aesthetics
 */

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR TYPE DEFINITIONS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Sealed class representing different types of errors with spiritual-themed messages
 */
sealed class ErrorType(
    val icon: ImageVector,
    val title: String,
    val defaultMessage: String,
    val color: Color
) {
    data object NetworkError : ErrorType(
        icon = Icons.Default.Warning,
        title = "Cosmic Connection Lost",
        defaultMessage = "The cosmic signals are disrupted. Let's reconnect to the universe.",
        color = SensualCoral
    )

    data object ServerError : ErrorType(
        icon = Icons.Default.Info,
        title = "The Stars Are Aligning",
        defaultMessage = "The celestial servers are realigning. Please wait a moment for harmony to return.",
        color = IntimacyPurple
    )

    data object NotFoundError : ErrorType(
        icon = Icons.Default.Search,
        title = "Path Not Found",
        defaultMessage = "This spiritual path doesn't exist in our realm. Let's guide you back.",
        color = AuraGold
    )

    data object PermissionError : ErrorType(
        icon = Icons.Default.Lock,
        title = "Sacred Access Required",
        defaultMessage = "This sacred knowledge requires special permissions. Please verify your access.",
        color = MysticViolet
    )

    data object UnknownError : ErrorType(
        icon = Icons.Default.Close,
        title = "Unexpected Energy",
        defaultMessage = "An unexpected energy disrupted the flow. The stars will align again soon.",
        color = TantricRose
    )

    /**
     * Custom error type for flexibility
     */
    data class Custom(
        val customIcon: ImageVector,
        val customTitle: String,
        val customMessage: String,
        val customColor: Color = SpiritualPurple
    ) : ErrorType(customIcon, customTitle, customMessage, customColor)
}

// ═══════════════════════════════════════════════════════════════════════════════
// FULL SCREEN ERROR COMPONENT
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Full screen error display with illustration, message, and retry button
 * Perfect for critical errors that prevent the entire screen from loading
 */
@Composable
fun ErrorScreen(
    errorType: ErrorType,
    modifier: Modifier = Modifier,
    message: String? = null,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    customAction: Pair<String, () -> Unit>? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        errorType.color.copy(alpha = 0.05f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .semantics {
                contentDescription = "Error screen: ${errorType.title}"
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated error icon
            AnimatedErrorIcon(
                icon = errorType.icon,
                color = errorType.color
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Error title
            Text(
                text = errorType.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = errorType.color,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Error message
            Text(
                text = message ?: errorType.defaultMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Retry button
                onRetry?.let {
                    RetryButton(
                        onClick = it,
                        text = "Try Again",
                        color = errorType.color,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Custom action button
                customAction?.let { (text, action) ->
                    OutlinedButton(
                        onClick = action,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = errorType.color
                        )
                    ) {
                        Text(
                            text = text,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Dismiss button
                onDismiss?.let {
                    TextButton(
                        onClick = it,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Dismiss",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// INLINE ERROR CARD
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Inline error card for displaying errors within sections
 * Less intrusive than full screen error
 */
@Composable
fun ErrorCard(
    errorType: ErrorType,
    modifier: Modifier = Modifier,
    message: String? = null,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    compact: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "Error card: ${errorType.title}"
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = errorType.color.copy(alpha = 0.08f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (compact) 12.dp else 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Error icon
            Icon(
                imageVector = errorType.icon,
                contentDescription = null,
                tint = errorType.color,
                modifier = Modifier.size(if (compact) 20.dp else 24.dp)
            )

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(if (compact) 4.dp else 8.dp)
            ) {
                Text(
                    text = errorType.title,
                    style = if (compact) {
                        MaterialTheme.typography.titleSmall
                    } else {
                        MaterialTheme.typography.titleMedium
                    },
                    fontWeight = FontWeight.SemiBold,
                    color = errorType.color
                )

                Text(
                    text = message ?: errorType.defaultMessage,
                    style = if (compact) {
                        MaterialTheme.typography.bodySmall
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Action buttons
                if (onRetry != null || onDismiss != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = if (compact) 4.dp else 8.dp)
                    ) {
                        onRetry?.let {
                            TextButton(
                                onClick = it,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = errorType.color
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Retry", fontSize = 14.sp)
                            }
                        }

                        onDismiss?.let {
                            TextButton(
                                onClick = it,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            ) {
                                Text("Dismiss", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR SNACKBAR
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Custom snackbar with spiritual error styling
 * For quick, dismissible error notifications
 */
@Composable
fun ErrorSnackbar(
    errorType: ErrorType,
    message: String,
    modifier: Modifier = Modifier,
    action: Pair<String, () -> Unit>? = null,
    onDismiss: (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier
            .padding(16.dp)
            .semantics {
                contentDescription = "Error notification: $message"
            },
        shape = RoundedCornerShape(16.dp),
        containerColor = errorType.color,
        contentColor = Color.White,
        action = action?.let {
            {
                TextButton(
                    onClick = it.second,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = it.first,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        dismissAction = onDismiss?.let {
            {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = errorType.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR BANNER
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Dismissible banner for non-critical errors
 * Shows at the top of the screen, can be swiped away
 */
@Composable
fun ErrorBanner(
    errorType: ErrorType,
    message: String,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    onDismiss: () -> Unit,
    action: Pair<String, () -> Unit>? = null
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Error banner: $message"
                },
            color = errorType.color.copy(alpha = 0.1f),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = errorType.icon,
                        contentDescription = null,
                        tint = errorType.color,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    action?.let { (text, onClick) ->
                        TextButton(
                            onClick = onClick,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = errorType.color
                            )
                        ) {
                            Text(
                                text = text,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NETWORK ERROR VIEW
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Specific view for network connectivity issues
 * Includes helpful troubleshooting suggestions
 */
@Composable
fun NetworkErrorView(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    customMessage: String? = null
) {
    ErrorScreen(
        errorType = ErrorType.NetworkError,
        modifier = modifier,
        message = customMessage,
        onRetry = onRetry,
        customAction = "Check Settings" to {
            // This would typically open network settings
            // Implementation depends on the app's navigation
        }
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// EMPTY STATE VIEW
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Beautiful empty state with illustration placeholder
 * For when there's no data to display (not an error, but related)
 */
@Composable
fun EmptyStateView(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Search,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    color: Color = SpiritualPurple
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "Empty state: $title"
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated empty state icon
            val infiniteTransition = rememberInfiniteTransition(label = "empty_state")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "scale"
            )

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color.copy(alpha = 0.6f),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            // Action button if provided
            if (actionText != null && onAction != null) {
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onAction,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = color,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = actionText,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// RETRY BUTTON
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Animated retry button with loading state
 * Features a gentle pulsing animation when idle
 */
@Composable
fun RetryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Try Again",
    isLoading: Boolean = false,
    color: Color = SpiritualPurple,
    enabled: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "retry_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .scale(if (!isLoading && enabled) scale else 1f)
            .semantics {
                contentDescription = if (isLoading) "Loading" else "Retry button"
            },
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White,
            disabledContainerColor = color.copy(alpha = 0.6f),
            disabledContentColor = Color.White.copy(alpha = 0.7f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "retry_content"
        ) { loading ->
            if (loading) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Text(
                        text = "Reconnecting...",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = text,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// HELPER COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Animated error icon with gentle rotation and scale animation
 */
@Composable
private fun AnimatedErrorIcon(
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "error_icon")

    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
            .size(100.dp)
            .scale(scale)
            .rotate(rotation)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(50.dp)
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// PREVIEW HELPERS (for Android Studio Preview)
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Extension function to show an error snackbar using SnackbarHostState
 */
suspend fun SnackbarHostState.showErrorSnackbar(
    errorType: ErrorType,
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short
): SnackbarResult {
    return showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = duration
    )
}
