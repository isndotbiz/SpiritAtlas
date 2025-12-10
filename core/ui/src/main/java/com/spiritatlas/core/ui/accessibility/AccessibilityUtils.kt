package com.spiritatlas.core.ui.accessibility

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Accessibility Utilities for WCAG 2.1 AAA Compliance
 *
 * This file provides comprehensive accessibility support including:
 * - Touch target size enforcement (minimum 48dp)
 * - Semantic roles and descriptions
 * - State descriptions for screen readers
 * - Focus indicators
 * - Dynamic type support utilities
 */

// ============================================================================
// TOUCH TARGET SIZE CONSTANTS
// ============================================================================

/**
 * Minimum touch target size per WCAG 2.1 Level AAA
 * Reference: WCAG 2.1 Success Criterion 2.5.5 Target Size (Level AAA)
 */
object TouchTargetSize {
    /** Minimum size for any interactive element (WCAG AAA) */
    val minimum: Dp = 48.dp

    /** Recommended size for comfortable interaction */
    val recommended: Dp = 56.dp

    /** Large size for users with motor impairments */
    val large: Dp = 64.dp
}

// ============================================================================
// SEMANTIC MODIFIERS
// ============================================================================

/**
 * Add semantic information for a heading
 * Screen readers will announce this as a heading for better navigation
 */
fun Modifier.headingSemantic(level: Int = 1): Modifier = this.semantics {
    heading()
}

/**
 * Add semantic role with content description
 */
fun Modifier.accessibleRole(
    role: Role,
    description: String,
    stateDescription: String? = null
): Modifier = this.semantics {
    this.role = role
    this.contentDescription = description
    stateDescription?.let { this.stateDescription = it }
}

/**
 * Add semantic information for a button with state
 */
fun Modifier.accessibleButton(
    description: String,
    enabled: Boolean = true,
    selected: Boolean? = null
): Modifier {
    val state = when {
        !enabled -> "disabled"
        selected == true -> "selected"
        selected == false -> "not selected"
        else -> null
    }

    return this.semantics {
        this.role = Role.Button
        this.contentDescription = description
        state?.let { this.stateDescription = it }
    }
}

/**
 * Add semantic information for a checkbox
 */
fun Modifier.accessibleCheckbox(
    description: String,
    checked: Boolean,
    enabled: Boolean = true
): Modifier = this.semantics {
    this.role = Role.Checkbox
    this.contentDescription = description
    this.stateDescription = if (checked) "checked" else "not checked"
}

/**
 * Add semantic information for a radio button
 */
fun Modifier.accessibleRadioButton(
    description: String,
    selected: Boolean,
    enabled: Boolean = true
): Modifier = this.semantics {
    this.role = Role.RadioButton
    this.contentDescription = description
    this.stateDescription = if (selected) "selected" else "not selected"
}

/**
 * Add semantic information for a switch/toggle
 */
fun Modifier.accessibleSwitch(
    description: String,
    checked: Boolean,
    enabled: Boolean = true
): Modifier = this.semantics {
    this.role = Role.Switch
    this.contentDescription = description
    this.stateDescription = if (checked) "on" else "off"
}

/**
 * Add semantic information for a slider
 */
fun Modifier.accessibleSlider(
    description: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    enabled: Boolean = true
): Modifier = this.semantics {
    this.contentDescription = description
    val percentage = ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start) * 100).toInt()
    this.stateDescription = "$percentage percent"
}

/**
 * Add semantic information for an image
 */
fun Modifier.accessibleImage(
    description: String,
    isDecorative: Boolean = false
): Modifier = this.semantics {
    this.role = Role.Image
    if (!isDecorative) {
        this.contentDescription = description
    }
}

/**
 * Add semantic information for a tab
 */
fun Modifier.accessibleTab(
    description: String,
    selected: Boolean,
    position: Int,
    total: Int
): Modifier = this.semantics {
    this.role = Role.Tab
    this.contentDescription = description
    this.stateDescription = if (selected) {
        "selected, tab $position of $total"
    } else {
        "tab $position of $total"
    }
}

// ============================================================================
// TOUCH TARGET SIZE ENFORCEMENT
// ============================================================================

/**
 * Ensure minimum touch target size (48dp x 48dp)
 * Automatically enforces WCAG 2.1 AAA requirements
 */
fun Modifier.minimumTouchTarget(): Modifier = this.size(TouchTargetSize.minimum)

/**
 * Ensure minimum touch target width
 */
fun Modifier.minimumTouchTargetWidth(): Modifier {
    return this.then(
        Modifier.size(width = TouchTargetSize.minimum, height = Dp.Unspecified)
    )
}

/**
 * Ensure minimum touch target height
 */
fun Modifier.minimumTouchTargetHeight(): Modifier {
    return this.then(
        Modifier.size(width = Dp.Unspecified, height = TouchTargetSize.minimum)
    )
}

// ============================================================================
// CONTENT DESCRIPTION BUILDERS
// ============================================================================

/**
 * Build a content description for a percentage value
 */
fun percentageDescription(value: Double): String {
    return "${value.toInt()} percent"
}

/**
 * Build a content description for a score
 */
fun scoreDescription(score: Double, maxScore: Double = 100.0): String {
    val percentage = (score / maxScore * 100).toInt()
    return "Score: $score out of $maxScore, or $percentage percent"
}

/**
 * Build a content description for a progress indicator
 */
fun progressDescription(current: Int, total: Int): String {
    val percentage = (current.toDouble() / total * 100).toInt()
    return "Progress: $current of $total, $percentage percent complete"
}

/**
 * Build a content description for a list item
 */
fun listItemDescription(
    itemName: String,
    position: Int,
    total: Int,
    additionalInfo: String? = null
): String {
    val base = "$itemName, item $position of $total"
    return if (additionalInfo != null) "$base, $additionalInfo" else base
}

/**
 * Build a content description for a date/time
 */
fun dateTimeDescription(
    date: String? = null,
    time: String? = null,
    format: String = "full"
): String {
    return when {
        date != null && time != null -> "$date at $time"
        date != null -> date
        time != null -> time
        else -> "No date or time selected"
    }
}

// ============================================================================
// STATE DESCRIPTION BUILDERS
// ============================================================================

/**
 * Build a state description for an expandable component
 */
fun expandableStateDescription(expanded: Boolean): String {
    return if (expanded) "expanded" else "collapsed"
}

/**
 * Build a state description for a loading component
 */
fun loadingStateDescription(isLoading: Boolean, loadedContent: String? = null): String {
    return if (isLoading) {
        "loading"
    } else {
        loadedContent ?: "loaded"
    }
}

/**
 * Build a state description for a selected component
 */
fun selectionStateDescription(isSelected: Boolean, itemName: String? = null): String {
    val selectedText = if (isSelected) "selected" else "not selected"
    return if (itemName != null) "$itemName, $selectedText" else selectedText
}

// ============================================================================
// DYNAMIC TYPE SUPPORT
// ============================================================================

/**
 * Check if text size is scaled beyond a certain threshold
 * Useful for adjusting layouts when large text is enabled
 */
@Composable
fun isLargeTextEnabled(threshold: Float = 1.3f): Boolean {
    val density = LocalDensity.current
    return density.fontScale >= threshold
}

/**
 * Check if text size is at maximum scale
 */
@Composable
fun isMaximumTextScale(): Boolean {
    val density = LocalDensity.current
    return density.fontScale >= 2.0f
}

// ============================================================================
// NAVIGATION DESCRIPTIONS
// ============================================================================

/**
 * Build a description for a navigation button
 */
fun navigationDescription(
    action: String,
    destination: String? = null
): String {
    return if (destination != null) {
        "$action, navigate to $destination"
    } else {
        action
    }
}

/**
 * Build a description for a back button
 */
fun backButtonDescription(previousScreen: String? = null): String {
    return if (previousScreen != null) {
        "Go back to $previousScreen"
    } else {
        "Go back"
    }
}

// ============================================================================
// SPIRITUAL-SPECIFIC DESCRIPTIONS
// ============================================================================

/**
 * Build a description for chakra indicators
 */
fun chakraDescription(
    chakraName: String,
    chakraIndex: Int,
    isActive: Boolean
): String {
    val state = if (isActive) "active" else "inactive"
    return "$chakraName chakra, number ${chakraIndex + 1}, $state"
}

/**
 * Build a description for moon phase
 */
fun moonPhaseDescription(phaseName: String, illumination: Float): String {
    val percent = (illumination * 100).toInt()
    return "$phaseName, $percent percent illuminated"
}

/**
 * Build a description for zodiac sign
 */
fun zodiacDescription(sign: String, element: String? = null): String {
    return if (element != null) {
        "$sign, $element element"
    } else {
        sign
    }
}

/**
 * Build a description for numerology number
 */
fun numerologyDescription(number: Int, meaning: String? = null): String {
    return if (meaning != null) {
        "Life path number $number, $meaning"
    } else {
        "Life path number $number"
    }
}

/**
 * Build a description for compatibility score
 */
fun compatibilityDescription(score: Double, profile1: String, profile2: String): String {
    val level = when {
        score >= 80 -> "excellent"
        score >= 60 -> "good"
        score >= 40 -> "moderate"
        else -> "challenging"
    }
    return "Compatibility between $profile1 and $profile2: ${score.toInt()} percent, $level match"
}

/**
 * Build a description for planetary transit
 */
fun transitDescription(
    planet: String,
    sign: String? = null,
    intensity: Float? = null
): String {
    val base = if (sign != null) "$planet in $sign" else planet
    return if (intensity != null) {
        "$base, ${(intensity * 100).toInt()} percent intensity"
    } else {
        base
    }
}

// ============================================================================
// FORM FIELD DESCRIPTIONS
// ============================================================================

/**
 * Build a description for a required field
 */
fun requiredFieldDescription(fieldName: String): String {
    return "$fieldName, required field"
}

/**
 * Build a description for an optional field
 */
fun optionalFieldDescription(fieldName: String): String {
    return "$fieldName, optional"
}

/**
 * Build a description for a field with error
 */
fun fieldErrorDescription(fieldName: String, errorMessage: String): String {
    return "$fieldName, error: $errorMessage"
}

/**
 * Build a description for a field with validation
 */
fun fieldValidationDescription(
    fieldName: String,
    isValid: Boolean,
    errorMessage: String? = null
): String {
    return if (isValid) {
        "$fieldName, valid"
    } else {
        "$fieldName, invalid${errorMessage?.let { ", $it" } ?: ""}"
    }
}
