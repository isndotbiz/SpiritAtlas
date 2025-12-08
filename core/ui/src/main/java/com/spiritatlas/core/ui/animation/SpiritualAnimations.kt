package com.spiritatlas.core.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Duration presets for spiritual animations
 */
object AnimationDurations {
  const val Quick = 200
  const val Medium = 400
  const val Slow = 600
  const val ExtraSlow = 1000
  const val Breath = 4000
  const val Pulse = 2000
  const val Float = 3000
  const val Wave = 2500
}

/**
 * Easing functions for spiritual animations
 */
object SpiritualEasing {
  val Gentle = FastOutSlowInEasing
  val Breath = CubicBezierEasing(0.4f, 0.0f, 0.6f, 1.0f)
  val Float = CubicBezierEasing(0.25f, 0.46f, 0.45f, 0.94f)
  val Pulse = CubicBezierEasing(0.45f, 0.05f, 0.55f, 0.95f)
  val Wave = CubicBezierEasing(0.36f, 0.0f, 0.66f, -0.56f)
}

// ============================================================================
// FADE ANIMATIONS
// ============================================================================

/**
 * Quick fade-in animation (200ms)
 */
fun Modifier.fadeInQuick(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = AnimationDurations.Quick,
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "fadeInQuick"
  )
  this.alpha(alpha)
}

/**
 * Medium fade-in animation (400ms)
 */
fun Modifier.fadeInMedium(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "fadeInMedium"
  )
  this.alpha(alpha)
}

/**
 * Slow fade-in animation (600ms) - for mystical reveals
 */
fun Modifier.fadeInSlow(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = AnimationDurations.Slow,
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "fadeInSlow"
  )
  this.alpha(alpha)
}

// ============================================================================
// SCALE ANIMATIONS
// ============================================================================

/**
 * Scale animation for buttons with press feedback
 */
fun Modifier.scaleButton(
  pressed: Boolean = false,
  enabled: Boolean = true
): Modifier = composed {
  val scale by animateFloatAsState(
    targetValue = when {
      !enabled -> 0.95f
      pressed -> 0.92f
      else -> 1f
    },
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    ),
    label = "scaleButton"
  )
  this.scale(scale)
}

/**
 * Scale animation for cards with hover/focus states
 */
fun Modifier.scaleCard(
  hovered: Boolean = false,
  focused: Boolean = false
): Modifier = composed {
  val scale by animateFloatAsState(
    targetValue = when {
      focused -> 1.05f
      hovered -> 1.02f
      else -> 1f
    },
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioLowBouncy,
      stiffness = Spring.StiffnessMediumLow
    ),
    label = "scaleCard"
  )
  this.scale(scale)
}

/**
 * Pop-in scale animation for appearing elements
 */
fun Modifier.scalePopIn(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val scale by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium,
      visibilityThreshold = 0.01f
    ),
    label = "scalePopIn"
  )
  this.scale(scale)
}

// ============================================================================
// SLIDE ANIMATIONS
// ============================================================================

/**
 * Slide from bottom animation
 */
fun slideFromBottom(): EnterTransition {
  return slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    )
  ) + fadeIn(
    animationSpec = tween(AnimationDurations.Medium)
  )
}

/**
 * Slide to bottom animation
 */
fun slideToBottom(): ExitTransition {
  return slideOutVertically(
    targetOffsetY = { it },
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    )
  ) + fadeOut(
    animationSpec = tween(AnimationDurations.Medium)
  )
}

/**
 * Slide from left animation
 */
fun slideFromLeft(): EnterTransition {
  return slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    )
  ) + fadeIn(
    animationSpec = tween(AnimationDurations.Medium)
  )
}

/**
 * Slide from right animation
 */
fun slideFromRight(): EnterTransition {
  return slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    )
  ) + fadeIn(
    animationSpec = tween(AnimationDurations.Medium)
  )
}

// ============================================================================
// PULSING & BREATHING ANIMATIONS
// ============================================================================

/**
 * Breathing animation for spiritual elements (chakras, energy indicators)
 */
@Composable
fun rememberBreathingAnimation(
  minScale: Float = 0.95f,
  maxScale: Float = 1.05f,
  durationMillis: Int = AnimationDurations.Breath
): Float {
  val infiniteTransition = rememberInfiniteTransition(label = "breathing")
  val scale by infiniteTransition.animateFloat(
    initialValue = minScale,
    targetValue = maxScale,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMillis,
        easing = SpiritualEasing.Breath
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "breathScale"
  )
  return scale
}

/**
 * Breathing modifier for spiritual elements
 */
fun Modifier.breathingAnimation(
  minScale: Float = 0.95f,
  maxScale: Float = 1.05f,
  durationMillis: Int = AnimationDurations.Breath
): Modifier = composed {
  val scale = rememberBreathingAnimation(minScale, maxScale, durationMillis)
  this.scale(scale)
}

/**
 * Pulsing animation for active/energy states
 */
@Composable
fun rememberPulseAnimation(
  minAlpha: Float = 0.6f,
  maxAlpha: Float = 1.0f,
  durationMillis: Int = AnimationDurations.Pulse
): Float {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val alpha by infiniteTransition.animateFloat(
    initialValue = minAlpha,
    targetValue = maxAlpha,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMillis,
        easing = SpiritualEasing.Pulse
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )
  return alpha
}

/**
 * Pulsing modifier for active elements
 */
fun Modifier.pulsingAnimation(
  minAlpha: Float = 0.6f,
  maxAlpha: Float = 1.0f,
  durationMillis: Int = AnimationDurations.Pulse
): Modifier = composed {
  val alpha = rememberPulseAnimation(minAlpha, maxAlpha, durationMillis)
  this.alpha(alpha)
}

/**
 * Combined breathing and pulsing for chakra indicators
 */
fun Modifier.chakraAnimation(): Modifier = composed {
  val scale = rememberBreathingAnimation(0.92f, 1.08f, 3000)
  val alpha = rememberPulseAnimation(0.7f, 1.0f, 2000)
  this
    .scale(scale)
    .alpha(alpha)
}

// ============================================================================
// SHIMMER EFFECT
// ============================================================================

/**
 * Shimmer animation state
 */
@Composable
fun rememberShimmerAnimation(): Float {
  val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
  val offset by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1500,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "shimmerOffset"
  )
  return offset
}

/**
 * Shimmer effect for loading states
 */
@Composable
fun ShimmerEffect(
  modifier: Modifier = Modifier,
  baseColor: Color = Color.Gray.copy(alpha = 0.3f),
  highlightColor: Color = Color.White.copy(alpha = 0.5f)
) {
  val offset = rememberShimmerAnimation()

  Box(
    modifier = modifier
      .background(
        brush = Brush.horizontalGradient(
          colors = listOf(
            baseColor,
            highlightColor,
            baseColor
          ),
          startX = -1000f + offset * 3000f,
          endX = offset * 3000f
        )
      )
  )
}

/**
 * Shimmer modifier for loading states
 */
fun Modifier.shimmerEffect(
  baseColor: Color = Color.Gray.copy(alpha = 0.3f),
  highlightColor: Color = Color.White.copy(alpha = 0.5f)
): Modifier = composed {
  val offset = rememberShimmerAnimation()

  this.background(
    brush = Brush.horizontalGradient(
      colors = listOf(
        baseColor,
        highlightColor,
        baseColor
      ),
      startX = -1000f + offset * 3000f,
      endX = offset * 3000f
    )
  )
}

// ============================================================================
// FLOATING & LEVITATION ANIMATIONS
// ============================================================================

/**
 * Floating animation for mystical elements
 */
@Composable
fun rememberFloatingAnimation(
  range: Float = 10f,
  durationMillis: Int = AnimationDurations.Float
): Float {
  val infiniteTransition = rememberInfiniteTransition(label = "floating")
  val offset by infiniteTransition.animateFloat(
    initialValue = -range,
    targetValue = range,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMillis,
        easing = SpiritualEasing.Float
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "floatOffset"
  )
  return offset
}

/**
 * Floating modifier for mystical elements
 */
fun Modifier.floatingAnimation(
  range: Dp = 10.dp,
  durationMillis: Int = AnimationDurations.Float
): Modifier = composed {
  val offset = rememberFloatingAnimation(range.value, durationMillis)
  this.offset(y = offset.dp)
}

/**
 * Levitation animation with rotation for advanced mystical effects
 */
@Composable
fun rememberLevitationAnimation(): Pair<Float, Float> {
  val infiniteTransition = rememberInfiniteTransition(label = "levitation")

  val yOffset by infiniteTransition.animateFloat(
    initialValue = -15f,
    targetValue = 15f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 4000,
        easing = SpiritualEasing.Float
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "levitationY"
  )

  val rotation by infiniteTransition.animateFloat(
    initialValue = -5f,
    targetValue = 5f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 3000,
        easing = SpiritualEasing.Float
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "levitationRotation"
  )

  return Pair(yOffset, rotation)
}

/**
 * Levitation modifier with rotation
 */
fun Modifier.levitationAnimation(): Modifier = composed {
  val (yOffset, rotation) = rememberLevitationAnimation()
  this
    .offset(y = yOffset.dp)
    .rotate(rotation)
}

// ============================================================================
// ROTATION ANIMATIONS
// ============================================================================

/**
 * Continuous rotation for zodiac wheels
 */
@Composable
fun rememberContinuousRotation(
  durationMillis: Int = 20000,
  clockwise: Boolean = true
): Float {
  val infiniteTransition = rememberInfiniteTransition(label = "rotation")
  val angle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = if (clockwise) 360f else -360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotationAngle"
  )
  return angle
}

/**
 * Continuous rotation modifier
 */
fun Modifier.continuousRotation(
  durationMillis: Int = 20000,
  clockwise: Boolean = true
): Modifier = composed {
  val angle = rememberContinuousRotation(durationMillis, clockwise)
  this.rotate(angle)
}

/**
 * Slow mystical rotation for zodiac wheels
 */
fun Modifier.zodiacRotation(): Modifier = composed {
  val angle = rememberContinuousRotation(30000, clockwise = true)
  this.rotate(angle)
}

/**
 * Counter-clockwise rotation for cosmic elements
 */
fun Modifier.cosmicRotation(): Modifier = composed {
  val angle = rememberContinuousRotation(25000, clockwise = false)
  this.rotate(angle)
}

// ============================================================================
// SPARKLE EFFECTS
// ============================================================================

/**
 * Sparkle particle data class
 */
private data class SparkleParticle(
  val x: Float,
  val y: Float,
  val size: Float,
  val alpha: Float,
  val color: Color,
  val speed: Float,
  val phase: Float
)

/**
 * Sparkle effect using Canvas for particle-like animations
 */
@Composable
fun SparkleEffect(
  modifier: Modifier = Modifier,
  particleCount: Int = 20,
  colors: List<Color> = listOf(
    AuraGold,
    SpiritualPurple,
    CosmicBlue,
    Color.White
  )
) {
  val infiniteTransition = rememberInfiniteTransition(label = "sparkle")
  val time by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2 * PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 3000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "sparkleTime"
  )

  val particles = remember {
    List(particleCount) {
      SparkleParticle(
        x = Random.nextFloat(),
        y = Random.nextFloat(),
        size = Random.nextFloat() * 4f + 2f,
        alpha = Random.nextFloat() * 0.5f + 0.5f,
        color = colors[Random.nextInt(colors.size)],
        speed = Random.nextFloat() * 0.5f + 0.5f,
        phase = Random.nextFloat() * 2 * PI.toFloat()
      )
    }
  }

  Canvas(modifier = modifier) {
    particles.forEach { particle ->
      val alpha = particle.alpha * (0.5f + 0.5f * sin(time * particle.speed + particle.phase))
      val scaledSize = particle.size * (0.8f + 0.4f * cos(time * particle.speed * 1.3f + particle.phase))

      drawCircle(
        color = particle.color.copy(alpha = alpha),
        radius = scaledSize,
        center = Offset(
          x = size.width * particle.x,
          y = size.height * particle.y
        )
      )
    }
  }
}

/**
 * Star sparkle effect for mystical moments
 */
@Composable
fun StarSparkleEffect(
  modifier: Modifier = Modifier,
  starCount: Int = 15,
  color: Color = AuraGold
) {
  val infiniteTransition = rememberInfiniteTransition(label = "starSparkle")
  val time by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2 * PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "starTime"
  )

  val stars = remember {
    List(starCount) {
      Triple(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat() * 2 * PI.toFloat()
      )
    }
  }

  Canvas(modifier = modifier) {
    stars.forEach { (x, y, phase) ->
      val alpha = 0.3f + 0.7f * sin(time + phase)
      val scale = 0.6f + 0.4f * cos(time * 1.5f + phase)

      drawStar(
        center = Offset(size.width * x, size.height * y),
        radius = 8f * scale,
        color = color.copy(alpha = alpha)
      )
    }
  }
}

/**
 * Helper function to draw a star shape
 */
private fun DrawScope.drawStar(
  center: Offset,
  radius: Float,
  color: Color,
  points: Int = 5
) {
  val path = Path()
  val angleStep = PI.toFloat() / points

  for (i in 0 until points * 2) {
    val angle = i * angleStep - PI.toFloat() / 2
    val r = if (i % 2 == 0) radius else radius * 0.4f
    val x = center.x + r * cos(angle)
    val y = center.y + r * sin(angle)

    if (i == 0) {
      path.moveTo(x, y)
    } else {
      path.lineTo(x, y)
    }
  }
  path.close()

  drawPath(path = path, color = color)
}

// ============================================================================
// WAVE ANIMATIONS
// ============================================================================

/**
 * Wave animation for energy flow visualization
 */
@Composable
fun WaveAnimation(
  modifier: Modifier = Modifier,
  waveColor: Color = SpiritualPurple,
  waveCount: Int = 3,
  amplitude: Float = 30f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "wave")
  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2 * PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = AnimationDurations.Wave,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "wavePhase"
  )

  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height
    val centerY = height / 2

    for (i in 0 until waveCount) {
      val wavePhase = phase + (i * 2 * PI.toFloat() / waveCount)
      val alpha = 1f - (i * 0.2f)

      val path = Path()
      path.moveTo(0f, centerY)

      for (x in 0..width.toInt() step 5) {
        val normalizedX = x / width
        val y = centerY + amplitude * sin(normalizedX * 4 * PI.toFloat() + wavePhase)
        path.lineTo(x.toFloat(), y.toFloat())
      }

      drawPath(
        path = path,
        color = waveColor.copy(alpha = alpha),
        style = Stroke(width = 3f, cap = StrokeCap.Round)
      )
    }
  }
}

/**
 * Energy flow wave animation with gradient
 */
@Composable
fun EnergyFlowWave(
  modifier: Modifier = Modifier,
  colors: List<Color> = ChakraGradient,
  waveCount: Int = 7
) {
  val infiniteTransition = rememberInfiniteTransition(label = "energyFlow")
  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 2 * PI.toFloat(),
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 3000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "energyPhase"
  )

  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height
    val spacing = height / (waveCount + 1)

    colors.forEachIndexed { index, color ->
      if (index >= waveCount) return@forEachIndexed

      val y = spacing * (index + 1)
      val wavePhase = phase + (index * PI.toFloat() / waveCount)

      val path = Path()
      path.moveTo(0f, y)

      for (x in 0..width.toInt() step 3) {
        val normalizedX = x / width
        val offset = 15f * sin(normalizedX * 6 * PI.toFloat() + wavePhase)
        path.lineTo(x.toFloat(), y + offset)
      }

      drawPath(
        path = path,
        color = color,
        style = Stroke(width = 4f, cap = StrokeCap.Round)
      )
    }
  }
}

/**
 * Circular wave animation for chakra activation
 */
@Composable
fun CircularWave(
  modifier: Modifier = Modifier,
  color: Color = ChakraCrown,
  maxRadius: Float = 200f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "circularWave")
  val radius by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = maxRadius,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "waveRadius"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2, size.height / 2)
    val alpha = 1f - (radius / maxRadius)

    drawCircle(
      color = color.copy(alpha = alpha * 0.6f),
      radius = radius,
      center = center,
      style = Stroke(width = 3f)
    )

    if (radius > maxRadius * 0.3f) {
      drawCircle(
        color = color.copy(alpha = alpha * 0.4f),
        radius = radius - maxRadius * 0.3f,
        center = center,
        style = Stroke(width = 2f)
      )
    }
  }
}

// ============================================================================
// MORPH ANIMATIONS
// ============================================================================

/**
 * Morph between two colors with animation
 */
@Composable
fun rememberColorMorph(
  targetColor: Color,
  durationMillis: Int = AnimationDurations.Medium
): Color {
  val color by animateColorAsState(
    targetValue = targetColor,
    animationSpec = tween(
      durationMillis = durationMillis,
      easing = SpiritualEasing.Gentle
    ),
    label = "colorMorph"
  )
  return color
}

/**
 * Morph between shapes animation state
 */
@Composable
fun rememberShapeMorph(
  targetProgress: Float,
  durationMillis: Int = AnimationDurations.Slow
): Float {
  val progress by animateFloatAsState(
    targetValue = targetProgress,
    animationSpec = tween(
      durationMillis = durationMillis,
      easing = SpiritualEasing.Gentle
    ),
    label = "shapeMorph"
  )
  return progress
}

/**
 * Circle to triangle morph animation
 */
@Composable
fun CircleToTriangleMorph(
  modifier: Modifier = Modifier,
  progress: Float = 0f,
  color: Color = SpiritualPurple
) {
  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = minOf(size.width, size.height) / 3

    val path = Path()

    if (progress == 0f) {
      // Draw circle
      drawCircle(
        color = color,
        radius = radius,
        center = center
      )
    } else if (progress == 1f) {
      // Draw triangle
      val height = radius * 1.5f
      path.moveTo(center.x, center.y - height)
      path.lineTo(center.x - radius, center.y + height / 2)
      path.lineTo(center.x + radius, center.y + height / 2)
      path.close()
      drawPath(path = path, color = color)
    } else {
      // Morph between circle and triangle
      val points = 120
      val angleStep = 2 * PI.toFloat() / points

      for (i in 0 until points) {
        val angle = i * angleStep

        // Circle point
        val circleX = center.x + radius * cos(angle)
        val circleY = center.y + radius * sin(angle)

        // Triangle point (approximate)
        val triangleAngle = ((angle / (2 * PI.toFloat() / 3)).toInt() * (2 * PI.toFloat() / 3))
        val nextTriangleAngle = triangleAngle + (2 * PI.toFloat() / 3)
        val t = (angle - triangleAngle) / (2 * PI.toFloat() / 3)

        val height = radius * 1.5f
        val triangleX = center.x + radius * cos(triangleAngle) * (1 - t) +
                       radius * cos(nextTriangleAngle) * t
        val triangleY = center.y + radius * sin(triangleAngle) * (1 - t) +
                       radius * sin(nextTriangleAngle) * t

        // Interpolate between circle and triangle
        val x = circleX * (1 - progress) + triangleX * progress
        val y = circleY * (1 - progress) + triangleY * progress

        if (i == 0) {
          path.moveTo(x, y)
        } else {
          path.lineTo(x, y)
        }
      }
      path.close()
      drawPath(path = path, color = color)
    }
  }
}

/**
 * Gradient morph animation for chakra transitions
 */
@Composable
fun rememberGradientMorph(
  targetGradient: List<Color>,
  currentGradient: List<Color>,
  durationMillis: Int = AnimationDurations.Slow
): List<Color> {
  require(targetGradient.size == currentGradient.size) {
    "Gradient lists must have the same size"
  }

  val colors = targetGradient.indices.map { index ->
    animateColorAsState(
      targetValue = targetGradient[index],
      animationSpec = tween(
        durationMillis = durationMillis,
        easing = SpiritualEasing.Gentle
      ),
      label = "gradientMorph$index"
    )
  }

  return colors.map { it.value }
}

// ============================================================================
// COMBINED SPIRITUAL ANIMATIONS
// ============================================================================

/**
 * Complete spiritual entrance animation
 */
fun Modifier.spiritualEntrance(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val scale = rememberBreathingAnimation(0.0f, 1.0f, 600)
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = AnimationDurations.Slow,
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "spiritualEntranceAlpha"
  )

  this
    .scale(if (visible) scale else 0f)
    .alpha(alpha)
}

/**
 * Meditation focus animation
 */
fun Modifier.meditationFocus(): Modifier = composed {
  val scale = rememberBreathingAnimation(0.98f, 1.02f, 4000)
  val alpha = rememberPulseAnimation(0.85f, 1.0f, 3000)

  this
    .scale(scale)
    .alpha(alpha)
}

/**
 * Sacred geometry rotation with breathing
 */
fun Modifier.sacredGeometry(): Modifier = composed {
  val rotation = rememberContinuousRotation(40000, true)
  val scale = rememberBreathingAnimation(0.95f, 1.05f, 5000)

  this
    .rotate(rotation)
    .scale(scale)
}

// ============================================================================
// UTILITY EXTENSIONS
// ============================================================================

/**
 * Delayed animation spec helper
 */
private fun <T> AnimationSpec<T>.delayed(delayMillis: Int): AnimationSpec<T> {
  return if (delayMillis > 0 && this is TweenSpec<T>) {
    tween(
      durationMillis = this.durationMillis,
      delayMillis = delayMillis,
      easing = this.easing
    )
  } else {
    this
  }
}

/**
 * Staggered fade-in for list items
 */
fun Modifier.staggeredFadeIn(
  index: Int,
  baseDelay: Int = 50
): Modifier = this.fadeInMedium(visible = true, delay = index * baseDelay)

/**
 * Combined fade and scale entrance
 */
fun fadeAndScaleIn(): EnterTransition {
  return fadeIn(
    animationSpec = tween(AnimationDurations.Medium)
  ) + scaleIn(
    initialScale = 0.8f,
    animationSpec = spring(
      dampingRatio = Spring.DampingRatioMediumBouncy,
      stiffness = Spring.StiffnessMedium
    )
  )
}

/**
 * Combined fade and scale exit
 */
fun fadeAndScaleOut(): ExitTransition {
  return fadeOut(
    animationSpec = tween(AnimationDurations.Medium)
  ) + scaleOut(
    targetScale = 0.8f,
    animationSpec = tween(AnimationDurations.Medium)
  )
}
