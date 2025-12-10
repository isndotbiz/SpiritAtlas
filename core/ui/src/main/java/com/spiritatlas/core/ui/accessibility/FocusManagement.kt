package com.spiritatlas.core.ui.accessibility

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Focus Management and Keyboard Navigation Support
 *
 * Provides comprehensive focus indicator support for keyboard navigation
 * and accessibility compliance per WCAG 2.1 Success Criterion 2.4.7
 */

// ============================================================================
// FOCUS INDICATOR CONFIGURATION
// ============================================================================

/**
 * Configuration for focus indicators
 */
data class FocusIndicatorConfig(
    val color: Color = Color.White,
    val width: Dp = 4.dp,
    val offset: Dp = 2.dp,
    val cornerRadius: Dp = 12.dp,
    val highContrast: Boolean = false
)

/**
 * Default focus indicator configuration
 */
object FocusIndicatorDefaults {
    /** Standard focus indicator for light theme */
    val Light = FocusIndicatorConfig(
        color = Color(0xFF000000),
        width = 3.dp,
        offset = 2.dp,
        cornerRadius = 12.dp
    )

    /** Standard focus indicator for dark theme */
    val Dark = FocusIndicatorConfig(
        color = Color(0xFFFFFFFF),
        width = 3.dp,
        offset = 2.dp,
        cornerRadius = 12.dp
    )

    /** High contrast focus indicator */
    val HighContrast = FocusIndicatorConfig(
        color = Color(0xFFFFFF00), // Yellow for maximum visibility
        width = 4.dp,
        offset = 2.dp,
        cornerRadius = 12.dp,
        highContrast = true
    )
}

// ============================================================================
// FOCUS INDICATOR MODIFIERS
// ============================================================================

/**
 * Add a visible focus indicator when the component gains keyboard focus
 * Complies with WCAG 2.1 Success Criterion 2.4.7 (Focus Visible)
 *
 * @param config Focus indicator configuration
 * @param enabled Whether the focus indicator is enabled
 */
fun Modifier.focusIndicator(
    config: FocusIndicatorConfig = FocusIndicatorDefaults.Dark,
    enabled: Boolean = true
): Modifier = composed {
    if (!enabled) return@composed this

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    this
        .focusable(interactionSource = interactionSource)
        .then(
            if (isFocused) {
                Modifier.border(
                    width = config.width,
                    color = config.color,
                    shape = RoundedCornerShape(config.cornerRadius)
                )
            } else {
                Modifier
            }
        )
}

/**
 * Add a focus indicator with custom border
 */
fun Modifier.focusIndicatorWithBorder(
    focusedBorder: BorderStroke,
    unfocusedBorder: BorderStroke? = null,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(12.dp)
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    this
        .focusable(interactionSource = interactionSource)
        .border(
            border = if (isFocused) focusedBorder else (unfocusedBorder ?: BorderStroke(0.dp, Color.Transparent)),
            shape = shape
        )
}

/**
 * Make a component focusable with a custom focus indicator
 */
fun Modifier.customFocusable(
    focusRequester: FocusRequester? = null,
    enabled: Boolean = true,
    onFocusChanged: ((Boolean) -> Unit)? = null
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Call the focus change callback
    onFocusChanged?.invoke(isFocused)

    var modifier = this.focusable(
        enabled = enabled,
        interactionSource = interactionSource
    )

    if (focusRequester != null) {
        modifier = modifier.focusRequester(focusRequester)
    }

    modifier
}

// ============================================================================
// FOCUS ORDER HELPERS
// ============================================================================

/**
 * Focus order group for managing sequential keyboard navigation
 */
class FocusOrderGroup {
    private val focusRequesters = mutableListOf<FocusRequester>()
    private var currentIndex = 0

    /**
     * Add a focus requester to the group
     */
    fun addFocusRequester(): FocusRequester {
        val requester = FocusRequester()
        focusRequesters.add(requester)
        return requester
    }

    /**
     * Move focus to the next element in the group
     */
    fun focusNext() {
        currentIndex = (currentIndex + 1) % focusRequesters.size
        focusRequesters[currentIndex].requestFocus()
    }

    /**
     * Move focus to the previous element in the group
     */
    fun focusPrevious() {
        currentIndex = if (currentIndex == 0) {
            focusRequesters.size - 1
        } else {
            currentIndex - 1
        }
        focusRequesters[currentIndex].requestFocus()
    }

    /**
     * Move focus to a specific index
     */
    fun focusAt(index: Int) {
        if (index in focusRequesters.indices) {
            currentIndex = index
            focusRequesters[currentIndex].requestFocus()
        }
    }

    /**
     * Get the current focus index
     */
    fun getCurrentIndex(): Int = currentIndex
}

// ============================================================================
// SKIP NAVIGATION HELPERS
// ============================================================================

/**
 * Skip link for accessibility
 * Allows keyboard users to skip repetitive content
 */
@Composable
fun rememberSkipToContentFocusRequester(): FocusRequester {
    return remember { FocusRequester() }
}

// ============================================================================
// FOCUS TRAP FOR MODALS
// ============================================================================

/**
 * Focus trap for modal dialogs and sheets
 * Prevents focus from leaving the modal
 */
class FocusTrap {
    private val focusRequesters = mutableListOf<FocusRequester>()
    private var isTrapped = false

    /**
     * Add a focusable element to the trap
     */
    fun addElement(): FocusRequester {
        val requester = FocusRequester()
        focusRequesters.add(requester)
        return requester
    }

    /**
     * Activate the focus trap
     */
    fun activate() {
        isTrapped = true
        if (focusRequesters.isNotEmpty()) {
            focusRequesters.first().requestFocus()
        }
    }

    /**
     * Deactivate the focus trap
     */
    fun deactivate() {
        isTrapped = false
    }

    /**
     * Check if the trap is active
     */
    fun isActive(): Boolean = isTrapped

    /**
     * Handle focus at trap boundary
     */
    fun handleBoundary(isAtEnd: Boolean) {
        if (!isTrapped || focusRequesters.isEmpty()) return

        if (isAtEnd) {
            // Loop back to first element
            focusRequesters.first().requestFocus()
        } else {
            // Loop to last element
            focusRequesters.last().requestFocus()
        }
    }
}

/**
 * Remember a focus trap for a modal
 */
@Composable
fun rememberFocusTrap(): FocusTrap {
    return remember { FocusTrap() }
}

// ============================================================================
// ACCESSIBILITY ANNOUNCEMENTS
// ============================================================================

/**
 * Priority for accessibility announcements
 */
enum class AnnouncementPriority {
    /** Low priority, may be interrupted */
    LOW,
    /** Medium priority, default */
    MEDIUM,
    /** High priority, should not be interrupted */
    HIGH
}

/**
 * Accessibility announcement for screen readers
 * Use this to announce dynamic content changes
 */
data class AccessibilityAnnouncement(
    val message: String,
    val priority: AnnouncementPriority = AnnouncementPriority.MEDIUM
)

// ============================================================================
// KEYBOARD SHORTCUTS
// ============================================================================

/**
 * Common keyboard shortcuts for accessibility
 */
object AccessibilityKeyboardShortcuts {
    const val ESCAPE = "Escape"
    const val ENTER = "Enter"
    const val SPACE = "Space"
    const val TAB = "Tab"
    const val SHIFT_TAB = "Shift+Tab"
    const val ARROW_UP = "ArrowUp"
    const val ARROW_DOWN = "ArrowDown"
    const val ARROW_LEFT = "ArrowLeft"
    const val ARROW_RIGHT = "ArrowRight"
    const val HOME = "Home"
    const val END = "End"
    const val PAGE_UP = "PageUp"
    const val PAGE_DOWN = "PageDown"
}

/**
 * Keyboard shortcut description builder
 */
fun keyboardShortcutDescription(
    action: String,
    key: String,
    modifiers: List<String> = emptyList()
): String {
    val modifierText = if (modifiers.isNotEmpty()) {
        modifiers.joinToString(" + ") + " + "
    } else {
        ""
    }
    return "$action: $modifierText$key"
}
