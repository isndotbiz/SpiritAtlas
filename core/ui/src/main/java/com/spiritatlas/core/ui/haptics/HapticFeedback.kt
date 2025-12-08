package com.spiritatlas.core.ui.haptics

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Haptic feedback types for spiritual interactions
 */
enum class HapticFeedbackType {
    LIGHT,
    MEDIUM,
    HEAVY,
    SELECTION,
    SUCCESS,
    WARNING,
    ERROR
}

/**
 * Haptic feedback controller stub
 * TODO: Implement actual haptic feedback using VibrationEffect API
 */
class HapticFeedbackController(private val context: Context) {
    fun performHaptic(type: HapticFeedbackType) {
        // Stub implementation - actual haptics would use VibrationEffect API
    }
}

/**
 * Remember a haptic feedback controller scoped to the composition
 */
@Composable
fun rememberHapticFeedback(): HapticFeedbackController {
    val context = LocalContext.current
    return remember { HapticFeedbackController(context) }
}

/**
 * Preferences for haptic feedback settings
 */
class HapticPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("haptic_prefs", Context.MODE_PRIVATE)

    fun isHapticEnabled(): Boolean = prefs.getBoolean("haptic_enabled", true)

    fun setHapticEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("haptic_enabled", enabled).apply()
    }
}
