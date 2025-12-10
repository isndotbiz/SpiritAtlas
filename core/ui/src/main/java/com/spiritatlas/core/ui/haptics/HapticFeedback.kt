package com.spiritatlas.core.ui.haptics

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * Haptic feedback types for spiritual interactions
 * Each type has a carefully tuned vibration pattern for optimal user experience
 */
enum class HapticFeedbackType {
    /** Light tap for subtle UI interactions (buttons, switches) */
    LIGHT,

    /** Medium tap for selections and state changes */
    MEDIUM,

    /** Heavy tap for important actions and confirmations */
    HEAVY,

    /** Selection change (smooth, single pulse) */
    SELECTION,

    /** Success feedback (double tap pattern: tap-pause-tap) */
    SUCCESS,

    /** Warning feedback (single medium pulse) */
    WARNING,

    /** Error feedback (three short rapid taps) */
    ERROR,

    /** Navigation feedback (subtle pulse for screen transitions) */
    NAVIGATION,

    /** Spiritual pulse (gentle flowing pattern for mystical moments) */
    SPIRITUAL_PULSE;
}

/**
 * Enhanced haptic feedback controller with VibrationEffect API
 * Provides rich, contextual haptic feedback for spiritual interactions
 */
class HapticFeedbackController(private val context: Context) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        vibratorManager?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    private val preferences = HapticPreferences(context)

    /**
     * Perform haptic feedback based on the specified type
     * Automatically checks if haptics are enabled in user preferences
     */
    fun performHaptic(type: HapticFeedbackType) {
        if (!preferences.isHapticEnabled() || vibrator == null || !vibrator.hasVibrator()) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = when (type) {
                HapticFeedbackType.LIGHT -> {
                    // Single short pulse (10ms)
                    VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE)
                }

                HapticFeedbackType.MEDIUM -> {
                    // Single medium pulse (20ms)
                    VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE)
                }

                HapticFeedbackType.HEAVY -> {
                    // Single strong pulse (30ms)
                    VibrationEffect.createOneShot(30, 200)
                }

                HapticFeedbackType.SELECTION -> {
                    // Smooth single pulse (15ms)
                    VibrationEffect.createOneShot(15, 100)
                }

                HapticFeedbackType.SUCCESS -> {
                    // Double tap pattern: tap-pause-tap
                    // Pattern: [delay, vibrate, pause, vibrate]
                    val timings = longArrayOf(0, 15, 50, 15)
                    val amplitudes = intArrayOf(0, 150, 0, 150)
                    VibrationEffect.createWaveform(timings, amplitudes, -1)
                }

                HapticFeedbackType.WARNING -> {
                    // Single medium pulse (25ms)
                    VibrationEffect.createOneShot(25, 150)
                }

                HapticFeedbackType.ERROR -> {
                    // Three short rapid taps
                    // Pattern: [delay, tap, pause, tap, pause, tap]
                    val timings = longArrayOf(0, 10, 30, 10, 30, 10)
                    val amplitudes = intArrayOf(0, 180, 0, 180, 0, 180)
                    VibrationEffect.createWaveform(timings, amplitudes, -1)
                }

                HapticFeedbackType.NAVIGATION -> {
                    // Very subtle pulse for screen transitions (8ms)
                    VibrationEffect.createOneShot(8, 80)
                }

                HapticFeedbackType.SPIRITUAL_PULSE -> {
                    // Gentle flowing pulse pattern
                    // Pattern: [delay, pulse, fade, pulse, fade, pulse]
                    val timings = longArrayOf(0, 100, 200, 100, 200, 100)
                    val amplitudes = intArrayOf(0, 60, 0, 90, 0, 60)
                    VibrationEffect.createWaveform(timings, amplitudes, -1)
                }
            }

            vibrator.vibrate(effect)
        } else {
            // Fallback for older Android versions
            @Suppress("DEPRECATION")
            when (type) {
                HapticFeedbackType.LIGHT -> vibrator.vibrate(10)
                HapticFeedbackType.MEDIUM -> vibrator.vibrate(20)
                HapticFeedbackType.HEAVY -> vibrator.vibrate(30)
                HapticFeedbackType.SELECTION -> vibrator.vibrate(15)
                HapticFeedbackType.SUCCESS -> vibrator.vibrate(longArrayOf(0, 15, 50, 15), -1)
                HapticFeedbackType.WARNING -> vibrator.vibrate(25)
                HapticFeedbackType.ERROR -> vibrator.vibrate(longArrayOf(0, 10, 30, 10, 30, 10), -1)
                HapticFeedbackType.NAVIGATION -> vibrator.vibrate(8)
                HapticFeedbackType.SPIRITUAL_PULSE -> vibrator.vibrate(longArrayOf(0, 100, 200, 100, 200, 100), -1)
            }
        }
    }

    /**
     * Perform a custom haptic pattern
     * @param timings Array of alternating delays and vibration durations (in milliseconds)
     * @param amplitudes Array of vibration amplitudes (0-255), must match timings length
     */
    fun performCustomPattern(timings: LongArray, amplitudes: IntArray) {
        if (!preferences.isHapticEnabled() || vibrator == null || !vibrator.hasVibrator()) {
            return
        }

        require(timings.size == amplitudes.size) {
            "Timings and amplitudes arrays must have the same length"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(timings, amplitudes, -1)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(timings, -1)
        }
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
