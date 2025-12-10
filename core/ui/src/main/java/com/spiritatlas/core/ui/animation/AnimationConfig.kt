package com.spiritatlas.core.ui.animation

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityManager
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService

/**
 * Animation Configuration System
 *
 * Provides buttery smooth 60 FPS animations with:
 * - Accessibility support (reduce motion)
 * - Adaptive performance based on device capabilities
 * - Contextual animation speeds (first-time vs repeated)
 * - Performance monitoring and optimization
 */

// ============================================================================
// EASING FUNCTIONS - Refined for spiritual animations
// ============================================================================

object SpiritualEasing {
  /**
   * Gentle spiritual easing - smooth and flowing
   */
  val Gentle: Easing = FastOutSlowInEasing

  /**
   * Breathing rhythm - mimics natural breath cycle
   * In (40%) -> Hold (20%) -> Out (40%)
   */
  val Breath: Easing = CubicBezierEasing(0.4f, 0.0f, 0.6f, 1.0f)

  /**
   * Floating levitation - ethereal movement
   */
  val Float: Easing = CubicBezierEasing(0.25f, 0.46f, 0.45f, 0.94f)

  /**
   * Energy pulse - rhythmic heartbeat
   */
  val Pulse: Easing = CubicBezierEasing(0.45f, 0.05f, 0.55f, 0.95f)

  /**
   * Wave motion - ocean-like flow
   */
  val Wave: Easing = CubicBezierEasing(0.36f, 0.0f, 0.66f, -0.56f)

  /**
   * Anticipation - wind-up before action
   */
  val Anticipation: Easing = CubicBezierEasing(0.6f, -0.28f, 0.735f, 0.045f)

  /**
   * Overshoot - delightful bounce
   */
  val Overshoot: Easing = CubicBezierEasing(0.175f, 0.885f, 0.32f, 1.275f)
}

// ============================================================================
// ANIMATION DURATIONS - Performance optimized
// ============================================================================

object AnimationDurations {
  // Micro-interactions (100-200ms)
  const val Instant = 0       // For reduce motion
  const val Quick = 150       // Button press, hover
  const val Micro = 200       // Quick feedback

  // Standard interactions (200-400ms)
  const val Fast = 250        // Snappy transitions
  const val Medium = 350      // Standard UI transitions
  const val Standard = 400    // Comfortable pace

  // Intentional transitions (400-700ms)
  const val Slow = 600        // Deliberate changes
  const val Mystical = 700    // Spiritual moments

  // Long animations (1000ms+)
  const val Breath = 4000     // Full breath cycle
  const val Pulse = 2000      // Energy pulse
  const val Float = 3000      // Floating motion
  const val Wave = 2500       // Wave motion
  const val Celestial = 20000 // Zodiac wheel, planets

  // First-time experience (slower for learning)
  const val FirstTimeMultiplier = 1.3f

  // Repeated action (faster for efficiency)
  const val RepeatedMultiplier = 0.7f
}

// ============================================================================
// ANIMATION CONFIGURATION
// ============================================================================

/**
 * Configuration for animation system with accessibility support
 */
@Stable
data class AnimationConfig(
  /**
   * Whether to reduce motion for accessibility
   */
  val reduceMotion: Boolean = false,

  /**
   * Device performance tier (affects particle counts, quality)
   */
  val performanceTier: PerformanceTier = PerformanceTier.HIGH,

  /**
   * Whether this is the user's first time seeing this animation
   */
  val isFirstTime: Boolean = true,

  /**
   * Speed multiplier for all animations (1.0 = normal)
   */
  val speedMultiplier: Float = 1.0f,

  /**
   * Enable performance monitoring (FPS counter, debug info)
   */
  val enablePerformanceMonitoring: Boolean = false,

  /**
   * Particle count multiplier (1.0 = normal, 0.5 = half particles)
   */
  val particleMultiplier: Float = 1.0f
) {
  /**
   * Get adjusted duration based on configuration
   */
  fun adjustDuration(baseDuration: Int): Int {
    if (reduceMotion) return 0

    var adjusted = baseDuration * speedMultiplier

    // First-time experience is slower for learning
    if (isFirstTime) {
      adjusted *= AnimationDurations.FirstTimeMultiplier
    } else {
      // Repeated actions are faster for efficiency
      adjusted *= AnimationDurations.RepeatedMultiplier
    }

    return adjusted.toInt()
  }

  /**
   * Get adjusted particle count based on performance tier
   */
  fun adjustParticleCount(baseCount: Int): Int {
    if (reduceMotion) return 0

    val tierMultiplier = when (performanceTier) {
      PerformanceTier.HIGH -> 1.0f
      PerformanceTier.MEDIUM -> 0.7f
      PerformanceTier.LOW -> 0.4f
    }

    return (baseCount * tierMultiplier * particleMultiplier).toInt().coerceAtLeast(1)
  }

  /**
   * Get animation spec with appropriate settings
   */
  fun <T> getAnimationSpec(
    baseDuration: Int,
    easing: Easing = SpiritualEasing.Gentle
  ): AnimationSpec<T> {
    return if (reduceMotion) {
      tween(0)
    } else {
      tween(
        durationMillis = adjustDuration(baseDuration),
        easing = easing
      )
    }
  }

  /**
   * Get spring animation spec
   */
  fun <T> getSpringSpec(
    dampingRatio: Float = Spring.DampingRatioMediumBouncy,
    stiffness: Float = Spring.StiffnessMedium
  ): AnimationSpec<T> {
    return if (reduceMotion) {
      spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Float.MAX_VALUE // Instant
      )
    } else {
      spring(
        dampingRatio = dampingRatio,
        stiffness = stiffness
      )
    }
  }
}

/**
 * Device performance tier for adaptive rendering
 */
enum class PerformanceTier {
  /**
   * High-end devices (flagship phones, tablets)
   * - Full particle counts
   * - Complex geometry
   * - All effects enabled
   */
  HIGH,

  /**
   * Mid-range devices
   * - 70% particle counts
   * - Simplified geometry
   * - Essential effects only
   */
  MEDIUM,

  /**
   * Low-end devices or battery saver
   * - 40% particle counts
   * - Minimal geometry
   * - Core animations only
   */
  LOW
}

// ============================================================================
// ACCESSIBILITY HELPERS
// ============================================================================

/**
 * Check if reduce motion is enabled in system accessibility settings
 */
fun Context.isReduceMotionEnabled(): Boolean {
  val accessibilityManager = getSystemService<AccessibilityManager>()

  // Check if accessibility service is enabled
  if (accessibilityManager?.isEnabled != true) {
    return false
  }

  // On Android 12+ (API 31+), check for EXTRA_ACCESSIBILITY_LIST_ATTRIBUTE_DISABLED_ANIMATIONS
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    // Use reflection to access the animation duration scale
    try {
      val animationScaleField = AccessibilityManager::class.java
        .getDeclaredField("mAccessibilityAnimationScale")
      animationScaleField.isAccessible = true
      val scale = animationScaleField.getFloat(accessibilityManager)
      return scale == 0f
    } catch (e: Exception) {
      // Fall back to checking if any accessibility services are enabled
      // that might affect animations
    }
  }

  // Fallback: assume reduce motion if accessibility is enabled
  // and the user has turned on TalkBack or other a11y services
  @Suppress("DEPRECATION")
  return accessibilityManager.getEnabledAccessibilityServiceList(
    AccessibilityServiceInfo.FEEDBACK_ALL_MASK
  ).isNotEmpty()
}

/**
 * Detect device performance tier based on hardware capabilities
 */
fun Context.detectPerformanceTier(): PerformanceTier {
  // Check available memory
  val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
  val memoryInfo = android.app.ActivityManager.MemoryInfo()
  activityManager.getMemoryInfo(memoryInfo)

  val totalMemoryMB = memoryInfo.totalMem / (1024 * 1024)

  // Check CPU cores
  val cpuCores = Runtime.getRuntime().availableProcessors()

  return when {
    // High-end: 6GB+ RAM, 8+ cores
    totalMemoryMB >= 6000 && cpuCores >= 8 -> PerformanceTier.HIGH

    // Mid-range: 4GB+ RAM, 6+ cores
    totalMemoryMB >= 4000 && cpuCores >= 6 -> PerformanceTier.MEDIUM

    // Low-end: everything else
    else -> PerformanceTier.LOW
  }
}

// ============================================================================
// COMPOSABLE HELPERS
// ============================================================================

/**
 * Remember animation configuration with accessibility support
 */
@Composable
fun rememberAnimationConfig(
  isFirstTime: Boolean = false,
  speedMultiplier: Float = 1.0f,
  enablePerformanceMonitoring: Boolean = false
): AnimationConfig {
  val context = LocalContext.current

  return remember(isFirstTime, speedMultiplier) {
    AnimationConfig(
      reduceMotion = context.isReduceMotionEnabled(),
      performanceTier = context.detectPerformanceTier(),
      isFirstTime = isFirstTime,
      speedMultiplier = speedMultiplier,
      enablePerformanceMonitoring = enablePerformanceMonitoring
    )
  }
}

/**
 * Create a configuration with reduced motion for accessibility
 */
fun AnimationConfig.withReducedMotion(): AnimationConfig {
  return copy(
    reduceMotion = true,
    particleMultiplier = 0f
  )
}

/**
 * Create a configuration optimized for low-end devices
 */
fun AnimationConfig.withLowPerformance(): AnimationConfig {
  return copy(
    performanceTier = PerformanceTier.LOW,
    particleMultiplier = 0.4f
  )
}

// ============================================================================
// STAGGER ANIMATION HELPERS
// ============================================================================

/**
 * Calculate stagger delay for list item animations
 * Creates cascading reveal effect
 */
fun calculateStaggerDelay(
  index: Int,
  baseDelay: Int = 50,
  maxDelay: Int = 300,
  reduceMotion: Boolean = false
): Int {
  if (reduceMotion) return 0
  return (index * baseDelay).coerceAtMost(maxDelay)
}

/**
 * Stagger configuration for list animations
 */
data class StaggerConfig(
  val baseDelay: Int = 50,
  val maxDelay: Int = 300,
  val maxItems: Int = 20, // Don't stagger after this many items
  val easing: Easing = SpiritualEasing.Gentle
)

/**
 * Get stagger delay with configuration
 */
fun StaggerConfig.getDelay(index: Int, reduceMotion: Boolean = false): Int {
  if (reduceMotion || index >= maxItems) return 0
  return (index * baseDelay).coerceAtMost(maxDelay)
}
