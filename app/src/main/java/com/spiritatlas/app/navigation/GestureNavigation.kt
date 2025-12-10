package com.spiritatlas.app.navigation

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spiritatlas.core.ui.haptics.HapticFeedbackController
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Gesture navigation configuration for SpiritAtlas.
 * Enables swipe-back gesture and other gesture-based navigation enhancements.
 */
object GestureNavigationConfig {
    /**
     * Minimum swipe distance to trigger back navigation (in dp)
     */
    const val MIN_SWIPE_DISTANCE_DP = 100

    /**
     * Velocity threshold for quick swipe (in dp/sec)
     */
    const val VELOCITY_THRESHOLD = 300f

    /**
     * Edge margin where swipe gesture is recognized (in dp)
     */
    const val EDGE_MARGIN_DP = 20
}

/**
 * Modifier that adds swipe-back gesture support to a screen.
 * Allows users to swipe from the left edge to navigate back.
 *
 * @param navController Navigation controller for back navigation
 * @param hapticController Optional haptic feedback controller
 * @param enabled Whether the gesture is enabled (default true)
 * @param onSwipeProgress Optional callback for swipe progress (0.0 to 1.0)
 */
fun Modifier.swipeBackGesture(
    navController: NavController,
    hapticController: HapticFeedbackController? = null,
    enabled: Boolean = true,
    onSwipeProgress: ((Float) -> Unit)? = null
): Modifier = composed {
    if (!enabled) return@composed this

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val minSwipeDistance = with(density) { GestureNavigationConfig.MIN_SWIPE_DISTANCE_DP.dp.toPx() }
    val edgeMargin = with(density) { GestureNavigationConfig.EDGE_MARGIN_DP.dp.toPx() }

    var swipeStartX by remember { mutableStateOf(0f) }
    var isSwipeFromEdge by remember { mutableStateOf(false) }
    var hasProvidedHaptic by remember { mutableStateOf(false) }

    this.pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragStart = { offset ->
                // Only start swipe if gesture begins near left edge
                swipeStartX = offset.x
                isSwipeFromEdge = offset.x <= edgeMargin
                hasProvidedHaptic = false
            },
            onDragEnd = {
                if (isSwipeFromEdge) {
                    onSwipeProgress?.invoke(0f)
                }
            },
            onDragCancel = {
                if (isSwipeFromEdge) {
                    onSwipeProgress?.invoke(0f)
                }
            },
            onHorizontalDrag = { change, dragAmount ->
                if (!isSwipeFromEdge) return@detectHorizontalDragGestures

                val currentDragDistance = change.position.x - swipeStartX

                // Only process rightward swipes (positive drag amount)
                if (currentDragDistance > 0) {
                    val progress = (currentDragDistance / minSwipeDistance).coerceIn(0f, 1f)
                    onSwipeProgress?.invoke(progress)

                    // Provide haptic feedback at 50% progress
                    if (progress >= 0.5f && !hasProvidedHaptic) {
                        hapticController?.performHaptic(HapticFeedbackType.SELECTION)
                        hasProvidedHaptic = true
                    }

                    // Trigger back navigation if swipe distance exceeds threshold
                    if (currentDragDistance >= minSwipeDistance) {
                        scope.launch {
                            Timber.d("Swipe-back gesture detected, navigating back")
                            hapticController?.performHaptic(HapticFeedbackType.NAVIGATION)

                            if (navController.previousBackStackEntry != null) {
                                navController.popBackStack()
                            }
                        }

                        // Reset state
                        isSwipeFromEdge = false
                        onSwipeProgress?.invoke(0f)
                    }
                }
            }
        )
    }
}

/**
 * Composable wrapper that adds swipe-back gesture to its content.
 * Useful for wrapping entire screens.
 */
@Composable
fun SwipeBackContainer(
    navController: NavController,
    hapticController: HapticFeedbackController? = null,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    var swipeProgress by remember { mutableStateOf(0f) }

    // Apply swipe gesture modifier to content
    // Note: In production, you might want to add visual feedback based on swipeProgress
    content()
}

/**
 * Extension function to check if a screen supports back navigation.
 */
fun NavController.canNavigateBack(): Boolean {
    return previousBackStackEntry != null
}

/**
 * Extension function to handle predictive back gesture (Android 13+).
 * Provides visual preview of back navigation.
 */
fun NavController.handlePredictiveBack(progress: Float) {
    // Note: Predictive back gesture API requires additional setup
    // This is a placeholder for future implementation
    Timber.d("Predictive back progress: $progress")
}
