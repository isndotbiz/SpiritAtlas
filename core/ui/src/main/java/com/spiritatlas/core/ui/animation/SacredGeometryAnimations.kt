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
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.CosmicViolet
import com.spiritatlas.core.ui.theme.MysticPurple
import com.spiritatlas.core.ui.theme.SpiritualPurple
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Flower of Life expanding animation
 * Sacred geometry pattern with overlapping circles
 *
 * @param modifier Modifier for the canvas
 * @param circleCount Number of circles to draw (typically 7 or 19)
 * @param color Color of the pattern
 * @param strokeWidth Width of the circle strokes
 * @param animate Whether to animate the expansion
 */
@Composable
fun FlowerOfLife(
  modifier: Modifier = Modifier,
  circleCount: Int = 7,
  color: Color = AuraGold,
  strokeWidth: Float = 2f,
  animate: Boolean = true
) {
  val progressAnimatable = remember { Animatable(if (animate) 0f else 1f) }

  LaunchedEffect(animate) {
    if (animate) {
      progressAnimatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(
          durationMillis = 2000,
          easing = FastOutSlowInEasing
        )
      )
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "flowerGlow")
  val glow by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow"
  )

  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 40000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotation"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = minOf(size.width, size.height) * 0.15f
    val progress = progressAnimatable.value

    rotate(if (animate) 0f else rotation, center) {
      // Center circle
      if (progress > 0f) {
        drawCircle(
          color = color.copy(alpha = 0.2f * glow),
          radius = radius * progress * 1.5f,
          center = center
        )

        drawCircle(
          color = color.copy(alpha = glow),
          radius = radius * progress,
          center = center,
          style = Stroke(width = strokeWidth)
        )
      }

      // Six surrounding circles (first ring)
      if (circleCount >= 7 && progress > 0.3f) {
        val ringProgress = ((progress - 0.3f) / 0.7f).coerceAtMost(1f)

        for (i in 0 until 6) {
          val angle = (i * 60f) * PI.toFloat() / 180f
          val offsetX = center.x + radius * cos(angle)
          val offsetY = center.y + radius * sin(angle)
          val circleCenter = Offset(offsetX, offsetY)

          drawCircle(
            color = color.copy(alpha = 0.15f * glow * ringProgress),
            radius = radius * ringProgress * 1.3f,
            center = circleCenter
          )

          drawCircle(
            color = color.copy(alpha = 0.8f * glow * ringProgress),
            radius = radius * ringProgress,
            center = circleCenter,
            style = Stroke(width = strokeWidth)
          )
        }
      }

      // Twelve outer circles (second ring) - for 19-circle pattern
      if (circleCount >= 19 && progress > 0.6f) {
        val outerProgress = ((progress - 0.6f) / 0.4f).coerceAtMost(1f)

        for (i in 0 until 12) {
          val angle = (i * 30f) * PI.toFloat() / 180f
          val distance = if (i % 2 == 0) radius * 2f else radius * sqrt(3f)
          val offsetX = center.x + distance * cos(angle)
          val offsetY = center.y + distance * sin(angle)
          val circleCenter = Offset(offsetX, offsetY)

          drawCircle(
            color = color.copy(alpha = 0.6f * glow * outerProgress),
            radius = radius * outerProgress,
            center = circleCenter,
            style = Stroke(width = strokeWidth)
          )
        }
      }
    }
  }
}

/**
 * Metatron's Cube rotation animation
 * Complex sacred geometry formed from Flower of Life
 *
 * @param modifier Modifier for the canvas
 * @param color Color of the pattern
 * @param rotationDurationMs Duration of one complete rotation
 */
@Composable
fun MetatronsCube(
  modifier: Modifier = Modifier,
  color: Color = SpiritualPurple,
  rotationDurationMs: Int = 30000
) {
  val infiniteTransition = rememberInfiniteTransition(label = "metatron")

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
    label = "rotation"
  )

  val glow by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = minOf(size.width, size.height) * 0.3f

    rotate(rotation, center) {
      // Draw 13 circles (sacred number)
      val circlePositions = getMetatronCirclePositions(center, radius)

      circlePositions.forEachIndexed { index, position ->
        // Glow
        drawCircle(
          color = color.copy(alpha = 0.2f * glow),
          radius = 15f,
          center = position
        )

        // Circle
        drawCircle(
          color = color.copy(alpha = 0.8f * glow),
          radius = 10f,
          center = position,
          style = Stroke(width = 2f)
        )
      }

      // Draw connecting lines between all circles
      for (i in circlePositions.indices) {
        for (j in i + 1 until circlePositions.size) {
          drawLine(
            color = color.copy(alpha = 0.3f * glow),
            start = circlePositions[i],
            end = circlePositions[j],
            strokeWidth = 1f,
            cap = StrokeCap.Round
          )
        }
      }

      // Draw inner sacred geometry shape (cube outline)
      drawMetatronInnerShape(
        center = center,
        radius = radius * 0.4f,
        color = color,
        alpha = glow
      )
    }
  }
}

/**
 * Get positions of 13 circles in Metatron's Cube
 */
private fun getMetatronCirclePositions(center: Offset, radius: Float): List<Offset> {
  val positions = mutableListOf<Offset>()

  // Center circle
  positions.add(center)

  // Inner hexagon (6 circles)
  for (i in 0 until 6) {
    val angle = (i * 60f) * PI.toFloat() / 180f
    positions.add(
      Offset(
        center.x + radius * 0.5f * cos(angle),
        center.y + radius * 0.5f * sin(angle)
      )
    )
  }

  // Outer hexagon (6 circles)
  for (i in 0 until 6) {
    val angle = (i * 60f) * PI.toFloat() / 180f
    positions.add(
      Offset(
        center.x + radius * cos(angle),
        center.y + radius * sin(angle)
      )
    )
  }

  return positions
}

/**
 * Draw inner shape of Metatron's Cube
 */
private fun DrawScope.drawMetatronInnerShape(
  center: Offset,
  radius: Float,
  color: Color,
  alpha: Float
) {
  // Draw hexagon
  val path = Path()
  for (i in 0 until 6) {
    val angle = (i * 60f) * PI.toFloat() / 180f
    val x = center.x + radius * cos(angle)
    val y = center.y + radius * sin(angle)

    if (i == 0) {
      path.moveTo(x, y)
    } else {
      path.lineTo(x, y)
    }
  }
  path.close()

  drawPath(
    path = path,
    color = color.copy(alpha = 0.4f * alpha),
    style = Stroke(width = 2f)
  )
}

/**
 * Sri Yantra morphing animation
 * Sacred Hindu diagram with nested triangles
 *
 * @param modifier Modifier for the canvas
 * @param morphProgress Progress of morphing animation (0.0 to 1.0)
 * @param color Color of the yantra
 */
@Composable
fun SriYantra(
  modifier: Modifier = Modifier,
  morphProgress: Float = 0f,
  color: Color = AuraGold
) {
  val infiniteTransition = rememberInfiniteTransition(label = "sriYantra")

  val glow by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow"
  )

  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 50000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotation"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = minOf(size.width, size.height) * 0.35f

    // Draw outer circles
    for (i in 3 downTo 1) {
      drawCircle(
        color = color.copy(alpha = 0.1f * glow * i / 3f),
        radius = radius * (1f + 0.1f * i),
        center = center,
        style = Stroke(width = 1f)
      )
    }

    rotate(rotation * 0.2f, center) {
      // Draw 9 interlocking triangles (simplified representation)
      // 5 pointing down (Shakti - feminine)
      for (i in 0 until 5) {
        val scale = 1f - (i * 0.15f)
        val rotationOffset = i * 8f + morphProgress * 5f

        rotate(rotationOffset, center) {
          drawTriangle(
            center = center,
            radius = radius * scale,
            pointingUp = false,
            color = color,
            alpha = 0.4f * glow,
            strokeWidth = 2f
          )
        }
      }

      // 4 pointing up (Shiva - masculine)
      for (i in 0 until 4) {
        val scale = 0.92f - (i * 0.15f)
        val rotationOffset = i * 10f - morphProgress * 5f

        rotate(rotationOffset, center) {
          drawTriangle(
            center = center,
            radius = radius * scale,
            pointingUp = true,
            color = color,
            alpha = 0.4f * glow,
            strokeWidth = 2f
          )
        }
      }
    }

    // Draw center bindu (point)
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color.White.copy(alpha = 0.9f * glow),
          color.copy(alpha = 0.7f * glow)
        ),
        center = center,
        radius = 8f
      ),
      radius = 8f,
      center = center
    )
  }
}

/**
 * Draw a triangle (upward or downward pointing)
 */
private fun DrawScope.drawTriangle(
  center: Offset,
  radius: Float,
  pointingUp: Boolean,
  color: Color,
  alpha: Float,
  strokeWidth: Float
) {
  val path = Path()
  val height = radius * sqrt(3f) / 2f

  if (pointingUp) {
    // Point at top
    path.moveTo(center.x, center.y - height)
    path.lineTo(center.x - radius, center.y + height / 2f)
    path.lineTo(center.x + radius, center.y + height / 2f)
  } else {
    // Point at bottom
    path.moveTo(center.x, center.y + height)
    path.lineTo(center.x - radius, center.y - height / 2f)
    path.lineTo(center.x + radius, center.y - height / 2f)
  }
  path.close()

  // Glow
  drawPath(
    path = path,
    color = color.copy(alpha = alpha * 0.3f)
  )

  // Outline
  drawPath(
    path = path,
    color = color.copy(alpha = alpha),
    style = Stroke(width = strokeWidth)
  )
}

/**
 * Golden Ratio Spiral growth animation
 * Fibonacci spiral with golden ratio proportions
 *
 * @param modifier Modifier for the canvas
 * @param spiralProgress Progress of spiral growth (0.0 to 1.0)
 * @param color Color of the spiral
 */
@Composable
fun GoldenRatioSpiral(
  modifier: Modifier = Modifier,
  spiralProgress: Float = 1f,
  color: Color = AuraGold
) {
  val infiniteTransition = rememberInfiniteTransition(label = "goldenSpiral")

  val glow by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val startSize = minOf(size.width, size.height) * 0.05f

    drawFibonacciSpiral(
      center = center,
      startSize = startSize,
      progress = spiralProgress,
      color = color,
      alpha = glow,
      segments = 12
    )
  }
}

/**
 * Draw Fibonacci spiral using golden ratio
 */
private fun DrawScope.drawFibonacciSpiral(
  center: Offset,
  startSize: Float,
  progress: Float,
  color: Color,
  alpha: Float,
  segments: Int
) {
  val phi = 1.618034f // Golden ratio
  var currentSize = startSize
  var currentX = center.x
  var currentY = center.y
  var currentAngle = 0f

  val path = Path()
  path.moveTo(currentX, currentY)

  for (i in 0 until segments) {
    if (i.toFloat() / segments > progress) break

    val segmentAlpha = alpha * (1f - i.toFloat() / segments * 0.5f)

    // Calculate arc for this segment
    val sweepAngle = 90f
    val arcRadius = currentSize

    // Draw quarter circle arc
    val startAngle = currentAngle
    val angleStep = 5f
    val steps = (sweepAngle / angleStep).toInt()

    for (step in 0..steps) {
      val angle = (startAngle + step * angleStep) * PI.toFloat() / 180f
      val x = currentX + arcRadius * cos(angle)
      val y = currentY + arcRadius * sin(angle)
      path.lineTo(x, y)
    }

    // Update position for next segment
    when (i % 4) {
      0 -> {
        currentX += currentSize
        currentAngle = 90f
      }
      1 -> {
        currentY += currentSize
        currentAngle = 180f
      }
      2 -> {
        currentX -= currentSize
        currentAngle = 270f
      }
      3 -> {
        currentY -= currentSize
        currentAngle = 0f
      }
    }

    currentSize *= phi
  }

  // Draw glow
  drawPath(
    path = path,
    color = color.copy(alpha = alpha * 0.3f),
    style = Stroke(width = 6f, cap = StrokeCap.Round)
  )

  // Draw spiral
  drawPath(
    path = path,
    color = color.copy(alpha = alpha),
    style = Stroke(width = 3f, cap = StrokeCap.Round)
  )
}

/**
 * Vesica Piscis animation
 * Two overlapping circles creating sacred intersection
 *
 * @param modifier Modifier for the canvas
 * @param color Color of the pattern
 * @param animate Whether to animate the intersection
 */
@Composable
fun VesicaPiscis(
  modifier: Modifier = Modifier,
  color: Color = MysticPurple,
  animate: Boolean = true
) {
  val progressAnimatable = remember { Animatable(if (animate) 0f else 1f) }

  LaunchedEffect(animate) {
    if (animate) {
      progressAnimatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(
          durationMillis = 2000,
          easing = FastOutSlowInEasing
        )
      )
    }
  }

  val infiniteTransition = rememberInfiniteTransition(label = "vesicaGlow")
  val glow by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = minOf(size.width, size.height) * 0.25f
    val progress = progressAnimatable.value

    val separation = radius * progress

    // Left circle
    val leftCenter = Offset(center.x - separation / 2f, center.y)
    drawCircle(
      color = color.copy(alpha = 0.2f * glow),
      radius = radius * 1.2f,
      center = leftCenter
    )
    drawCircle(
      color = color.copy(alpha = 0.6f * glow),
      radius = radius,
      center = leftCenter,
      style = Stroke(width = 2f)
    )

    // Right circle
    val rightCenter = Offset(center.x + separation / 2f, center.y)
    drawCircle(
      color = color.copy(alpha = 0.2f * glow),
      radius = radius * 1.2f,
      center = rightCenter
    )
    drawCircle(
      color = color.copy(alpha = 0.6f * glow),
      radius = radius,
      center = rightCenter,
      style = Stroke(width = 2f)
    )

    // Highlight the intersection (vesica piscis)
    if (progress > 0.5f) {
      val intersectionAlpha = ((progress - 0.5f) * 2f).coerceAtMost(1f)

      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color.White.copy(alpha = 0.4f * intersectionAlpha * glow),
            color.copy(alpha = 0.2f * intersectionAlpha * glow),
            Color.Transparent
          ),
          center = center,
          radius = radius * 0.6f
        ),
        radius = radius * 0.6f,
        center = center
      )
    }
  }
}

/**
 * Platonic Solids rotation
 * 3D sacred geometry shapes rotating
 *
 * @param modifier Modifier for the canvas
 * @param solidType Type of platonic solid ("tetrahedron", "cube", "octahedron", "dodecahedron", "icosahedron")
 * @param color Color of the shape
 */
@Composable
fun PlatonicSolid(
  modifier: Modifier = Modifier,
  solidType: String = "tetrahedron",
  color: Color = CosmicViolet
) {
  val infiniteTransition = rememberInfiniteTransition(label = "platonicRotation")

  val rotationX by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 8000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotationX"
  )

  val rotationY by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 10000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotationY"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val size = minOf(size.width, size.height) * 0.3f

    // Simplified 2D projection of 3D rotation
    rotate(rotationX, center) {
      when (solidType.lowercase()) {
        "tetrahedron" -> drawTetrahedron(center, size, color)
        "cube" -> drawCube(center, size, color, rotationY)
        "octahedron" -> drawOctahedron(center, size, color)
        else -> drawTetrahedron(center, size, color)
      }
    }
  }
}

private fun DrawScope.drawTetrahedron(center: Offset, size: Float, color: Color) {
  // Draw as triangle with depth lines
  drawTriangle(
    center = center,
    radius = size,
    pointingUp = true,
    color = color,
    alpha = 0.8f,
    strokeWidth = 2f
  )

  // Draw lines from top to base creating 3D effect
  val height = size * sqrt(3f) / 2f
  val apex = Offset(center.x, center.y - height)

  drawLine(
    color = color.copy(alpha = 0.5f),
    start = apex,
    end = Offset(center.x, center.y + height / 3f),
    strokeWidth = 2f
  )
}

private fun DrawScope.drawCube(center: Offset, size: Float, color: Color, rotation: Float) {
  // Front face
  val frontTopLeft = Offset(center.x - size / 2, center.y - size / 2)
  val frontTopRight = Offset(center.x + size / 2, center.y - size / 2)
  val frontBottomLeft = Offset(center.x - size / 2, center.y + size / 2)
  val frontBottomRight = Offset(center.x + size / 2, center.y + size / 2)

  // Back face (with perspective)
  val depth = size * 0.4f
  val backTopLeft = Offset(frontTopLeft.x + depth, frontTopLeft.y - depth)
  val backTopRight = Offset(frontTopRight.x + depth, frontTopRight.y - depth)
  val backBottomLeft = Offset(frontBottomLeft.x + depth, frontBottomLeft.y - depth)
  val backBottomRight = Offset(frontBottomRight.x + depth, frontBottomRight.y - depth)

  // Draw back face
  drawLine(color.copy(alpha = 0.3f), backTopLeft, backTopRight, 2f)
  drawLine(color.copy(alpha = 0.3f), backTopRight, backBottomRight, 2f)
  drawLine(color.copy(alpha = 0.3f), backBottomRight, backBottomLeft, 2f)
  drawLine(color.copy(alpha = 0.3f), backBottomLeft, backTopLeft, 2f)

  // Draw connecting lines
  drawLine(color.copy(alpha = 0.5f), frontTopLeft, backTopLeft, 2f)
  drawLine(color.copy(alpha = 0.5f), frontTopRight, backTopRight, 2f)
  drawLine(color.copy(alpha = 0.5f), frontBottomLeft, backBottomLeft, 2f)
  drawLine(color.copy(alpha = 0.5f), frontBottomRight, backBottomRight, 2f)

  // Draw front face
  drawLine(color.copy(alpha = 0.8f), frontTopLeft, frontTopRight, 2f)
  drawLine(color.copy(alpha = 0.8f), frontTopRight, frontBottomRight, 2f)
  drawLine(color.copy(alpha = 0.8f), frontBottomRight, frontBottomLeft, 2f)
  drawLine(color.copy(alpha = 0.8f), frontBottomLeft, frontTopLeft, 2f)
}

private fun DrawScope.drawOctahedron(center: Offset, size: Float, color: Color) {
  // Draw as two pyramids (simplified 2D projection)
  val top = Offset(center.x, center.y - size)
  val bottom = Offset(center.x, center.y + size)
  val left = Offset(center.x - size * 0.7f, center.y)
  val right = Offset(center.x + size * 0.7f, center.y)

  // Top pyramid
  drawLine(color.copy(alpha = 0.8f), top, left, 2f)
  drawLine(color.copy(alpha = 0.8f), top, right, 2f)
  drawLine(color.copy(alpha = 0.6f), left, right, 2f)

  // Bottom pyramid
  drawLine(color.copy(alpha = 0.8f), bottom, left, 2f)
  drawLine(color.copy(alpha = 0.8f), bottom, right, 2f)
}
