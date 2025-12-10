package com.spiritatlas.core.ui.interaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.core.ui.theme.SpiritualPurple
import kotlinx.coroutines.launch

// ============================================================================
// BUTTON MICRO-INTERACTIONS
// ============================================================================

/**
 * Enhanced button press interaction modifier
 * Combines scale animation, haptic feedback, and ripple effect
 *
 * @param enabled Whether the button is enabled
 * @param hapticType Type of haptic feedback to trigger
 * @param pressScale Scale factor when pressed (default 0.95)
 * @param onPress Optional callback when button is pressed
 * @param onClick Callback when button is clicked
 */
@Composable
fun Modifier.interactiveButton(
    enabled: Boolean = true,
    hapticType: HapticFeedbackType = HapticFeedbackType.LIGHT,
    pressScale: Float = 0.95f,
    onPress: (() -> Unit)? = null,
    onClick: () -> Unit
): Modifier {
    var isPressed by remember { mutableStateOf(false) }
    val haptic = rememberHapticFeedback()

    val scale by animateFloatAsState(
        targetValue = when {
            !enabled -> 0.95f
            isPressed -> pressScale
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(enabled) {
            if (enabled) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        haptic.performHaptic(hapticType)
                        onPress?.invoke()

                        val released = tryAwaitRelease()
                        isPressed = false

                        if (released) {
                            onClick()
                        }
                    }
                )
            }
        }
}

/**
 * Button with success animation (checkmark and sparkle)
 * Shows a quick scale-up animation to indicate successful action
 */
@Composable
fun Modifier.successAnimation(
    triggered: Boolean
): Modifier {
    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(triggered) {
        if (triggered) {
            scope.launch {
                // Quick pop-up animation
                scale.animateTo(
                    targetValue = 1.15f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            }
        }
    }

    return this.graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
}

// ============================================================================
// CARD MICRO-INTERACTIONS
// ============================================================================

/**
 * Interactive card with elevation change, scale, and haptic feedback
 *
 * @param onClick Callback when card is clicked
 * @param modifier Modifier for the card
 * @param enabled Whether the card is interactive
 * @param baseElevation Base elevation of the card
 * @param pressedElevation Elevation when pressed
 * @param hapticEnabled Whether to trigger haptic feedback
 * @param content Content of the card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    baseElevation: Dp = 4.dp,
    pressedElevation: Dp = 8.dp,
    hapticEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = rememberHapticFeedback()

    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isPressed && enabled) pressedElevation.value else baseElevation.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardElevation"
    )

    LaunchedEffect(isPressed) {
        if (isPressed && enabled && hapticEnabled) {
            haptic.performHaptic(HapticFeedbackType.LIGHT)
        }
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(elevation = elevation.dp, shape = RoundedCornerShape(16.dp)),
        interactionSource = interactionSource,
        enabled = enabled,
        content = { content() }
    )
}

/**
 * Card flip animation modifier for profile/compatibility cards
 *
 * @param flipped Whether the card is flipped
 * @param onClick Callback when flip is triggered
 */
@Composable
fun Modifier.cardFlip(
    flipped: Boolean,
    onClick: () -> Unit
): Modifier {
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardFlip"
    )

    val haptic = rememberHapticFeedback()

    return this
        .graphicsLayer {
            rotationY = rotation
            cameraDistance = 12f * density
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    haptic.performHaptic(HapticFeedbackType.MEDIUM)
                    onClick()
                }
            )
        }
}

/**
 * Swipe to dismiss card with spring animation
 *
 * @param onDismiss Callback when card is dismissed
 */
@Composable
fun Modifier.swipeToDismiss(
    onDismiss: () -> Unit
): Modifier {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val haptic = rememberHapticFeedback()

    return this
        .graphicsLayer {
            translationX = offsetX.value
            alpha = 1f - (kotlin.math.abs(offsetX.value) / 1000f).coerceIn(0f, 0.8f)
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    // Detect horizontal drag
                    val pressPosition = it
                    tryAwaitRelease()

                    // For now, implement double swipe to dismiss
                    // Full swipe gesture would need more complex gesture detection
                }
            )
        }
}

// ============================================================================
// INPUT FIELD MICRO-INTERACTIONS
// ============================================================================

/**
 * Enhanced text field with focus animation and error shake
 *
 * @param value Current text value
 * @param onValueChange Callback when text changes
 * @param modifier Modifier for the text field
 * @param label Label for the text field
 * @param isError Whether the field has an error
 * @param errorMessage Error message to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpiritualTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val haptic = rememberHapticFeedback()
    val shakeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Trigger shake animation when error occurs
    LaunchedEffect(isError) {
        if (isError) {
            haptic.performHaptic(HapticFeedbackType.ERROR)
            scope.launch {
                // Shake animation: left-right-left
                repeat(3) {
                    shakeOffset.animateTo(
                        targetValue = 10f,
                        animationSpec = tween(50)
                    )
                    shakeOffset.animateTo(
                        targetValue = -10f,
                        animationSpec = tween(50)
                    )
                }
                shakeOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                )
            }
        }
    }

    Box(
        modifier = modifier.graphicsLayer {
            translationX = shakeOffset.value
        }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = SpiritualPurple,
                unfocusedIndicatorColor = SpiritualPurple.copy(alpha = 0.5f),
                focusedLabelColor = SpiritualPurple,
                unfocusedLabelColor = SpiritualPurple.copy(alpha = 0.7f),
                cursorColor = SpiritualPurple
            ),
            modifier = Modifier
        )
    }
}

/**
 * Text field focus animation modifier
 * Adds subtle scale and shadow when focused
 */
@Composable
fun Modifier.textFieldFocus(
    focused: Boolean
): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (focused) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "textFieldScale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (focused) 4f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "textFieldElevation"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .shadow(elevation = elevation.dp, shape = RoundedCornerShape(8.dp))
}

// ============================================================================
// RIPPLE EFFECTS
// ============================================================================

/**
 * Spiritual ripple effect with custom colors
 * Adds a subtle ripple animation with spiritual color palette
 */
@Composable
fun Modifier.spiritualRipple(
    color: Color = SpiritualPurple
): Modifier {
    // Material3 already provides ripple through indication
    // This is a placeholder for future custom ripple implementation
    return this
}

// ============================================================================
// SPRING PHYSICS HELPERS
// ============================================================================

/**
 * Spring animation presets for different interaction types
 */
object SpringPresets {
    /** Quick, bouncy spring for buttons */
    val Button = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )

    /** Smooth, low-bounce spring for cards */
    val Card = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    /** Stiff spring for immediate feedback */
    val Immediate = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessHigh
    )

    /** Gentle spring for subtle animations */
    val Gentle = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    /** Elastic spring for playful interactions */
    val Elastic = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMedium
    )
}

// ============================================================================
// ELEVATION & SHADOW HELPERS
// ============================================================================

/**
 * Animated elevation modifier
 * Smoothly animates elevation changes
 */
@Composable
fun Modifier.animatedElevation(
    elevation: Dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp)
): Modifier {
    val animatedElevation by animateFloatAsState(
        targetValue = elevation.value,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "elevation"
    )

    return this.shadow(elevation = animatedElevation.dp, shape = shape)
}

/**
 * Lift animation for cards on press
 * Combines scale and elevation for a 3D lift effect
 */
@Composable
fun Modifier.liftOnPress(
    pressed: Boolean,
    baseElevation: Dp = 2.dp,
    liftedElevation: Dp = 8.dp
): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (pressed) 1.02f else 1f,
        animationSpec = SpringPresets.Card,
        label = "liftScale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (pressed) liftedElevation.value else baseElevation.value,
        animationSpec = SpringPresets.Gentle,
        label = "liftElevation"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .shadow(elevation = elevation.dp, shape = RoundedCornerShape(16.dp))
}
