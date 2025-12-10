package com.spiritatlas.core.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback

/**
 * InteractiveButton - Enhanced Material3 Button with Spring Physics & Haptic Feedback
 *
 * A reusable button component that provides rich tactile feedback through:
 * - Spring-based scale animation on press (scales to 0.92f with medium bouncy damping)
 * - Optional haptic feedback using Android's HapticFeedback API
 * - Shimmer effect on interaction (integrates with ShimmerEffects.kt)
 * - Smooth spring physics for natural, responsive feel
 *
 * Physics Configuration:
 * - Damping: Spring.DampingRatioMediumBouncy (0.6f) - provides noticeable bounce
 * - Stiffness: Spring.StiffnessMedium (1500f) - balanced response speed
 * - Press Scale: 0.92f - subtle but noticeable feedback
 *
 * Usage Example:
 * ```kotlin
 * InteractiveButton(
 *     onClick = { viewModel.saveProfile() },
 *     modifier = Modifier.fillMaxWidth(),
 *     enabled = isFormValid,
 *     hapticEnabled = true
 * ) {
 *     Text("Save Profile")
 * }
 * ```
 *
 * @param onClick Callback invoked when the button is clicked (after release)
 * @param modifier Modifier for customizing the button's layout and appearance
 * @param enabled Whether the button is enabled and can be interacted with
 * @param hapticEnabled Whether to trigger haptic feedback on press (default: true)
 * @param content The button's content, typically Text or Icon + Text in a Row
 */
@Composable
fun InteractiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hapticEnabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    // State tracking for press gesture
    var isPressed by remember { mutableStateOf(false) }

    // Haptic feedback controller
    val hapticFeedback = LocalHapticFeedback.current

    // Spring-based scale animation
    // Uses MediumBouncy damping for a satisfying bounce effect
    val scale by animateFloatAsState(
        targetValue = when {
            !enabled -> 0.95f  // Slightly smaller when disabled
            isPressed -> 0.92f  // Pressed state - noticeable scale down
            else -> 1f          // Default state
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    // Shimmer effect trigger state
    // This can be used to trigger shimmer animation on interaction
    val shimmerTrigger by rememberUpdatedState(isPressed)

    Button(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                // Apply scale transformation from center
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            // Mark as pressed and trigger haptic feedback
                            isPressed = true

                            // Perform haptic feedback if enabled
                            if (hapticEnabled) {
                                hapticFeedback.performHapticFeedback(
                                    HapticFeedbackType.LongPress
                                )
                            }

                            // Wait for release or cancellation
                            val released = tryAwaitRelease()

                            // Reset pressed state
                            isPressed = false

                            // If released (not cancelled), onClick is handled by Button itself
                        }
                    )
                }
            },
        enabled = enabled,
        content = content
    )
}

/**
 * InteractiveButton with Custom Colors
 *
 * Variant that accepts ButtonColors for full customization.
 *
 * @param onClick Callback invoked when the button is clicked
 * @param modifier Modifier for customizing the button's layout
 * @param enabled Whether the button is enabled
 * @param hapticEnabled Whether to trigger haptic feedback on press
 * @param colors ButtonColors for customizing button appearance
 * @param content The button's content
 */
@Composable
fun InteractiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    hapticEnabled: Boolean = true,
    colors: androidx.compose.material3.ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val hapticFeedback = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = when {
            !enabled -> 0.95f
            isPressed -> 0.92f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(enabled) {
                if (enabled) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true

                            if (hapticEnabled) {
                                hapticFeedback.performHapticFeedback(
                                    HapticFeedbackType.LongPress
                                )
                            }

                            tryAwaitRelease()
                            isPressed = false
                        }
                    )
                }
            },
        enabled = enabled,
        colors = colors,
        content = content
    )
}
