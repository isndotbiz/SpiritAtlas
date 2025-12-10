package com.spiritatlas.core.ui.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import com.spiritatlas.core.ui.theme.ChakraBlue
import com.spiritatlas.core.ui.theme.ChakraCrown
import com.spiritatlas.core.ui.theme.ChakraGreen
import com.spiritatlas.core.ui.theme.ChakraIndigo
import com.spiritatlas.core.ui.theme.ChakraOrange
import com.spiritatlas.core.ui.theme.ChakraRed
import com.spiritatlas.core.ui.theme.ChakraYellow
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Particle data class for chakra animations
 */
private data class ChakraParticle(
  val angle: Float,
  val distance: Float,
  val speed: Float,
  val size: Float,
  val alpha: Float,
  val offset: Float
)

/**
 * Get chakra color by index (0-6)
 */
fun getChakraColor(index: Int): Color {
  return when (index) {
    0 -> ChakraRed
    1 -> ChakraOrange
    2 -> ChakraYellow
    3 -> ChakraGreen
    4 -> ChakraBlue
    5 -> ChakraIndigo
    6 -> ChakraCrown
    else -> ChakraCrown
  }
}

/**
 * Enhanced spinning chakra animation with particles and pulsing glow
 *
 * Features:
 * - Smooth 360° rotation with easing
 * - Particle emission from center
 * - Pulsing glow effect
 * - Color transitions matching chakra energy
 *
 * @param modifier Modifier for the canvas
 * @param chakraIndex Chakra index (0=Root, 6=Crown)
 * @param particleCount Number of particles to emit
 * @param rotationDurationMs Duration of one full rotation
 */
@Composable
fun SpinningChakraWithParticles(
  modifier: Modifier = Modifier,
  chakraIndex: Int = 6,
  particleCount: Int = 24,
  rotationDurationMs: Int = 8000
) {
  val infiniteTransition = rememberInfiniteTransition(label = "chakraSpinning")

  // Smooth rotation animation
  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = rotationDurationMs,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "chakraRotation"
  )

  // Pulsing glow animation
  val glowPulse by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "chakraGlow"
  )

  // Breathing scale animation
  val breathScale by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 3000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "chakraBreath"
  )

  val chakraColor = getChakraColor(chakraIndex)

  // Generate particle system
  val particles = remember(particleCount) {
    List(particleCount) {
      ChakraParticle(
        angle = (it * 360f / particleCount) + Random.nextFloat() * 10f,
        distance = Random.nextFloat() * 80f + 40f,
        speed = Random.nextFloat() * 0.5f + 0.5f,
        size = Random.nextFloat() * 3f + 2f,
        alpha = Random.nextFloat() * 0.6f + 0.4f,
        offset = Random.nextFloat() * PI.toFloat() * 2
      )
    }
  }

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val baseRadius = minOf(size.width, size.height) / 4f * breathScale

    // Draw outer glow rings
    for (i in 3 downTo 1) {
      drawCircle(
        color = chakraColor.copy(alpha = glowPulse * 0.15f * i / 3f),
        radius = baseRadius * (1f + 0.2f * i),
        center = center
      )
    }

    // Draw rotating inner petals (sacred geometry)
    rotate(rotation, center) {
      drawChakraPetals(
        center = center,
        radius = baseRadius * 0.85f,
        color = chakraColor,
        petalCount = when(chakraIndex) {
          0 -> 4   // Root - 4 petals
          1 -> 6   // Sacral - 6 petals
          2 -> 10  // Solar Plexus - 10 petals
          3 -> 12  // Heart - 12 petals
          4 -> 16  // Throat - 16 petals
          5 -> 2   // Third Eye - 2 petals
          6 -> 1000 // Crown - 1000 petals (represented as a circle)
          else -> 8
        }
      )
    }

    // Draw center core with gradient
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color.White.copy(alpha = 0.8f),
          chakraColor.copy(alpha = 0.9f),
          chakraColor.copy(alpha = 0.7f)
        ),
        center = center,
        radius = baseRadius * 0.4f
      ),
      radius = baseRadius * 0.4f,
      center = center
    )

    // Draw particles emanating from center
    particles.forEach { particle ->
      val particleAngle = (rotation + particle.angle + particle.offset * 180f / PI.toFloat()) * PI.toFloat() / 180f
      val particleDistance = particle.distance * breathScale

      val particleX = center.x + particleDistance * cos(particleAngle)
      val particleY = center.y + particleDistance * sin(particleAngle)

      // Particle with glow
      drawCircle(
        color = chakraColor.copy(alpha = particle.alpha * glowPulse * 0.3f),
        radius = particle.size * 2f,
        center = Offset(particleX, particleY)
      )

      drawCircle(
        color = chakraColor.copy(alpha = particle.alpha * glowPulse),
        radius = particle.size,
        center = Offset(particleX, particleY)
      )
    }

    // Draw rotating sacred symbol in center
    rotate(rotation * 0.5f, center) {
      drawPath(
        path = createYantraSymbol(center, baseRadius * 0.25f),
        color = Color.White.copy(alpha = 0.6f),
        style = Stroke(width = 2f, cap = StrokeCap.Round)
      )
    }
  }
}

/**
 * Draw chakra petals in a circular pattern
 */
private fun DrawScope.drawChakraPetals(
  center: Offset,
  radius: Float,
  color: Color,
  petalCount: Int
) {
  if (petalCount >= 100) {
    // For crown chakra (1000 petals), draw as concentric circles
    drawCircle(
      color = color.copy(alpha = 0.3f),
      radius = radius,
      center = center,
      style = Stroke(width = 2f)
    )
    drawCircle(
      color = color.copy(alpha = 0.2f),
      radius = radius * 1.1f,
      center = center,
      style = Stroke(width = 1.5f)
    )
    return
  }

  val angleStep = 360f / petalCount

  for (i in 0 until petalCount) {
    val angle = (i * angleStep) * PI.toFloat() / 180f
    val petalX = center.x + radius * cos(angle)
    val petalY = center.y + radius * sin(angle)

    // Draw petal shape
    val path = Path().apply {
      moveTo(center.x, center.y)

      val controlX1 = center.x + (radius * 0.6f) * cos(angle - 0.3f)
      val controlY1 = center.y + (radius * 0.6f) * sin(angle - 0.3f)
      val controlX2 = center.x + (radius * 0.6f) * cos(angle + 0.3f)
      val controlY2 = center.y + (radius * 0.6f) * sin(angle + 0.3f)

      quadraticBezierTo(controlX1, controlY1, petalX, petalY)
      quadraticBezierTo(controlX2, controlY2, center.x, center.y)
      close()
    }

    drawPath(
      path = path,
      color = color.copy(alpha = 0.3f)
    )

    drawPath(
      path = path,
      color = color.copy(alpha = 0.5f),
      style = Stroke(width = 1f)
    )
  }
}

/**
 * Create a simple yantra symbol path
 */
private fun createYantraSymbol(center: Offset, radius: Float): Path {
  return Path().apply {
    // Draw triangle (pointing up for upper chakras, down for lower chakras)
    val height = radius * 1.732f / 2f // equilateral triangle height

    moveTo(center.x, center.y - height)
    lineTo(center.x - radius, center.y + height / 2f)
    lineTo(center.x + radius, center.y + height / 2f)
    close()
  }
}

/**
 * Simple chakra spinning indicator
 * Useful for smaller UI elements
 */
@Composable
fun ChakraSpinner(
  modifier: Modifier = Modifier,
  chakraIndex: Int = 6,
  size: Float = 40f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "chakraSpinner")

  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "spinnerRotation"
  )

  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "spinnerPulse"
  )

  val chakraColor = getChakraColor(chakraIndex)

  Canvas(modifier = modifier) {
    val center = Offset(this.size.width / 2f, this.size.height / 2f)

    // Outer ring
    drawCircle(
      color = chakraColor.copy(alpha = 0.3f * pulse),
      radius = size * pulse,
      center = center,
      style = Stroke(width = 3f)
    )

    // Rotating inner symbol
    rotate(rotation, center) {
      val radius = size * 0.6f
      val points = 8
      val angleStep = 360f / points

      for (i in 0 until points) {
        val angle = (i * angleStep) * PI.toFloat() / 180f
        val x = center.x + radius * cos(angle)
        val y = center.y + radius * sin(angle)

        drawCircle(
          color = chakraColor.copy(alpha = if (i % 2 == 0) 0.8f else 0.4f),
          radius = 3f,
          center = Offset(x, y)
        )
      }
    }

    // Center dot
    drawCircle(
      color = Color.White,
      radius = 4f,
      center = center
    )
  }
}

/**
 * Chakra activation animation
 * Shows a chakra "lighting up" with a burst effect
 */
@Composable
fun ChakraActivation(
  modifier: Modifier = Modifier,
  chakraIndex: Int = 0,
  isActive: Boolean = false
) {
  val animatable = remember { Animatable(0f) }

  LaunchedEffect(isActive) {
    if (isActive) {
      animatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(
          durationMillis = 1000,
          easing = FastOutSlowInEasing
        )
      )
    } else {
      animatable.snapTo(0f)
    }
  }

  val chakraColor = getChakraColor(chakraIndex)

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val maxRadius = minOf(size.width, size.height) / 2f
    val progress = animatable.value

    if (progress > 0f) {
      // Expanding wave
      val waveRadius = maxRadius * progress
      val waveAlpha = 1f - progress

      drawCircle(
        color = chakraColor.copy(alpha = waveAlpha * 0.5f),
        radius = waveRadius,
        center = center,
        style = Stroke(width = 3f)
      )

      // Inner pulse
      drawCircle(
        color = chakraColor.copy(alpha = (1f - progress * 0.5f) * 0.8f),
        radius = maxRadius * 0.5f * (0.5f + progress * 0.5f),
        center = center
      )

      // Bright center
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color.White.copy(alpha = 0.9f * (1f - progress * 0.3f)),
            chakraColor.copy(alpha = 0.8f * (1f - progress * 0.3f))
          ),
          center = center,
          radius = maxRadius * 0.3f
        ),
        radius = maxRadius * 0.3f,
        center = center
      )
    }
  }
}

/**
 * All seven chakras spinning in alignment
 * Perfect for meditation or energy work visualizations
 */
@Composable
fun ChakraAlignment(
  modifier: Modifier = Modifier,
  spacing: Float = 80f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "chakraAlignment")

  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 10000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "alignmentRotation"
  )

  Canvas(modifier = modifier) {
    val centerX = size.width / 2f
    val startY = spacing

    for (i in 0 until 7) {
      val chakraColor = getChakraColor(i)
      val y = startY + i * spacing
      val center = Offset(centerX, y)
      val radius = 30f

      // Glow
      drawCircle(
        color = chakraColor.copy(alpha = 0.2f),
        radius = radius * 1.5f,
        center = center
      )

      // Main chakra
      rotate(rotation * (if (i % 2 == 0) 1f else -1f), center) {
        drawCircle(
          color = chakraColor.copy(alpha = 0.8f),
          radius = radius,
          center = center
        )

        // Simple mandala pattern
        val points = 6
        val angleStep = 360f / points
        for (j in 0 until points) {
          val angle = (j * angleStep) * PI.toFloat() / 180f
          val x = center.x + radius * 0.6f * cos(angle)
          val y2 = center.y + radius * 0.6f * sin(angle)

          drawCircle(
            color = Color.White.copy(alpha = 0.6f),
            radius = 3f,
            center = Offset(x, y2)
          )
        }
      }

      // Center dot
      drawCircle(
        color = Color.White,
        radius = 5f,
        center = center
      )
    }
  }
}
