package com.spiritatlas.app.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.spiritatlas.core.ui.haptics.HapticFeedbackController
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.theme.SpiritAtlasTheme

/**
 * Enhanced bottom navigation item with animations, badges, and haptic feedback.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val badgeCount: Int = 0,
    val showBadgeDot: Boolean = false
)

/**
 * Enhanced bottom navigation bar with spiritual animations and haptic feedback.
 *
 * Features:
 * - Smooth icon animations on selection
 * - Badge support (count and dot)
 * - Haptic feedback on tab change
 * - Beautiful gradient background
 * - Scale animation on selection
 * - Cross-fade transitions
 */
@Composable
fun EnhancedBottomNavigation(
    items: List<BottomNavItem>,
    currentRoute: String?,
    navController: NavController,
    hapticController: HapticFeedbackController? = null,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(80.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        hapticController?.performHaptic(HapticFeedbackType.SELECTION)
                        navController.navigateToTopLevel(item.route, TransitionType.CROSS_FADE)
                    }
                },
                icon = {
                    EnhancedNavIcon(
                        item = item,
                        isSelected = isSelected
                    )
                },
                label = {
                    AnimatedNavLabel(
                        label = item.label,
                        isSelected = isSelected
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

/**
 * Enhanced navigation icon with animations and badge support.
 */
@Composable
private fun EnhancedNavIcon(
    item: BottomNavItem,
    isSelected: Boolean
) {
    Box(contentAlignment = Alignment.Center) {
        // Animated icon with scale effect
        val scale by animateFloatAsState(
            targetValue = if (isSelected) 1.15f else 1.0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            ),
            label = "icon_scale"
        )

        // Cross-fade between selected and unselected icons
        Crossfade(
            targetState = isSelected,
            animationSpec = tween(durationMillis = 300),
            label = "icon_crossfade"
        ) { selected ->
            Icon(
                imageVector = if (selected) item.selectedIcon else item.icon,
                contentDescription = item.label,
                modifier = Modifier.scale(scale)
            )
        }

        // Badge overlay
        if (item.badgeCount > 0 || item.showBadgeDot) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = (-4).dp)
            ) {
                if (item.badgeCount > 0) {
                    AnimatedBadgeCount(count = item.badgeCount)
                }
            }
        }
    }
}

/**
 * Animated badge count with smooth transitions.
 */
@Composable
private fun AnimatedBadgeCount(count: Int) {
    // Animate badge count changes
    AnimatedContent(
        targetState = count,
        transitionSpec = {
            if (targetState > initialState) {
                // Growing: scale up and fade in
                (scaleIn(initialScale = 0.5f) + fadeIn()).togetherWith(
                    scaleOut(targetScale = 1.2f) + fadeOut()
                )
            } else {
                // Shrinking: scale down and fade out
                (scaleIn(initialScale = 1.2f) + fadeIn()).togetherWith(
                    scaleOut(targetScale = 0.5f) + fadeOut()
                )
            }.using(SizeTransform(clip = false))
        },
        label = "badge_count"
    ) { targetCount ->
        Text(
            text = if (targetCount > 99) "99+" else targetCount.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Animated navigation label with smooth transitions.
 */
@Composable
private fun AnimatedNavLabel(
    label: String,
    isSelected: Boolean
) {
    val fontWeight by animateIntAsState(
        targetValue = if (isSelected) 700 else 400,
        animationSpec = tween(durationMillis = 300),
        label = "label_weight"
    )

    Text(
        text = label,
        fontWeight = FontWeight(fontWeight),
        fontSize = 12.sp,
        maxLines = 1
    )
}

/**
 * Alternative: Floating Action Bottom Navigation with spiritual gradient.
 * More visually striking option.
 */
@Composable
fun FloatingBottomNavigation(
    items: List<BottomNavItem>,
    currentRoute: String?,
    navController: NavController,
    hapticController: HapticFeedbackController? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(24.dp)),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                FloatingNavItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = {
                        if (currentRoute != item.route) {
                            hapticController?.performHaptic(HapticFeedbackType.SELECTION)
                            navController.navigateToTopLevel(item.route, TransitionType.CROSS_FADE)
                        }
                    }
                )
            }
        }
    }
}

/**
 * Floating navigation item with pill-shaped selection indicator.
 */
@Composable
private fun FloatingNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            Color.Transparent
        },
        animationSpec = tween(durationMillis = 300),
        label = "bg_color"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .selectable(
                selected = isSelected,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.animateContentSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "icon_scale"
                )

                Icon(
                    imageVector = if (isSelected) item.selectedIcon else item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.scale(scale),
                    tint = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )

                // Badge
                if (item.badgeCount > 0 || item.showBadgeDot) {
                    Badge(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = (-4).dp)
                    ) {
                        if (item.badgeCount > 0) {
                            Text(
                                text = if (item.badgeCount > 99) "99+" else item.badgeCount.toString(),
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }

            // Show label only when selected
            AnimatedVisibility(
                visible = isSelected,
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                Text(
                    text = item.label,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Example usage and default items for SpiritAtlas.
 */
object SpiritAtlasBottomNav {
    fun getDefaultItems(): List<BottomNavItem> = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            label = "Home",
            icon = Icons.Default.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomNavItem(
            route = Screen.ProfileList.route,
            label = "Profiles",
            icon = Icons.Default.Person,
            selectedIcon = Icons.Filled.Person
        ),
        BottomNavItem(
            route = Screen.Compatibility.route,
            label = "Compatibility",
            icon = Icons.Default.FavoriteBorder,
            selectedIcon = Icons.Filled.Favorite
        ),
        BottomNavItem(
            route = Screen.Consent.route,
            label = "Settings",
            icon = Icons.Default.Settings,
            selectedIcon = Icons.Filled.Settings
        )
    )
}
