package com.spiritatlas.core.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Animation Choreography System
 *
 * Coordinates multiple animations to create meaningful visual narratives.
 * Animations tell stories through motion, creating spiritual journeys.
 *
 * Key Concepts:
 * - Sequence: Animations play one after another
 * - Parallel: Multiple animations play simultaneously
 * - Stagger: Animations start with delays, creating waves
 * - Theme: Related animations with shared timing and meaning
 */

// ============================================================================
// CHOREOGRAPHY DATA CLASSES
// ============================================================================

/**
 * An animation step in a choreographed sequence
 */
data class AnimationStep(
  val name: String,
  val startDelay: Long = 0,
  val duration: Long = 300,
  val targetAlpha: Float = 1f,
  val targetScale: Float = 1f,
  val targetTranslationY: Float = 0f,
  val targetRotation: Float = 0f,
  val easing: androidx.compose.animation.core.Easing = SpiritualEasing.Gentle
)

/**
 * A choreographed animation sequence
 */
data class ChoreographySequence(
  val name: String,
  val steps: List<AnimationStep>,
  val loop: Boolean = false
)

// ============================================================================
// PREDEFINED CHOREOGRAPHIES
// ============================================================================

object SpiritualChoreographies {
  /**
   * Chakra Awakening Sequence
   * Each chakra lights up from root to crown
   */
  fun chakraAwakening(): ChoreographySequence {
    val chakraNames = listOf("Root", "Sacral", "Solar Plexus", "Heart", "Throat", "Third Eye", "Crown")
    val steps = chakraNames.mapIndexed { index, name ->
      AnimationStep(
        name = name,
        startDelay = index * 200L,
        duration = 600,
        targetAlpha = 1f,
        targetScale = 1.2f,
        easing = SpiritualEasing.Overshoot
      )
    }
    return ChoreographySequence("Chakra Awakening", steps)
  }

  /**
   * Constellation Reveal Sequence
   * Stars appear, then lines connect them
   */
  fun constellationReveal(): ChoreographySequence {
    val steps = listOf(
      AnimationStep(
        name = "Stars Appear",
        startDelay = 0,
        duration = 1000,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Gentle
      ),
      AnimationStep(
        name = "Lines Connect",
        startDelay = 1200,
        duration = 1500,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Float
      ),
      AnimationStep(
        name = "Glow Pulse",
        startDelay = 2800,
        duration = 800,
        targetAlpha = 1f,
        targetScale = 1.05f,
        easing = SpiritualEasing.Pulse
      )
    )
    return ChoreographySequence("Constellation Reveal", steps)
  }

  /**
   * Energy Flow Sequence
   * Energy builds, flows, and releases
   */
  fun energyFlow(): ChoreographySequence {
    val steps = listOf(
      AnimationStep(
        name = "Gather Energy",
        startDelay = 0,
        duration = 800,
        targetAlpha = 0.6f,
        targetScale = 0.8f,
        easing = SpiritualEasing.Anticipation
      ),
      AnimationStep(
        name = "Channel Energy",
        startDelay = 800,
        duration = 600,
        targetAlpha = 1f,
        targetScale = 1.3f,
        easing = SpiritualEasing.Overshoot
      ),
      AnimationStep(
        name = "Release Energy",
        startDelay = 1400,
        duration = 1000,
        targetAlpha = 0.8f,
        targetScale = 1f,
        easing = SpiritualEasing.Float
      )
    )
    return ChoreographySequence("Energy Flow", steps, loop = true)
  }

  /**
   * Sacred Geometry Emergence
   * Geometry forms from center outward
   */
  fun sacredGeometryEmergence(): ChoreographySequence {
    val steps = listOf(
      AnimationStep(
        name = "Center Point",
        startDelay = 0,
        duration = 400,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Gentle
      ),
      AnimationStep(
        name = "Inner Circle",
        startDelay = 500,
        duration = 600,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Float
      ),
      AnimationStep(
        name = "Outer Circles",
        startDelay = 1200,
        duration = 1000,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Gentle
      ),
      AnimationStep(
        name = "Connecting Lines",
        startDelay = 2300,
        duration = 800,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Float
      )
    )
    return ChoreographySequence("Sacred Geometry Emergence", steps)
  }

  /**
   * Meditation Journey
   * Breathing rhythm with deepening awareness
   */
  fun meditationJourney(): ChoreographySequence {
    val steps = listOf(
      AnimationStep(
        name = "Settle In",
        startDelay = 0,
        duration = 2000,
        targetAlpha = 0.8f,
        targetScale = 0.95f,
        easing = SpiritualEasing.Breath
      ),
      AnimationStep(
        name = "Deep Breath",
        startDelay = 2000,
        duration = 4000,
        targetAlpha = 1f,
        targetScale = 1.05f,
        easing = SpiritualEasing.Breath
      ),
      AnimationStep(
        name = "Awareness Expands",
        startDelay = 6000,
        duration = 3000,
        targetAlpha = 1f,
        targetScale = 1.1f,
        easing = SpiritualEasing.Float
      )
    )
    return ChoreographySequence("Meditation Journey", steps, loop = true)
  }

  /**
   * Compatibility Analysis Reveal
   * Two profiles appear, energy flows between them
   */
  fun compatibilityReveal(): ChoreographySequence {
    val steps = listOf(
      AnimationStep(
        name = "Profile 1 Appears",
        startDelay = 0,
        duration = 500,
        targetAlpha = 1f,
        targetScale = 1f,
        targetTranslationY = 0f,
        easing = SpiritualEasing.Overshoot
      ),
      AnimationStep(
        name = "Profile 2 Appears",
        startDelay = 300,
        duration = 500,
        targetAlpha = 1f,
        targetScale = 1f,
        targetTranslationY = 0f,
        easing = SpiritualEasing.Overshoot
      ),
      AnimationStep(
        name = "Energy Connection",
        startDelay = 900,
        duration = 1200,
        targetAlpha = 1f,
        targetScale = 1f,
        easing = SpiritualEasing.Float
      ),
      AnimationStep(
        name = "Compatibility Score",
        startDelay = 2200,
        duration = 800,
        targetAlpha = 1f,
        targetScale = 1.05f,
        easing = SpiritualEasing.Overshoot
      )
    )
    return ChoreographySequence("Compatibility Reveal", steps)
  }
}

// ============================================================================
// CHOREOGRAPHY CONTROLLER
// ============================================================================

/**
 * Controls execution of choreographed animation sequences
 */
class ChoreographyController(
  val sequence: ChoreographySequence,
  val config: AnimationConfig = AnimationConfig()
) {
  private val stepStates = mutableMapOf<String, ChoreographyStepState>()

  /**
   * State for a single animation step
   */
  data class ChoreographyStepState(
    val alpha: Animatable<Float, *> = Animatable(0f),
    val scale: Animatable<Float, *> = Animatable(0f),
    val translationY: Animatable<Float, *> = Animatable(0f),
    val rotation: Animatable<Float, *> = Animatable(0f)
  )

  /**
   * Get or create state for a step
   */
  fun getStepState(stepName: String): ChoreographyStepState {
    return stepStates.getOrPut(stepName) { ChoreographyStepState() }
  }

  /**
   * Execute the choreography sequence
   */
  suspend fun execute() = coroutineScope {
    if (config.reduceMotion) {
      // Instant completion for accessibility
      sequence.steps.forEach { step ->
        val state = getStepState(step.name)
        state.alpha.snapTo(step.targetAlpha)
        state.scale.snapTo(step.targetScale)
        state.translationY.snapTo(step.targetTranslationY)
        state.rotation.snapTo(step.targetRotation)
      }
      return@coroutineScope
    }

    do {
      // Execute all steps with their delays
      sequence.steps.forEach { step ->
        launch {
          delay(step.startDelay)
          val state = getStepState(step.name)

          // Animate all properties in parallel
          launch {
            state.alpha.animateTo(
              targetValue = step.targetAlpha,
              animationSpec = tween(
                durationMillis = config.adjustDuration(step.duration.toInt()),
                easing = step.easing
              )
            )
          }
          launch {
            state.scale.animateTo(
              targetValue = step.targetScale,
              animationSpec = tween(
                durationMillis = config.adjustDuration(step.duration.toInt()),
                easing = step.easing
              )
            )
          }
          launch {
            state.translationY.animateTo(
              targetValue = step.targetTranslationY,
              animationSpec = tween(
                durationMillis = config.adjustDuration(step.duration.toInt()),
                easing = step.easing
              )
            )
          }
          launch {
            state.rotation.animateTo(
              targetValue = step.targetRotation,
              animationSpec = tween(
                durationMillis = config.adjustDuration(step.duration.toInt()),
                easing = step.easing
              )
            )
          }
        }
      }

      // Wait for sequence to complete
      val totalDuration = sequence.steps.maxOf { it.startDelay + it.duration }
      delay(totalDuration)

    } while (sequence.loop)
  }
}

// ============================================================================
// COMPOSABLE CHOREOGRAPHY MODIFIERS
// ============================================================================

/**
 * Apply choreographed animation to a composable
 */
fun Modifier.choreographed(
  controller: ChoreographyController,
  stepName: String
): Modifier = composed {
  val state = remember(controller, stepName) {
    controller.getStepState(stepName)
  }

  LaunchedEffect(controller) {
    controller.execute()
  }

  this.graphicsLayer {
    alpha = state.alpha.value
    scaleX = state.scale.value
    scaleY = state.scale.value
    translationY = state.translationY.value
    rotationZ = state.rotation.value
  }
}

/**
 * Create a choreography controller for a sequence
 */
@Composable
fun rememberChoreographyController(
  sequence: ChoreographySequence,
  config: AnimationConfig = rememberAnimationConfig()
): ChoreographyController {
  return remember(sequence, config) {
    ChoreographyController(sequence, config)
  }
}

// ============================================================================
// COORDINATED ANIMATIONS
// ============================================================================

/**
 * Coordinate multiple elements with shared timing
 */
data class CoordinatedAnimation(
  val name: String,
  val elements: List<String>,
  val timing: AnimationTiming
)

/**
 * Timing strategy for coordinated animations
 */
sealed class AnimationTiming {
  /**
   * All elements animate simultaneously
   */
  data object Parallel : AnimationTiming()

  /**
   * Elements animate one after another
   */
  data class Sequential(val gap: Long = 200) : AnimationTiming()

  /**
   * Elements animate with stagger delay
   */
  data class Staggered(val staggerDelay: Long = 50, val maxDelay: Long = 300) : AnimationTiming()

  /**
   * Custom timing per element
   */
  data class Custom(val delays: Map<String, Long>) : AnimationTiming()
}

/**
 * Calculate delay for an element in coordinated animation
 */
fun AnimationTiming.getDelay(index: Int, elementId: String): Long {
  return when (this) {
    is AnimationTiming.Parallel -> 0L
    is AnimationTiming.Sequential -> index * gap
    is AnimationTiming.Staggered -> (index * staggerDelay).coerceAtMost(maxDelay)
    is AnimationTiming.Custom -> delays[elementId] ?: 0L
  }
}

// ============================================================================
// NARRATIVE ANIMATIONS
// ============================================================================

/**
 * Create animations that tell a story
 * Example: Profile creation journey, compatibility analysis revelation
 */
object NarrativeAnimations {
  /**
   * Profile Creation Journey
   * User creates profile -> Calculations happen -> Results reveal
   */
  fun profileCreationJourney(): ChoreographySequence {
    return ChoreographySequence(
      name = "Profile Creation",
      steps = listOf(
        AnimationStep("Input Form", 0, 500, 1f, 1f, 0f, 0f, SpiritualEasing.Gentle),
        AnimationStep("Processing", 600, 1500, 1f, 1f, 0f, 360f, SpiritualEasing.Float),
        AnimationStep("Results Appear", 2200, 800, 1f, 1.05f, 0f, 0f, SpiritualEasing.Overshoot),
        AnimationStep("Celebrate", 3100, 600, 1f, 1f, 0f, 0f, SpiritualEasing.Gentle)
      )
    )
  }

  /**
   * Spiritual Awakening Moment
   * Dramatic reveal for profound insights
   */
  fun spiritualAwakening(): ChoreographySequence {
    return ChoreographySequence(
      name = "Awakening",
      steps = listOf(
        AnimationStep("Darkness", 0, 0, 0f, 0.5f, 0f, 0f, SpiritualEasing.Gentle),
        AnimationStep("First Light", 1000, 1500, 0.3f, 0.8f, 0f, 0f, SpiritualEasing.Breath),
        AnimationStep("Illumination", 2600, 1000, 1f, 1.2f, 0f, 0f, SpiritualEasing.Overshoot),
        AnimationStep("Enlightenment", 3700, 1500, 1f, 1f, 0f, 0f, SpiritualEasing.Float)
      )
    )
  }

  /**
   * Cosmic Connection
   * Two souls finding alignment
   */
  fun cosmicConnection(): ChoreographySequence {
    return ChoreographySequence(
      name = "Cosmic Connection",
      steps = listOf(
        AnimationStep("Soul 1 Awakens", 0, 600, 1f, 1f, 0f, 0f, SpiritualEasing.Overshoot),
        AnimationStep("Soul 2 Awakens", 400, 600, 1f, 1f, 0f, 0f, SpiritualEasing.Overshoot),
        AnimationStep("Energy Stirs", 1100, 800, 1f, 1.05f, 0f, 0f, SpiritualEasing.Pulse),
        AnimationStep("Connection Forms", 2000, 1500, 1f, 1f, 0f, 0f, SpiritualEasing.Float),
        AnimationStep("Unity Achieved", 3600, 1000, 1f, 1.1f, 0f, 0f, SpiritualEasing.Overshoot)
      )
    )
  }
}
