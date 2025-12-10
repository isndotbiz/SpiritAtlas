package com.spiritatlas.core.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * Theme Animations for SpiritAtlas
 *
 * Provides smooth, accessible transitions between theme modes:
 * - Light to Dark theme
 * - Dark to High Contrast
 * - Standard to Organic shapes
 * - Color palette transitions
 *
 * All animations respect user's motion preferences and accessibility settings
 */

// ============================================================================
// ANIMATION DURATION CONSTANTS
// ============================================================================

object ThemeAnimationDurations {
  const val INSTANT = 0               // No animation (for reduced motion)
  const val FAST = 150                // Quick transitions (150ms)
  const val NORMAL = 300              // Standard transitions (300ms)
  const val SLOW = 500                // Deliberate transitions (500ms)
  const val SPIRITUAL = 700           // Meditative transitions (700ms)
}

// ============================================================================
// ANIMATION SPECIFICATIONS
// ============================================================================

/**
 * Spring animation for smooth, natural color transitions
 * Used for theme switching to create flowing, organic feel
 */
object ThemeAnimationSpecs {
  /**
   * Gentle spring for smooth color transitions
   * Provides subtle bounce that feels natural
   */
  val gentleSpring: SpringSpec<Color> = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMediumLow
  )

  /**
   * Smooth spring for theme mode changes
   * More subtle than gentleSpring, no bounce
   */
  val smoothSpring: SpringSpec<Color> = spring(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium
  )

  /**
   * Quick spring for reduced motion preference
   * Fast and direct, minimal animation
   */
  val quickSpring: SpringSpec<Color> = spring(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessHigh
  )

  /**
   * Spiritual spring for meditative transitions
   * Slower, more contemplative animation
   */
  val spiritualSpring: SpringSpec<Color> = spring(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness = Spring.StiffnessLow
  )
}

/**
 * Tween animations for precise, controlled transitions
 */
object ThemeTweenSpecs {
  /**
   * Fast tween for quick theme changes
   */
  fun <T> fastTween(): AnimationSpec<T> = tween(
    durationMillis = ThemeAnimationDurations.FAST,
    easing = FastOutSlowInEasing
  )

  /**
   * Normal tween for standard theme transitions
   */
  fun <T> normalTween(): AnimationSpec<T> = tween(
    durationMillis = ThemeAnimationDurations.NORMAL,
    easing = FastOutSlowInEasing
  )

  /**
   * Spiritual tween for meditative transitions
   */
  fun <T> spiritualTween(): AnimationSpec<T> = tween(
    durationMillis = ThemeAnimationDurations.SPIRITUAL,
    easing = FastOutSlowInEasing
  )
}

// ============================================================================
// COMPOSABLE ANIMATION HELPERS
// ============================================================================

/**
 * Animate a color change with spiritual theming
 * Respects user's motion preference
 *
 * @param targetColor The target color to animate to
 * @param reduceMotion Whether to reduce motion for accessibility
 * @param label Animation label for debugging
 */
@Composable
fun animateThemeColorAsState(
  targetColor: Color,
  reduceMotion: Boolean = false,
  label: String = "theme_color"
): State<Color> {
  return animateColorAsState(
    targetValue = targetColor,
    animationSpec = when {
      reduceMotion -> ThemeAnimationSpecs.quickSpring
      else -> ThemeAnimationSpecs.smoothSpring
    },
    label = label
  )
}

/**
 * Animate multiple colors for theme transitions
 * Returns a list of animated color states
 */
@Composable
fun animateThemeColorsAsState(
  targetColors: List<Color>,
  reduceMotion: Boolean = false,
  labelPrefix: String = "theme_color"
): List<State<Color>> {
  return targetColors.mapIndexed { index, color ->
    animateThemeColorAsState(
      targetColor = color,
      reduceMotion = reduceMotion,
      label = "${labelPrefix}_$index"
    )
  }
}

/**
 * Animate elevation changes during theme transitions
 */
@Composable
fun animateThemeElevationAsState(
  targetElevation: Dp,
  reduceMotion: Boolean = false,
  label: String = "theme_elevation"
): State<Dp> {
  return animateDpAsState(
    targetValue = targetElevation,
    animationSpec = when {
      reduceMotion -> ThemeTweenSpecs.fastTween()
      else -> ThemeTweenSpecs.normalTween()
    },
    label = label
  )
}

/**
 * Animate opacity changes during theme transitions
 * Useful for crossfade effects
 */
@Composable
fun animateThemeOpacityAsState(
  targetOpacity: Float,
  reduceMotion: Boolean = false,
  label: String = "theme_opacity"
): State<Float> {
  return animateFloatAsState(
    targetValue = targetOpacity,
    animationSpec = when {
      reduceMotion -> ThemeTweenSpecs.fastTween()
      else -> ThemeTweenSpecs.normalTween()
    },
    label = label
  )
}

// ============================================================================
// THEME TRANSITION MODES
// ============================================================================

/**
 * Theme transition mode for different animation styles
 */
enum class ThemeTransitionMode {
  /**
   * Instant transition with no animation
   * Used when user has motion sensitivity enabled
   */
  INSTANT,

  /**
   * Quick fade transition
   * Used for subtle theme changes
   */
  FADE,

  /**
   * Smooth color transition
   * Used for standard theme switching
   */
  SMOOTH,

  /**
   * Spiritual meditative transition
   * Used for deliberate theme changes with emphasis
   */
  SPIRITUAL,

  /**
   * Crossfade transition
   * Used when switching between vastly different themes
   */
  CROSSFADE
}

/**
 * Get the appropriate animation spec for a theme transition mode
 */
fun getAnimationSpecForMode(mode: ThemeTransitionMode): SpringSpec<Color> {
  return when (mode) {
    ThemeTransitionMode.INSTANT -> spring(
      stiffness = Float.MAX_VALUE // Effectively instant
    )
    ThemeTransitionMode.FADE,
    ThemeTransitionMode.SMOOTH -> ThemeAnimationSpecs.smoothSpring
    ThemeTransitionMode.SPIRITUAL -> ThemeAnimationSpecs.spiritualSpring
    ThemeTransitionMode.CROSSFADE -> ThemeAnimationSpecs.gentleSpring
  }
}

// ============================================================================
// GRADIENT ANIMATION HELPERS
// ============================================================================

/**
 * Animate gradient color transitions
 * Smoothly transitions between two gradients
 */
@Composable
fun animateGradientColorsAsState(
  targetColors: List<Color>,
  reduceMotion: Boolean = false,
  labelPrefix: String = "gradient"
): List<Color> {
  return targetColors.mapIndexed { index, targetColor ->
    val animatedColor by animateThemeColorAsState(
      targetColor = targetColor,
      reduceMotion = reduceMotion,
      label = "${labelPrefix}_$index"
    )
    animatedColor
  }
}

// ============================================================================
// ACCESSIBILITY HELPERS
// ============================================================================

/**
 * Determine if motion should be reduced based on theme configuration
 */
fun shouldReduceMotion(config: SpiritualThemeConfig): Boolean {
  return config.reduceMotion
}

/**
 * Get animation duration based on motion preferences
 */
fun getAnimationDuration(
  config: SpiritualThemeConfig,
  defaultDuration: Int = ThemeAnimationDurations.NORMAL
): Int {
  return if (config.reduceMotion) {
    ThemeAnimationDurations.INSTANT
  } else {
    defaultDuration
  }
}

// ============================================================================
// THEME CROSSFADE ANIMATION
// ============================================================================

/**
 * Crossfade animation state for theme transitions
 * Provides smooth opacity transitions between themes
 */
data class ThemeCrossfadeState(
  val fromOpacity: Float,
  val toOpacity: Float
)

/**
 * Calculate crossfade state for smooth theme transitions
 * Returns opacity values for old and new theme layers
 */
@Composable
fun animateThemeCrossfade(
  isTransitioning: Boolean,
  reduceMotion: Boolean = false
): ThemeCrossfadeState {
  val targetOpacity = if (isTransitioning) 0f else 1f

  val fromOpacity by animateThemeOpacityAsState(
    targetOpacity = if (isTransitioning) 0f else 1f,
    reduceMotion = reduceMotion,
    label = "theme_from_opacity"
  )

  val toOpacity by animateThemeOpacityAsState(
    targetOpacity = if (isTransitioning) 1f else 0f,
    reduceMotion = reduceMotion,
    label = "theme_to_opacity"
  )

  return ThemeCrossfadeState(
    fromOpacity = fromOpacity,
    toOpacity = toOpacity
  )
}

// ============================================================================
// SPIRITUAL TRANSITION EFFECTS
// ============================================================================

/**
 * Spiritual breathing animation for meditative transitions
 * Provides gentle pulsing effect during theme changes
 */
@Composable
fun animateSpiritualBreathing(
  isActive: Boolean,
  reduceMotion: Boolean = false
): State<Float> {
  val targetScale = if (isActive) 1.02f else 1.0f

  return animateFloatAsState(
    targetValue = targetScale,
    animationSpec = if (reduceMotion) {
      spring(stiffness = Spring.StiffnessHigh)
    } else {
      spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessVeryLow
      )
    },
    label = "spiritual_breathing"
  )
}

// ============================================================================
// THEME SWITCH COORDINATOR
// ============================================================================

/**
 * Coordinates complex theme transitions with multiple animated properties
 * Ensures smooth, synchronized transitions across all theme elements
 */
class ThemeSwitchCoordinator(
  private val config: SpiritualThemeConfig
) {
  private val duration = getAnimationDuration(config)

  /**
   * Get the color animation spec based on configuration
   */
  fun getColorAnimationSpec(): SpringSpec<Color> {
    return if (config.reduceMotion) {
      ThemeAnimationSpecs.quickSpring
    } else {
      ThemeAnimationSpecs.smoothSpring
    }
  }

  /**
   * Get the shape animation spec based on configuration
   */
  fun <T> getShapeAnimationSpec(): AnimationSpec<T> {
    return if (config.reduceMotion) {
      ThemeTweenSpecs.fastTween()
    } else {
      ThemeTweenSpecs.normalTween()
    }
  }

  /**
   * Calculate transition delay for staggered animations
   * Creates cascading effect for visual interest
   */
  fun getStaggerDelay(index: Int, totalItems: Int): Int {
    if (config.reduceMotion) return 0

    val maxDelay = 100 // Maximum delay in milliseconds
    val delayPerItem = maxDelay / totalItems.coerceAtLeast(1)
    return index * delayPerItem
  }
}
