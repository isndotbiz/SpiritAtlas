package com.spiritatlas.core.ui.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.ChakraBlue
import com.spiritatlas.core.ui.theme.ChakraGreen
import com.spiritatlas.core.ui.theme.ChakraRed
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.core.ui.theme.TantricRose
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.random.Random

/**
 * Energy particle for flow animations
 */
private data class EnergyParticle(
  val startX: Float,
  val startY: Float,
  val speed: Float,
  val size: Float,
  val alpha: Float,
  val offset: Float
)

/**
 * Get compatibility color based on percentage
 */
private fun getCompatibilityColor(percentage: Float): Color {
  return when {
    percentage >= 0.9f -> AuraGold // Excellent
    percentage >= 0.75f -> ChakraGreen // Very Good
    percentage >= 0.6f -> ChakraBlue // Good
    percentage >= 0.45f -> SpiritualPurple // Moderate
    percentage >= 0.3f -> TantricRose // Fair
    else -> ChakraRed // Challenging
  }
}

/**
 * Energy flow animation between two profile cards
 * Flowing particles represent compatibility strength with color and density
 *
 * @param modifier Modifier for the canvas
 * @param compatibilityPercentage Compatibility strength (0.0 to 1.0)
 * @param isHorizontal True for horizontal flow, false for vertical
 * @param particleCount Number of particles in the flow
 * @param bidirectional True for two-way flow, false for one-way
 */
@Composable
fun EnergyFlowBetweenProfiles(
  modifier: Modifier = Modifier,
  compatibilityPercentage: Float = 0.75f,
  isHorizontal: Boolean = true,
  particleCount: Int = 30,
  bidirectional: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "energyFlow")

  val progress by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 3000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "flowProgress"
  )

  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1500,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  val flowColor = getCompatibilityColor(compatibilityPercentage)

  val particles = remember(particleCount) {
    List(particleCount) {
      EnergyParticle(
        startX = Random.nextFloat(),
        startY = Random.nextFloat(),
        speed = Random.nextFloat() * 0.5f + 0.5f,
        size = Random.nextFloat() * 3f + 2f,
        alpha = Random.nextFloat() * 0.5f + 0.5f,
        offset = Random.nextFloat()
      )
    }
  }

  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height

    // Draw flowing energy path
    if (isHorizontal) {
      // Horizontal flow (left to right)
      drawEnergyPath(
        start = Offset(0f, height / 2f),
        end = Offset(width, height / 2f),
        color = flowColor,
        progress = progress,
        pulse = pulse,
        compatibility = compatibilityPercentage
      )

      // Draw particles along the path
      particles.forEach { particle ->
        val particleProgress = (progress + particle.offset) % 1f
        val adjustedProgress = particleProgress * particle.speed

        // Forward particles (left to right)
        if (adjustedProgress < 1f) {
          val x = width * adjustedProgress
          val y = height / 2f + sin(adjustedProgress * PI.toFloat() * 4) * 20f * particle.startY

          drawEnergyParticle(
            center = Offset(x, y),
            color = flowColor,
            size = particle.size,
            alpha = particle.alpha * pulse
          )
        }

        // Backward particles (right to left) if bidirectional
        if (bidirectional && adjustedProgress < 0.7f) {
          val backProgress = 1f - adjustedProgress * 1.4f
          val x = width * backProgress
          val y = height / 2f - sin(backProgress * PI.toFloat() * 4) * 15f * particle.startY

          drawEnergyParticle(
            center = Offset(x, y),
            color = flowColor.copy(alpha = 0.6f),
            size = particle.size * 0.8f,
            alpha = particle.alpha * 0.7f * pulse
          )
        }
      }
    } else {
      // Vertical flow (top to bottom)
      drawEnergyPath(
        start = Offset(width / 2f, 0f),
        end = Offset(width / 2f, height),
        color = flowColor,
        progress = progress,
        pulse = pulse,
        compatibility = compatibilityPercentage
      )

      // Draw particles along the path
      particles.forEach { particle ->
        val particleProgress = (progress + particle.offset) % 1f
        val adjustedProgress = particleProgress * particle.speed

        // Forward particles (top to bottom)
        if (adjustedProgress < 1f) {
          val x = width / 2f + sin(adjustedProgress * PI.toFloat() * 4) * 20f * particle.startX
          val y = height * adjustedProgress

          drawEnergyParticle(
            center = Offset(x, y),
            color = flowColor,
            size = particle.size,
            alpha = particle.alpha * pulse
          )
        }

        // Backward particles (bottom to top) if bidirectional
        if (bidirectional && adjustedProgress < 0.7f) {
          val backProgress = 1f - adjustedProgress * 1.4f
          val x = width / 2f - sin(backProgress * PI.toFloat() * 4) * 15f * particle.startX
          val y = height * backProgress

          drawEnergyParticle(
            center = Offset(x, y),
            color = flowColor.copy(alpha = 0.6f),
            size = particle.size * 0.8f,
            alpha = particle.alpha * 0.7f * pulse
          )
        }
      }
    }
  }
}

/**
 * Draw the energy path with glow effect
 */
private fun DrawScope.drawEnergyPath(
  start: Offset,
  end: Offset,
  color: Color,
  progress: Float,
  pulse: Float,
  compatibility: Float
) {
  // Outer glow
  drawLine(
    color = color.copy(alpha = 0.2f * pulse),
    start = start,
    end = end,
    strokeWidth = 12f,
    cap = StrokeCap.Round
  )

  // Middle glow
  drawLine(
    color = color.copy(alpha = 0.4f * pulse),
    start = start,
    end = end,
    strokeWidth = 6f,
    cap = StrokeCap.Round
  )

  // Inner bright line
  drawLine(
    color = color.copy(alpha = 0.8f * pulse),
    start = start,
    end = end,
    strokeWidth = 2f,
    cap = StrokeCap.Round
  )

  // Animated dash effect for movement indication
  val dashPhase = progress * 30f
  drawLine(
    color = Color.White.copy(alpha = 0.6f * pulse),
    start = start,
    end = end,
    strokeWidth = 1f,
    cap = StrokeCap.Round,
    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), dashPhase)
  )
}

/**
 * Draw a single energy particle with glow
 */
private fun DrawScope.drawEnergyParticle(
  center: Offset,
  color: Color,
  size: Float,
  alpha: Float
) {
  // Outer glow
  drawCircle(
    color = color.copy(alpha = alpha * 0.3f),
    radius = size * 2.5f,
    center = center
  )

  // Middle glow
  drawCircle(
    color = color.copy(alpha = alpha * 0.6f),
    radius = size * 1.5f,
    center = center
  )

  // Bright core
  drawCircle(
    color = Color.White.copy(alpha = alpha * 0.8f),
    radius = size,
    center = center
  )
}

/**
 * Circular energy flow around a profile
 * Creates a rotating aura effect
 *
 * @param modifier Modifier for the canvas
 * @param color Energy color
 * @param particleCount Number of particles in orbit
 * @param radius Orbit radius multiplier
 */
@Composable
fun CircularEnergyFlow(
  modifier: Modifier = Modifier,
  color: Color = SpiritualPurple,
  particleCount: Int = 12,
  radius: Float = 0.4f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "circularFlow")

  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 4000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotation"
  )

  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1500,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val orbitRadius = minOf(size.width, size.height) * radius

    // Draw orbit path
    drawCircle(
      color = color.copy(alpha = 0.15f * pulse),
      radius = orbitRadius,
      center = center,
      style = Stroke(width = 2f)
    )

    // Draw particles
    val angleStep = 360f / particleCount
    for (i in 0 until particleCount) {
      val angle = (rotation + i * angleStep) * PI.toFloat() / 180f
      val x = center.x + orbitRadius * cos(angle)
      val y = center.y + orbitRadius * sin(angle)

      // Particle size varies to create depth effect
      val distanceFromFront = cos(angle)
      val particleSize = 4f + 3f * (distanceFromFront + 1f) / 2f
      val particleAlpha = 0.5f + 0.5f * (distanceFromFront + 1f) / 2f

      drawEnergyParticle(
        center = Offset(x, y),
        color = color,
        size = particleSize,
        alpha = particleAlpha * pulse
      )
    }
  }
}

/**
 * Heart connection animation
 * Two hearts connected by flowing energy (for romantic compatibility)
 *
 * @param modifier Modifier for the canvas
 * @param connectionStrength How strong the connection is (0.0 to 1.0)
 * @param color Energy color
 */
@Composable
fun HeartConnection(
  modifier: Modifier = Modifier,
  connectionStrength: Float = 0.8f,
  color: Color = TantricRose
) {
  val infiniteTransition = rememberInfiniteTransition(label = "heartConnection")

  val beat by infiniteTransition.animateFloat(
    initialValue = 0.95f,
    targetValue = 1.05f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "beat"
  )

  val flow by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "flow"
  )

  Canvas(modifier = modifier) {
    val centerY = size.height / 2f
    val leftHeartX = size.width * 0.25f
    val rightHeartX = size.width * 0.75f
    val heartSize = minOf(size.width, size.height) * 0.15f

    // Draw connection path
    val connectionPath = Path().apply {
      moveTo(leftHeartX, centerY)

      // Bezier curve for natural flow
      val controlX1 = leftHeartX + (rightHeartX - leftHeartX) * 0.3f
      val controlY1 = centerY - 30f
      val controlX2 = leftHeartX + (rightHeartX - leftHeartX) * 0.7f
      val controlY2 = centerY + 30f

      cubicTo(
        controlX1, controlY1,
        controlX2, controlY2,
        rightHeartX, centerY
      )
    }

    // Draw glow path
    drawPath(
      path = connectionPath,
      color = color.copy(alpha = 0.3f * connectionStrength),
      style = Stroke(width = 8f, cap = StrokeCap.Round)
    )

    drawPath(
      path = connectionPath,
      color = color.copy(alpha = 0.6f * connectionStrength),
      style = Stroke(width = 3f, cap = StrokeCap.Round)
    )

    // Draw flowing particles along the path
    for (i in 0 until 8) {
      val particleProgress = (flow + i * 0.125f) % 1f
      val t = particleProgress

      // Calculate Bezier curve position
      val controlX1 = leftHeartX + (rightHeartX - leftHeartX) * 0.3f
      val controlY1 = centerY - 30f
      val controlX2 = leftHeartX + (rightHeartX - leftHeartX) * 0.7f
      val controlY2 = centerY + 30f

      val x = (1 - t).pow(3) * leftHeartX +
              3 * (1 - t).pow(2) * t * controlX1 +
              3 * (1 - t) * t.pow(2) * controlX2 +
              t.pow(3) * rightHeartX

      val y = (1 - t).pow(3) * centerY +
              3 * (1 - t).pow(2) * t * controlY1 +
              3 * (1 - t) * t.pow(2) * controlY2 +
              t.pow(3) * centerY

      drawEnergyParticle(
        center = Offset(x, y),
        color = color,
        size = 3f,
        alpha = connectionStrength
      )
    }

    // Draw left heart
    drawHeart(
      center = Offset(leftHeartX, centerY),
      size = heartSize * beat,
      color = color
    )

    // Draw right heart
    drawHeart(
      center = Offset(rightHeartX, centerY),
      size = heartSize * beat,
      color = color
    )
  }
}

/**
 * Draw a heart shape
 */
private fun DrawScope.drawHeart(
  center: Offset,
  size: Float,
  color: Color
) {
  val path = Path().apply {
    val heartWidth = size
    val heartHeight = size * 0.9f

    moveTo(center.x, center.y + heartHeight * 0.3f)

    // Left curve
    cubicTo(
      center.x - heartWidth * 0.5f, center.y - heartHeight * 0.3f,
      center.x - heartWidth, center.y + heartHeight * 0.1f,
      center.x, center.y + heartHeight * 0.7f
    )

    // Right curve
    cubicTo(
      center.x + heartWidth, center.y + heartHeight * 0.1f,
      center.x + heartWidth * 0.5f, center.y - heartHeight * 0.3f,
      center.x, center.y + heartHeight * 0.3f
    )

    close()
  }

  // Glow
  drawPath(
    path = path,
    color = color.copy(alpha = 0.3f)
  )

  // Heart fill
  drawPath(
    path = path,
    color = color.copy(alpha = 0.8f)
  )

  // Heart outline
  drawPath(
    path = path,
    color = Color.White.copy(alpha = 0.6f),
    style = Stroke(width = 2f)
  )
}

/**
 * Compatibility meter with animated energy flow
 * Visual representation of compatibility percentage
 *
 * @param modifier Modifier for the canvas
 * @param percentage Compatibility percentage (0.0 to 1.0)
 * @param showPercentage Whether to show the percentage text
 */
@Composable
fun CompatibilityEnergyMeter(
  modifier: Modifier = Modifier,
  percentage: Float = 0.75f,
  showPercentage: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "energyMeter")

  val flow by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "flow"
  )

  val pulse by infiniteTransition.animateFloat(
    initialValue = 0.8f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1500,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse"
  )

  val meterColor = getCompatibilityColor(percentage)

  Canvas(modifier = modifier) {
    val width = size.width
    val height = size.height
    val barHeight = height * 0.3f
    val barY = height / 2f - barHeight / 2f

    // Background bar
    drawRoundRect(
      color = Color.Gray.copy(alpha = 0.2f),
      topLeft = Offset(0f, barY),
      size = androidx.compose.ui.geometry.Size(width, barHeight),
      cornerRadius = androidx.compose.ui.geometry.CornerRadius(barHeight / 2f)
    )

    // Filled portion with gradient
    val filledWidth = width * percentage
    if (filledWidth > 0f) {
      drawRoundRect(
        brush = Brush.horizontalGradient(
          colors = listOf(
            meterColor.copy(alpha = 0.6f),
            meterColor.copy(alpha = 1.0f)
          ),
          startX = 0f,
          endX = filledWidth
        ),
        topLeft = Offset(0f, barY),
        size = androidx.compose.ui.geometry.Size(filledWidth, barHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(barHeight / 2f)
      )

      // Animated energy particles flowing in the bar
      for (i in 0 until 5) {
        val particleProgress = (flow + i * 0.2f) % 1f
        if (particleProgress < percentage) {
          val particleX = width * particleProgress
          val particleY = barY + barHeight / 2f

          drawCircle(
            color = Color.White.copy(alpha = 0.8f * pulse),
            radius = 4f,
            center = Offset(particleX, particleY)
          )
        }
      }
    }

    // Energy glow at the end
    if (percentage > 0f) {
      drawCircle(
        color = meterColor.copy(alpha = 0.4f * pulse),
        radius = barHeight * 0.8f,
        center = Offset(filledWidth, barY + barHeight / 2f)
      )
    }
  }
}
