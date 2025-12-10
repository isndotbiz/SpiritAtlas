package com.spiritatlas.core.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Optimized Animations with graphicsLayer
 *
 * All animations use graphicsLayer {} for hardware acceleration and better performance.
 * graphicsLayer moves operations to the GPU, reducing CPU load and preventing recomposition.
 *
 * Benefits:
 * - No recomposition on animation frame
 * - Hardware accelerated (GPU rendering)
 * - Smooth 60 FPS even with many animated elements
 * - Lower battery consumption
 */

// ============================================================================
// OPTIMIZED FADE ANIMATIONS
// ============================================================================

/**
 * Optimized fade-in with graphicsLayer
 * Uses hardware acceleration for smooth 60 FPS
 */
fun Modifier.optimizedFadeIn(
  visible: Boolean = true,
  durationMillis: Int = AnimationDurations.Medium,
  delay: Int = 0,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = config.getAnimationSpec(
      baseDuration = durationMillis,
      easing = SpiritualEasing.Gentle
    ),
    label = "optimizedFadeIn"
  )

  this.graphicsLayer {
    this.alpha = alpha
  }
}

/**
 * Staggered fade-in for list items
 * Creates cascading reveal effect
 */
fun Modifier.staggeredFadeIn(
  index: Int,
  visible: Boolean = true,
  staggerConfig: StaggerConfig = StaggerConfig(),
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val delay = staggerConfig.getDelay(index, config.reduceMotion)

  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = config.adjustDuration(AnimationDurations.Medium),
      delayMillis = delay,
      easing = staggerConfig.easing
    ),
    label = "staggeredFadeIn_$index"
  )

  this.graphicsLayer {
    this.alpha = alpha
  }
}

// ============================================================================
// OPTIMIZED SCALE ANIMATIONS
// ============================================================================

/**
 * Optimized scale animation with graphicsLayer
 * Perfect for buttons and interactive elements
 */
fun Modifier.optimizedScale(
  targetScale: Float = 1f,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val scale by animateFloatAsState(
    targetValue = targetScale,
    animationSpec = config.getSpringSpec(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "optimizedScale"
  )

  this.graphicsLayer {
    scaleX = scale
    scaleY = scale
  }
}

/**
 * Optimized button press animation
 * Scales down on press with spring bounce
 */
fun Modifier.optimizedButtonPress(
  pressed: Boolean = false,
  enabled: Boolean = true,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val targetScale = when {
    !enabled -> 0.95f
    pressed -> 0.92f
    else -> 1f
  }

  val scale by animateFloatAsState(
    targetValue = targetScale,
    animationSpec = if (config.reduceMotion) {
      spring(stiffness = Float.MAX_VALUE)
    } else {
      spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
      )
    },
    label = "buttonPress"
  )

  this.graphicsLayer {
    scaleX = scale
    scaleY = scale
  }
}

/**
 * Optimized scale with overshoot for delightful moments
 * Scales beyond target then settles back
 */
fun Modifier.scaleWithOvershoot(
  targetScale: Float = 1f,
  overshoot: Float = 1.15f,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val scale = remember { Animatable(0f) }

  LaunchedEffect(targetScale) {
    if (config.reduceMotion) {
      scale.snapTo(targetScale)
    } else {
      // Animate to overshoot
      scale.animateTo(
        targetValue = overshoot,
        animationSpec = tween(
          durationMillis = config.adjustDuration(AnimationDurations.Fast),
          easing = SpiritualEasing.Anticipation
        )
      )
      // Settle back to target
      scale.animateTo(
        targetValue = targetScale,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioLowBouncy,
          stiffness = Spring.StiffnessMediumLow
        )
      )
    }
  }

  this.graphicsLayer {
    scaleX = scale.value
    scaleY = scale.value
  }
}

/**
 * Staggered scale animation for grid items
 */
fun Modifier.staggeredScaleIn(
  index: Int,
  visible: Boolean = true,
  staggerConfig: StaggerConfig = StaggerConfig(),
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val delay = staggerConfig.getDelay(index, config.reduceMotion)

  val scale by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "staggeredScale_$index"
  )

  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = config.adjustDuration(AnimationDurations.Medium),
      delayMillis = delay,
      easing = staggerConfig.easing
    ),
    label = "staggeredScaleAlpha_$index"
  )

  this.graphicsLayer {
    scaleX = scale
    scaleY = scale
    this.alpha = alpha
  }
}

// ============================================================================
// OPTIMIZED ROTATION ANIMATIONS
// ============================================================================

/**
 * Optimized continuous rotation with graphicsLayer
 */
@Composable
fun rememberOptimizedRotation(
  durationMillis: Int = 20000,
  clockwise: Boolean = true,
  config: AnimationConfig = AnimationConfig()
): Float {
  val rotation = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      rotation.animateTo(
        targetValue = if (clockwise) 360f else -360f,
        animationSpec = tween(
          durationMillis = config.adjustDuration(durationMillis),
          easing = androidx.compose.animation.core.LinearEasing
        )
      )
    }
  }

  return rotation.value
}

/**
 * Optimized rotation modifier with graphicsLayer
 */
fun Modifier.optimizedRotation(
  rotation: Float
): Modifier = this.graphicsLayer {
  rotationZ = rotation
}

// ============================================================================
// OPTIMIZED TRANSLATION ANIMATIONS
// ============================================================================

/**
 * Optimized floating animation with graphicsLayer
 * Uses translationY for hardware acceleration
 */
fun Modifier.optimizedFloating(
  range: Float = 10f,
  durationMillis: Int = AnimationDurations.Float,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val offset = remember { Animatable(-range) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      offset.animateTo(
        targetValue = range,
        animationSpec = tween(
          durationMillis = config.adjustDuration(durationMillis),
          easing = SpiritualEasing.Float
        )
      )
      // Reverse
      offset.animateTo(
        targetValue = -range,
        animationSpec = tween(
          durationMillis = config.adjustDuration(durationMillis),
          easing = SpiritualEasing.Float
        )
      )
    }
  }

  this.graphicsLayer {
    translationY = offset.value
  }
}

/**
 * Slide in from side with graphicsLayer optimization
 */
fun Modifier.optimizedSlideIn(
  visible: Boolean = true,
  fromLeft: Boolean = true,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val translationX by animateFloatAsState(
    targetValue = if (visible) 0f else if (fromLeft) -1000f else 1000f,
    animationSpec = config.getAnimationSpec(
      baseDuration = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    ),
    label = "slideIn"
  )

  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = config.getAnimationSpec(
      baseDuration = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    ),
    label = "slideInAlpha"
  )

  this.graphicsLayer {
    this.translationX = translationX
    this.alpha = alpha
  }
}

// ============================================================================
// OPTIMIZED BREATHING ANIMATION
// ============================================================================

/**
 * Optimized breathing animation for meditation elements
 * Uses graphicsLayer for smooth GPU rendering
 */
fun Modifier.optimizedBreathing(
  minScale: Float = 0.95f,
  maxScale: Float = 1.05f,
  durationMillis: Int = AnimationDurations.Breath,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val scale = remember { Animatable(minScale) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      while (true) {
        // Inhale
        scale.animateTo(
          targetValue = maxScale,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis / 2),
            easing = SpiritualEasing.Breath
          )
        )
        // Exhale
        scale.animateTo(
          targetValue = minScale,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis / 2),
            easing = SpiritualEasing.Breath
          )
        )
      }
    }
  }

  this.graphicsLayer {
    scaleX = scale.value
    scaleY = scale.value
  }
}

// ============================================================================
// OPTIMIZED PULSE ANIMATION
// ============================================================================

/**
 * Optimized pulsing animation for active elements
 */
fun Modifier.optimizedPulse(
  minAlpha: Float = 0.6f,
  maxAlpha: Float = 1.0f,
  durationMillis: Int = AnimationDurations.Pulse,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val alpha = remember { Animatable(minAlpha) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      while (true) {
        alpha.animateTo(
          targetValue = maxAlpha,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis / 2),
            easing = SpiritualEasing.Pulse
          )
        )
        alpha.animateTo(
          targetValue = minAlpha,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis / 2),
            easing = SpiritualEasing.Pulse
          )
        )
      }
    }
  }

  this.graphicsLayer {
    this.alpha = alpha.value
  }
}

// ============================================================================
// COMBINED OPTIMIZED ANIMATIONS
// ============================================================================

/**
 * Spiritual entrance with scale + fade + slight overshoot
 * Perfect for cards, modals, and important UI elements
 */
fun Modifier.spiritualEntrance(
  visible: Boolean = true,
  delay: Int = 0,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val scale = remember { Animatable(0f) }
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = config.adjustDuration(AnimationDurations.Slow),
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "spiritualEntranceAlpha"
  )

  LaunchedEffect(visible) {
    if (visible && !config.reduceMotion) {
      // Start from 0
      scale.snapTo(0f)
      // Overshoot to 1.1
      scale.animateTo(
        targetValue = 1.1f,
        animationSpec = tween(
          durationMillis = config.adjustDuration(AnimationDurations.Medium),
          delayMillis = delay,
          easing = SpiritualEasing.Overshoot
        )
      )
      // Settle to 1.0
      scale.animateTo(
        targetValue = 1.0f,
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioMediumBouncy,
          stiffness = Spring.StiffnessMediumLow
        )
      )
    } else if (visible) {
      scale.snapTo(1f)
    } else {
      scale.snapTo(0f)
    }
  }

  this.graphicsLayer {
    scaleX = scale.value
    scaleY = scale.value
    this.alpha = alpha
  }
}

/**
 * Card hover effect with elevation simulation
 */
fun Modifier.optimizedCardHover(
  hovered: Boolean = false,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val scale by animateFloatAsState(
    targetValue = if (hovered) 1.02f else 1f,
    animationSpec = if (config.reduceMotion) {
      spring(stiffness = Float.MAX_VALUE)
    } else {
      spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMediumLow
      )
    },
    label = "cardHoverScale"
  )

  val translationY by animateFloatAsState(
    targetValue = if (hovered) -4f else 0f,
    animationSpec = config.getSpringSpec(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "cardHoverTranslation"
  )

  this.graphicsLayer {
    scaleX = scale
    scaleY = scale
    this.translationY = translationY
    // Simulate elevation with shadow
    shadowElevation = if (hovered) 8f else 2f
  }
}

/**
 * Levitation animation with rotation for mystical elements
 */
fun Modifier.optimizedLevitation(
  yRange: Float = 15f,
  rotationRange: Float = 5f,
  durationMillis: Int = 4000,
  config: AnimationConfig = AnimationConfig()
): Modifier = composed {
  val yOffset = remember { Animatable(-yRange) }
  val rotation = remember { Animatable(-rotationRange) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      while (true) {
        // Float up and rotate right
        yOffset.animateTo(
          targetValue = yRange,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis),
            easing = SpiritualEasing.Float
          )
        )
        rotation.animateTo(
          targetValue = rotationRange,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis),
            easing = SpiritualEasing.Float
          )
        )
        // Float down and rotate left
        yOffset.animateTo(
          targetValue = -yRange,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis),
            easing = SpiritualEasing.Float
          )
        )
        rotation.animateTo(
          targetValue = -rotationRange,
          animationSpec = tween(
            durationMillis = config.adjustDuration(durationMillis),
            easing = SpiritualEasing.Float
          )
        )
      }
    }
  }

  this.graphicsLayer {
    translationY = yOffset.value
    rotationZ = rotation.value
  }
}
