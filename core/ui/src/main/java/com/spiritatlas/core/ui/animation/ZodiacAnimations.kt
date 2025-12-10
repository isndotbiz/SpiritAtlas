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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.MoonlightSilver
import com.spiritatlas.core.ui.theme.StardustBlue
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Star data for constellation patterns
 */
private data class ConstellationStar(
  val x: Float,
  val y: Float,
  val brightness: Float = 1f,
  val size: Float = 1f
)

/**
 * Line connection between stars
 */
private data class StarConnection(
  val from: Int,
  val to: Int
)

/**
 * Zodiac constellation data
 * Real astronomical patterns for each zodiac sign
 */
private object ZodiacConstellations {
  fun getAries(): Pair<List<ConstellationStar>, List<StarConnection>> {
    val stars = listOf(
      ConstellationStar(0.3f, 0.4f, 1.0f, 1.2f),
      ConstellationStar(0.45f, 0.35f, 0.8f, 1.0f),
      ConstellationStar(0.6f, 0.45f, 0.9f, 1.1f)
    )
    val connections = listOf(
      StarConnection(0, 1),
      StarConnection(1, 2)
    )
    return stars to connections
  }

  fun getTaurus(): Pair<List<ConstellationStar>, List<StarConnection>> {
    val stars = listOf(
      ConstellationStar(0.5f, 0.3f, 1.0f, 1.5f), // Aldebaran
      ConstellationStar(0.3f, 0.4f, 0.7f, 0.9f),
      ConstellationStar(0.35f, 0.5f, 0.7f, 0.9f),
      ConstellationStar(0.45f, 0.55f, 0.8f, 1.0f),
      ConstellationStar(0.55f, 0.5f, 0.7f, 0.9f),
      ConstellationStar(0.65f, 0.45f, 0.7f, 0.9f),
      ConstellationStar(0.7f, 0.35f, 0.8f, 1.0f)
    )
    val connections = listOf(
      StarConnection(0, 1),
      StarConnection(1, 2),
      StarConnection(2, 3),
      StarConnection(3, 4),
      StarConnection(4, 5),
      StarConnection(5, 6),
      StarConnection(6, 0)
    )
    return stars to connections
  }

  fun getLeo(): Pair<List<ConstellationStar>, List<StarConnection>> {
    val stars = listOf(
      ConstellationStar(0.3f, 0.3f, 1.0f, 1.3f), // Regulus
      ConstellationStar(0.4f, 0.35f, 0.8f, 1.0f),
      ConstellationStar(0.5f, 0.3f, 0.9f, 1.1f),
      ConstellationStar(0.6f, 0.4f, 0.7f, 0.9f),
      ConstellationStar(0.55f, 0.55f, 0.8f, 1.0f),
      ConstellationStar(0.4f, 0.5f, 0.7f, 0.9f)
    )
    val connections = listOf(
      StarConnection(0, 1),
      StarConnection(1, 2),
      StarConnection(2, 3),
      StarConnection(3, 4),
      StarConnection(4, 5),
      StarConnection(5, 0)
    )
    return stars to connections
  }

  // Generic constellation pattern for other signs
  fun getGenericConstellation(): Pair<List<ConstellationStar>, List<StarConnection>> {
    val stars = listOf(
      ConstellationStar(0.35f, 0.35f, 0.9f, 1.0f),
      ConstellationStar(0.5f, 0.3f, 1.0f, 1.2f),
      ConstellationStar(0.65f, 0.35f, 0.8f, 0.9f),
      ConstellationStar(0.6f, 0.5f, 0.85f, 1.0f),
      ConstellationStar(0.5f, 0.6f, 0.9f, 1.1f),
      ConstellationStar(0.4f, 0.5f, 0.8f, 0.95f)
    )
    val connections = listOf(
      StarConnection(0, 1),
      StarConnection(1, 2),
      StarConnection(2, 3),
      StarConnection(3, 4),
      StarConnection(4, 5),
      StarConnection(5, 0)
    )
    return stars to connections
  }

  fun getConstellation(zodiacSign: String): Pair<List<ConstellationStar>, List<StarConnection>> {
    return when (zodiacSign.lowercase()) {
      "aries" -> getAries()
      "taurus" -> getTaurus()
      "leo" -> getLeo()
      else -> getGenericConstellation()
    }
  }
}

/**
 * Constellation reveal animation
 * Stars fade in sequentially, then lines draw to connect them
 *
 * @param modifier Modifier for the canvas
 * @param zodiacSign Name of the zodiac sign
 * @param isRevealed Whether to show the constellation
 * @param starColor Color for the stars
 * @param lineColor Color for the constellation lines
 */
@Composable
fun ConstellationReveal(
  modifier: Modifier = Modifier,
  zodiacSign: String = "Leo",
  isRevealed: Boolean = false,
  starColor: Color = AuraGold,
  lineColor: Color = StardustBlue
) {
  val progressAnimatable = remember { Animatable(0f) }

  LaunchedEffect(isRevealed) {
    if (isRevealed) {
      progressAnimatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(
          durationMillis = 3000,
          easing = FastOutSlowInEasing
        )
      )
    } else {
      progressAnimatable.snapTo(0f)
    }
  }

  val (stars, connections) = remember(zodiacSign) {
    ZodiacConstellations.getConstellation(zodiacSign)
  }

  val infiniteTransition = rememberInfiniteTransition(label = "starTwinkle")
  val twinkle by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1500,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "twinkle"
  )

  val textMeasurer = rememberTextMeasurer()

  Canvas(modifier = modifier) {
    val progress = progressAnimatable.value
    val canvasWidth = size.width
    val canvasHeight = size.height

    // Phase 1: Stars fade in sequentially (0.0 to 0.5)
    val starPhase = (progress * 2f).coerceAtMost(1f)

    stars.forEachIndexed { index, star ->
      val starProgress = ((starPhase - (index.toFloat() / stars.size)) * stars.size)
        .coerceIn(0f, 1f)

      if (starProgress > 0f) {
        val starX = canvasWidth * star.x
        val starY = canvasHeight * star.y
        val center = Offset(starX, starY)
        val baseSize = 6f * star.size
        val alpha = starProgress * star.brightness * twinkle

        // Star glow
        drawCircle(
          color = starColor.copy(alpha = alpha * 0.3f),
          radius = baseSize * 2.5f,
          center = center
        )

        // Outer star ring
        drawCircle(
          color = starColor.copy(alpha = alpha * 0.6f),
          radius = baseSize * 1.5f,
          center = center
        )

        // Star core
        drawCircle(
          color = Color.White.copy(alpha = alpha * 0.9f),
          radius = baseSize,
          center = center
        )

        // Star sparkle points
        drawStarSparkle(
          center = center,
          size = baseSize * 2f,
          color = starColor.copy(alpha = alpha * 0.7f)
        )
      }
    }

    // Phase 2: Lines draw to connect stars (0.5 to 1.0)
    if (progress > 0.5f) {
      val linePhase = ((progress - 0.5f) * 2f).coerceAtMost(1f)

      connections.forEachIndexed { index, connection ->
        val connectionProgress = ((linePhase - (index.toFloat() / connections.size)) * connections.size)
          .coerceIn(0f, 1f)

        if (connectionProgress > 0f) {
          val fromStar = stars[connection.from]
          val toStar = stars[connection.to]

          val fromX = canvasWidth * fromStar.x
          val fromY = canvasHeight * fromStar.y
          val toX = canvasWidth * toStar.x
          val toY = canvasHeight * toStar.y

          // Interpolate line drawing
          val currentToX = fromX + (toX - fromX) * connectionProgress
          val currentToY = fromY + (toY - fromY) * connectionProgress

          // Draw glowing line
          drawLine(
            brush = Brush.linearGradient(
              colors = listOf(
                lineColor.copy(alpha = 0.6f),
                lineColor.copy(alpha = 0.3f)
              ),
              start = Offset(fromX, fromY),
              end = Offset(currentToX, currentToY)
            ),
            start = Offset(fromX, fromY),
            end = Offset(currentToX, currentToY),
            strokeWidth = 3f,
            cap = StrokeCap.Round
          )

          // Inner bright line
          drawLine(
            color = Color.White.copy(alpha = 0.4f * connectionProgress),
            start = Offset(fromX, fromY),
            end = Offset(currentToX, currentToY),
            strokeWidth = 1f,
            cap = StrokeCap.Round
          )
        }
      }
    }

    // Phase 3: Constellation name shimmer (after 0.8)
    if (progress > 0.8f) {
      val nameProgress = ((progress - 0.8f) * 5f).coerceAtMost(1f)
      val shimmerOffset = ((progress - 0.8f) * 10f) % 1f

      drawConstellationName(
        textMeasurer = textMeasurer,
        name = zodiacSign,
        alpha = nameProgress,
        shimmerOffset = shimmerOffset,
        color = starColor
      )
    }
  }
}

/**
 * Draw star sparkle effect (4-pointed star)
 */
private fun DrawScope.drawStarSparkle(
  center: Offset,
  size: Float,
  color: Color
) {
  val path = Path()

  // Vertical beam
  path.moveTo(center.x, center.y - size)
  path.lineTo(center.x, center.y + size)

  // Horizontal beam
  path.moveTo(center.x - size, center.y)
  path.lineTo(center.x + size, center.y)

  drawPath(
    path = path,
    color = color,
    style = Stroke(width = 1f, cap = StrokeCap.Round)
  )
}

/**
 * Draw constellation name with shimmer effect
 */
private fun DrawScope.drawConstellationName(
  textMeasurer: androidx.compose.ui.text.TextMeasurer,
  name: String,
  alpha: Float,
  shimmerOffset: Float,
  color: Color
) {
  val textLayoutResult = textMeasurer.measure(
    text = name,
    style = TextStyle(
      fontSize = 24.sp,
      color = color.copy(alpha = alpha * 0.8f)
    )
  )

  val textWidth = textLayoutResult.size.width
  val textHeight = textLayoutResult.size.height
  val x = (size.width - textWidth) / 2f
  val y = size.height - textHeight - 20f

  // Shimmer effect using gradient
  drawText(
    textLayoutResult = textLayoutResult,
    topLeft = Offset(x, y)
  )
}

/**
 * Zodiac wheel rotation animation
 * Shows all 12 zodiac signs rotating in a circle
 */
@Composable
fun ZodiacWheelRotation(
  modifier: Modifier = Modifier,
  rotationDurationMs: Int = 30000,
  highlightedSign: String? = null
) {
  val infiniteTransition = rememberInfiniteTransition(label = "zodiacWheel")

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
    label = "wheelRotation"
  )

  val zodiacSigns = listOf(
    "Aries", "Taurus", "Gemini", "Cancer",
    "Leo", "Virgo", "Libra", "Scorpio",
    "Sagittarius", "Capricorn", "Aquarius", "Pisces"
  )

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = minOf(size.width, size.height) / 2.5f
    val angleStep = 360f / zodiacSigns.size

    // Draw outer circle
    drawCircle(
      color = CosmicBlue.copy(alpha = 0.2f),
      radius = radius * 1.1f,
      center = center,
      style = Stroke(width = 2f)
    )

    zodiacSigns.forEachIndexed { index, sign ->
      val angle = (rotation + index * angleStep) * PI.toFloat() / 180f
      val x = center.x + radius * cos(angle)
      val y = center.y + radius * sin(angle)

      val isHighlighted = sign == highlightedSign
      val starSize = if (isHighlighted) 12f else 8f
      val starColor = if (isHighlighted) AuraGold else MoonlightSilver

      // Draw constellation symbol (simplified)
      drawCircle(
        color = starColor.copy(alpha = if (isHighlighted) 1.0f else 0.7f),
        radius = starSize,
        center = Offset(x, y)
      )

      if (isHighlighted) {
        drawCircle(
          color = starColor.copy(alpha = 0.3f),
          radius = starSize * 2f,
          center = Offset(x, y)
        )
      }
    }

    // Draw center symbol
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          AuraGold.copy(alpha = 0.6f),
          CosmicBlue.copy(alpha = 0.3f),
          Color.Transparent
        ),
        center = center,
        radius = radius * 0.3f
      ),
      radius = radius * 0.3f,
      center = center
    )
  }
}

/**
 * Star field background animation
 * Creates a twinkling star field effect
 */
@Composable
fun StarFieldBackground(
  modifier: Modifier = Modifier,
  starCount: Int = 100,
  color: Color = MoonlightSilver
) {
  val infiniteTransition = rememberInfiniteTransition(label = "starField")

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
    label = "time"
  )

  val stars = remember(starCount) {
    List(starCount) {
      Triple(
        kotlin.random.Random.nextFloat(), // x
        kotlin.random.Random.nextFloat(), // y
        kotlin.random.Random.nextFloat() * 2 * PI.toFloat() // phase
      )
    }
  }

  Canvas(modifier = modifier) {
    stars.forEach { (x, y, phase) ->
      val alpha = 0.2f + 0.6f * kotlin.math.sin(time + phase)
      val starSize = 1f + 2f * kotlin.math.cos(time * 0.7f + phase)

      drawCircle(
        color = color.copy(alpha = alpha),
        radius = starSize,
        center = Offset(size.width * x, size.height * y)
      )
    }
  }
}

/**
 * Zodiac sign transition animation
 * Morphs from one zodiac constellation to another
 */
@Composable
fun ZodiacTransition(
  modifier: Modifier = Modifier,
  fromSign: String = "Leo",
  toSign: String = "Virgo",
  progress: Float = 0f
) {
  val (fromStars, fromConnections) = remember(fromSign) {
    ZodiacConstellations.getConstellation(fromSign)
  }
  val (toStars, toConnections) = remember(toSign) {
    ZodiacConstellations.getConstellation(toSign)
  }

  Canvas(modifier = modifier) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    // Interpolate star positions
    val maxStars = maxOf(fromStars.size, toStars.size)
    for (i in 0 until maxStars) {
      val fromStar = fromStars.getOrNull(i) ?: fromStars.last()
      val toStar = toStars.getOrNull(i) ?: toStars.last()

      val x = canvasWidth * (fromStar.x + (toStar.x - fromStar.x) * progress)
      val y = canvasHeight * (fromStar.y + (toStar.y - fromStar.y) * progress)
      val brightness = fromStar.brightness + (toStar.brightness - fromStar.brightness) * progress

      val center = Offset(x, y)
      val starSize = 6f

      // Draw star
      drawCircle(
        color = AuraGold.copy(alpha = brightness * 0.3f),
        radius = starSize * 2f,
        center = center
      )

      drawCircle(
        color = Color.White.copy(alpha = brightness * 0.9f),
        radius = starSize,
        center = center
      )
    }
  }
}
