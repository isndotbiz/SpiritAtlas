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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*

/**
 * Network status and offline mode UI components
 *
 * Features:
 * - Offline banner at top of screen
 * - Sync status indicators
 * - Queued operations display
 * - Connection status chip
 * - Network error handling
 */

// ═══════════════════════════════════════════════════════════════════════════════
// OFFLINE BANNER
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Offline banner displayed at the top of the screen
 */
@Composable
fun OfflineBanner(
    isOnline: Boolean,
    queuedOperations: Int = 0,
    modifier: Modifier = Modifier,
    onSync: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = !isOnline,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Offline banner: You are currently offline"
                },
            color = SensualCoral.copy(alpha = 0.9f),
            shadowElevation = 8.dp
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
                    // Offline icon
                    Icon(
                        imageVector = Icons.Default.WifiOff,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )

                    Column {
                        Text(
                            text = "You're offline",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (queuedOperations > 0) {
                            Text(
                                text = "$queuedOperations ${if (queuedOperations == 1) "operation" else "operations"} queued",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Sync button (if operations queued)
                    if (queuedOperations > 0 && onSync != null) {
                        TextButton(
                            onClick = onSync,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sync", fontSize = 14.sp)
                        }
                    }

                    // Dismiss button
                    onDismiss?.let {
                        IconButton(
                            onClick = it,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dismiss",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Online restoration banner with celebration animation
 */
@Composable
fun OnlineRestoredBanner(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Connection restored"
                },
            color = CosmicBlue.copy(alpha = 0.9f),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Connection restored",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SYNC STATUS INDICATOR
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Sync status indicator showing current sync state
 */
@Composable
fun SyncStatusIndicator(
    syncState: SyncStateType,
    modifier: Modifier = Modifier
) {
    val (icon, color, text) = when (syncState) {
        is SyncStateType.Idle -> Triple(Icons.Default.Check, CosmicBlue, "Synced")
        is SyncStateType.Syncing -> Triple(Icons.Default.Sync, AuraGold, "Syncing...")
        is SyncStateType.Queued -> Triple(Icons.Default.Schedule, IntimacyPurple, "${syncState.count} queued")
        is SyncStateType.Error -> Triple(Icons.Default.Error, SensualCoral, "Sync failed")
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (syncState is SyncStateType.Syncing) {
                // Animated syncing icon
                val infiniteTransition = rememberInfiniteTransition(label = "sync")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "rotation"
                )

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .size(16.dp)
                        .graphicsLayer { rotationZ = rotation }
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * Sync state types
 */
sealed class SyncStateType {
    data object Idle : SyncStateType()
    data class Syncing(val progress: Int = 0, val total: Int = 0) : SyncStateType()
    data class Queued(val count: Int) : SyncStateType()
    data class Error(val message: String) : SyncStateType()
}

// ═══════════════════════════════════════════════════════════════════════════════
// CONNECTION STATUS CHIP
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Small chip showing connection status
 */
@Composable
fun ConnectionStatusChip(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true
) {
    val color = if (isOnline) CosmicBlue else SensualCoral
    val text = if (isOnline) "Online" else "Offline"
    val icon = if (isOnline) Icons.Default.Wifi else Icons.Default.WifiOff

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status indicator dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )

            if (showLabel) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(14.dp)
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// QUEUED OPERATIONS CARD
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Card showing queued operations with sync button
 */
@Composable
fun QueuedOperationsCard(
    queuedCount: Int,
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    onSync: () -> Unit,
    onViewDetails: (() -> Unit)? = null
) {
    if (queuedCount == 0) return

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = IntimacyPurple.copy(alpha = 0.08f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Queued Operations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = IntimacyPurple
                )

                Text(
                    text = "$queuedCount ${if (queuedCount == 1) "operation" else "operations"} waiting to sync",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (!isOnline) {
                    Text(
                        text = "Will sync automatically when online",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                if (isOnline) {
                    FilledTonalButton(
                        onClick = onSync,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = IntimacyPurple,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sync Now")
                    }
                }

                onViewDetails?.let {
                    TextButton(
                        onClick = it,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = IntimacyPurple
                        )
                    ) {
                        Text("View Details", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SYNC PROGRESS INDICATOR
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Full-width sync progress indicator
 */
@Composable
fun SyncProgressIndicator(
    processed: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Syncing...",
                style = MaterialTheme.typography.labelMedium,
                color = AuraGold,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "$processed / $total",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        LinearProgressIndicator(
            progress = { if (total > 0) processed.toFloat() / total else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = AuraGold,
            trackColor = AuraGold.copy(alpha = 0.2f)
        )
    }
}

/**
 * Compact sync progress with circular indicator
 */
@Composable
fun CompactSyncProgress(
    processed: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            progress = { if (total > 0) processed.toFloat() / total else 0f },
            modifier = Modifier.size(20.dp),
            color = AuraGold,
            strokeWidth = 2.dp
        )

        Text(
            text = "Syncing $processed/$total",
            style = MaterialTheme.typography.labelSmall,
            color = AuraGold,
            fontWeight = FontWeight.Medium
        )
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NETWORK ERROR SNACKBAR
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Snackbar for network errors with retry action
 */
@Composable
fun NetworkErrorSnackbar(
    message: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        containerColor = SensualCoral,
        contentColor = Color.White,
        action = {
            TextButton(
                onClick = onRetry,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Retry")
            }
        },
        dismissAction = {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss",
                    tint = Color.White
                )
            }
        }
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
