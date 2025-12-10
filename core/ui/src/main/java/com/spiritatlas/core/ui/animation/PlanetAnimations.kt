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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.spiritatlas.core.ui.theme.AirCyan
import com.spiritatlas.core.ui.theme.AuraGold
import com.spiritatlas.core.ui.theme.ChakraRed
import com.spiritatlas.core.ui.theme.CosmicBlue
import com.spiritatlas.core.ui.theme.CosmicViolet
import com.spiritatlas.core.ui.theme.MoonlightSilver
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.core.ui.theme.TantricRose
import com.spiritatlas.core.ui.theme.WaterTeal
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Planet data for solar system
 */
private data class Planet(
  val name: String,
  val color: Color,
  val size: Float,
  val orbitRadius: Float,
  val orbitPeriod: Float, // In seconds (relative)
  val hasRings: Boolean = false
)

/**
 * Get planetary colors and properties
 */
private object PlanetarySystem {
  val Sun = Planet("Sun", AuraGold, 30f, 0f, 0f)
  val Mercury = Planet("Mercury", Color(0xFFB5B5B5), 6f, 60f, 8f)
  val Venus = Planet("Venus", TantricRose, 10f, 90f, 12f)
  val Earth = Planet("Earth", CosmicBlue, 10f, 120f, 16f)
  val Mars = Planet("Mars", ChakraRed, 8f, 150f, 20f)
  val Jupiter = Planet("Jupiter", AuraGold.copy(alpha = 0.8f), 20f, 200f, 30f)
  val Saturn = Planet("Saturn", AuraGold.copy(alpha = 0.7f), 18f, 250f, 40f, hasRings = true)
  val Uranus = Planet("Uranus", AirCyan, 14f, 290f, 50f)
  val Neptune = Planet("Neptune", WaterTeal, 14f, 320f, 60f)

  val allPlanets = listOf(Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune)
  val innerPlanets = listOf(Mercury, Venus, Earth, Mars)
  val outerPlanets = listOf(Jupiter, Saturn, Uranus, Neptune)
}

/**
 * Full solar system with orbiting planets
 * Features accurate orbital speeds and beautiful glow effects
 *
 * @param modifier Modifier for the canvas
 * @param showOrbits Whether to show orbital paths
 * @param showInnerPlanets Whether to show inner planets (Mercury, Venus, Earth, Mars)
 * @param showOuterPlanets Whether to show outer planets (Jupiter, Saturn, Uranus, Neptune)
 * @param speedMultiplier Speed multiplier for animation (1.0 = normal)
 */
@Composable
fun SolarSystemAnimation(
  modifier: Modifier = Modifier,
  showOrbits: Boolean = true,
  showInnerPlanets: Boolean = true,
  showOuterPlanets: Boolean = true,
  speedMultiplier: Float = 1.0f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "solarSystem")

  val time by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = (20000 / speedMultiplier).toInt(),
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "time"
  )

  val sunPulse by infiniteTransition.animateFloat(
    initialValue = 0.9f,
    targetValue = 1.1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 2000,
        easing = FastOutSlowInEasing
      ),
      repeatMode = RepeatMode.Reverse
    ),
    label = "sunPulse"
  )

  val planetsToShow = mutableListOf<Planet>()
  if (showInnerPlanets) planetsToShow.addAll(PlanetarySystem.innerPlanets)
  if (showOuterPlanets) planetsToShow.addAll(PlanetarySystem.outerPlanets)

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val scale = minOf(size.width, size.height) / 700f

    // Draw the Sun
    drawSun(
      center = center,
      size = PlanetarySystem.Sun.size * scale * sunPulse,
      color = PlanetarySystem.Sun.color
    )

    // Draw orbits and planets
    planetsToShow.forEach { planet ->
      val scaledOrbitRadius = planet.orbitRadius * scale

      // Draw orbit path
      if (showOrbits) {
        drawCircle(
          color = Color.White.copy(alpha = 0.1f),
          radius = scaledOrbitRadius,
          center = center,
          style = Stroke(width = 1f)
        )
      }

      // Calculate planet position
      val angle = (time / planet.orbitPeriod) * 360f * PI.toFloat() / 180f
      val planetX = center.x + scaledOrbitRadius * cos(angle)
      val planetY = center.y + scaledOrbitRadius * sin(angle)
      val planetCenter = Offset(planetX, planetY)

      // Draw planet with shadow and glow
      drawPlanet(
        center = planetCenter,
        planet = planet,
        scale = scale,
        sunCenter = center
      )
    }
  }
}

/**
 * Draw the sun with glow and corona
 */
private fun DrawScope.drawSun(
  center: Offset,
  size: Float,
  color: Color
) {
  // Outer corona
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        color.copy(alpha = 0.2f),
        Color.Transparent
      ),
      center = center,
      radius = size * 3f
    ),
    radius = size * 3f,
    center = center
  )

  // Inner glow
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        Color.White.copy(alpha = 0.8f),
        color.copy(alpha = 0.9f),
        color.copy(alpha = 0.7f)
      ),
      center = center,
      radius = size * 1.5f
    ),
    radius = size * 1.5f,
    center = center
  )

  // Sun core
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        Color.White,
        color
      ),
      center = center,
      radius = size
    ),
    radius = size,
    center = center
  )
}

/**
 * Draw a planet with glow, shadow, and optional rings
 */
private fun DrawScope.drawPlanet(
  center: Offset,
  planet: Planet,
  scale: Float,
  sunCenter: Offset
) {
  val planetSize = planet.size * scale

  // Calculate shadow direction (away from sun)
  val dx = center.x - sunCenter.x
  val dy = center.y - sunCenter.y
  val distance = kotlin.math.sqrt(dx * dx + dy * dy)
  val shadowOffsetX = (dx / distance) * planetSize * 0.3f
  val shadowOffsetY = (dy / distance) * planetSize * 0.3f

  // Planet glow (opposite side from sun)
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        planet.color.copy(alpha = 0.4f),
        Color.Transparent
      ),
      center = Offset(center.x - shadowOffsetX, center.y - shadowOffsetY),
      radius = planetSize * 1.8f
    ),
    radius = planetSize * 1.8f,
    center = Offset(center.x - shadowOffsetX, center.y - shadowOffsetY)
  )

  // Planet with gradient (lit side)
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(
        planet.color.copy(alpha = 1.0f),
        planet.color.copy(alpha = 0.7f),
        planet.color.copy(alpha = 0.5f)
      ),
      center = Offset(center.x - shadowOffsetX * 0.5f, center.y - shadowOffsetY * 0.5f),
      radius = planetSize
    ),
    radius = planetSize,
    center = center
  )

  // Draw rings for Saturn
  if (planet.hasRings) {
    drawPlanetRings(
      center = center,
      innerRadius = planetSize * 1.4f,
      outerRadius = planetSize * 2.2f,
      color = planet.color
    )
  }

  // Highlight on lit side
  drawCircle(
    color = Color.White.copy(alpha = 0.3f),
    radius = planetSize * 0.4f,
    center = Offset(center.x - shadowOffsetX * 0.7f, center.y - shadowOffsetY * 0.7f)
  )
}

/**
 * Draw planet rings (for Saturn)
 */
private fun DrawScope.drawPlanetRings(
  center: Offset,
  innerRadius: Float,
  outerRadius: Float,
  color: Color
) {
  // Draw multiple ring bands
  val bandCount = 5
  val bandWidth = (outerRadius - innerRadius) / bandCount

  for (i in 0 until bandCount) {
    val radius = innerRadius + bandWidth * i + bandWidth / 2f
    val alpha = 0.4f - (i * 0.05f)

    drawCircle(
      color = color.copy(alpha = alpha),
      radius = radius,
      center = center,
      style = Stroke(width = bandWidth * 0.8f)
    )
  }
}

/**
 * Single planet orbit animation
 * Shows one planet orbiting around a center point
 *
 * @param modifier Modifier for the canvas
 * @param planetName Name of the planet ("Mercury", "Venus", etc.)
 * @param orbitDurationMs Duration of one complete orbit
 * @param showOrbit Whether to show the orbital path
 */
@Composable
fun SinglePlanetOrbit(
  modifier: Modifier = Modifier,
  planetName: String = "Earth",
  orbitDurationMs: Int = 8000,
  showOrbit: Boolean = true
) {
  val infiniteTransition = rememberInfiniteTransition(label = "planetOrbit")

  val angle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = orbitDurationMs,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "orbitAngle"
  )

  val planet = remember(planetName) {
    when (planetName.lowercase()) {
      "mercury" -> PlanetarySystem.Mercury
      "venus" -> PlanetarySystem.Venus
      "earth" -> PlanetarySystem.Earth
      "mars" -> PlanetarySystem.Mars
      "jupiter" -> PlanetarySystem.Jupiter
      "saturn" -> PlanetarySystem.Saturn
      "uranus" -> PlanetarySystem.Uranus
      "neptune" -> PlanetarySystem.Neptune
      else -> PlanetarySystem.Earth
    }
  }

  Canvas(modifier = modifier) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val orbitRadius = minOf(size.width, size.height) * 0.35f

    // Draw orbit path
    if (showOrbit) {
      drawCircle(
        color = planet.color.copy(alpha = 0.2f),
        radius = orbitRadius,
        center = center,
        style = Stroke(width = 2f)
      )
    }

    // Draw central point (sun/center)
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color.White.copy(alpha = 0.8f),
          AuraGold.copy(alpha = 0.6f),
          Color.Transparent
        ),
        center = center,
        radius = 20f
      ),
      radius = 20f,
      center = center
    )

    // Calculate planet position
    val radians = angle * PI.toFloat() / 180f
    val planetX = center.x + orbitRadius * cos(radians)
    val planetY = center.y + orbitRadius * sin(radians)
    val planetCenter = Offset(planetX, planetY)

    // Draw planet
    drawPlanet(
      center = planetCenter,
      planet = planet.copy(size = planet.size * 1.5f),
      scale = 1f,
      sunCenter = center
    )

    // Draw orbital trail
    drawOrbitalTrail(
      center = center,
      currentAngle = angle,
      radius = orbitRadius,
      color = planet.color
    )
  }
}

/**
 * Draw orbital trail showing recent path
 */
private fun DrawScope.drawOrbitalTrail(
  center: Offset,
  currentAngle: Float,
  radius: Float,
  color: Color
) {
  val trailLength = 90f // degrees
  val segments = 20

  val path = Path()
  var isFirst = true

  for (i in 0..segments) {
    val angle = (currentAngle - trailLength * (i.toFloat() / segments)) * PI.toFloat() / 180f
    val x = center.x + radius * cos(angle)
    val y = center.y + radius * sin(angle)

    if (isFirst) {
      path.moveTo(x, y)
      isFirst = false
    } else {
      path.lineTo(x, y)
    }
  }

  // Draw gradient trail
  drawPath(
    path = path,
    brush = Brush.linearGradient(
      colors = listOf(
        color.copy(alpha = 0.6f),
        color.copy(alpha = 0.3f),
        Color.Transparent
      )
    ),
    style = Stroke(width = 3f, cap = StrokeCap.Round)
  )
}

/**
 * Planetary alignment animation
 * Shows multiple planets aligning in a row (rare astronomical event)
 *
 * @param modifier Modifier for the canvas
 * @param alignmentProgress Progress of alignment (0.0 = orbiting, 1.0 = aligned)
 */
@Composable
fun PlanetaryAlignment(
  modifier: Modifier = Modifier,
  alignmentProgress: Float = 0f
) {
  val infiniteTransition = rememberInfiniteTransition(label = "alignment")

  val time by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 20000,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "time"
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
    val scale = minOf(size.width, size.height) / 700f

    // Draw the Sun
    drawSun(
      center = center,
      size = PlanetarySystem.Sun.size * scale,
      color = PlanetarySystem.Sun.color
    )

    // Draw planets in alignment
    PlanetarySystem.innerPlanets.forEachIndexed { index, planet ->
      // Calculate normal orbital position
      val orbitAngle = (time / planet.orbitPeriod) * 360f * PI.toFloat() / 180f
      val orbitX = center.x + planet.orbitRadius * scale * cos(orbitAngle)
      val orbitY = center.y + planet.orbitRadius * scale * sin(orbitAngle)

      // Calculate aligned position (horizontal line)
      val alignedX = center.x + planet.orbitRadius * scale
      val alignedY = center.y

      // Interpolate between orbit and alignment
      val planetX = orbitX + (alignedX - orbitX) * alignmentProgress
      val planetY = orbitY + (alignedY - orbitY) * alignmentProgress

      val planetCenter = Offset(planetX, planetY)

      // Draw planet
      drawPlanet(
        center = planetCenter,
        planet = planet,
        scale = scale,
        sunCenter = center
      )

      // If aligned, draw connection line
      if (alignmentProgress > 0.5f) {
        drawLine(
          color = Color.White.copy(alpha = 0.3f * (alignmentProgress - 0.5f) * 2f * glow),
          start = center,
          end = planetCenter,
          strokeWidth = 1f,
          cap = StrokeCap.Round
        )
      }
    }

    // Draw alignment energy burst
    if (alignmentProgress > 0.9f) {
      val burstAlpha = (alignmentProgress - 0.9f) * 10f
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color.White.copy(alpha = burstAlpha * 0.6f * glow),
            SpiritualPurple.copy(alpha = burstAlpha * 0.4f * glow),
            Color.Transparent
          ),
          center = center,
          radius = 300f * scale
        ),
        radius = 300f * scale,
        center = center
      )
    }
  }
}

/**
 * Moon phases animation
 * Shows the moon cycling through its phases
 *
 * @param modifier Modifier for the canvas
 * @param phaseDurationMs Duration of one complete moon cycle
 */
@Composable
fun MoonPhasesAnimation(
  modifier: Modifier = Modifier,
  phaseDurationMs: Int = 8000
) {
  val infiniteTransition = rememberInfiniteTransition(label = "moonPhases")

  val phase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = phaseDurationMs,
        easing = LinearEasing
      ),
      repeatMode = RepeatMode.Restart
    ),
    label = "phase"
  )

  val glow by infiniteTransition.animateFloat(
    initialValue = 0.8f,
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
    val moonRadius = minOf(size.width, size.height) * 0.35f

    // Draw moon glow
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          MoonlightSilver.copy(alpha = 0.4f * glow),
          Color.Transparent
        ),
        center = center,
        radius = moonRadius * 1.8f
      ),
      radius = moonRadius * 1.8f,
      center = center
    )

    // Draw full moon
    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color.White.copy(alpha = 0.9f),
          MoonlightSilver.copy(alpha = 0.8f)
        ),
        center = center,
        radius = moonRadius
      ),
      radius = moonRadius,
      center = center
    )

    // Draw shadow for moon phase
    val shadowPhase = phase * 2 * PI.toFloat()
    val shadowOffset = moonRadius * cos(shadowPhase)

    drawCircle(
      brush = Brush.radialGradient(
        colors = listOf(
          Color(0xFF0A0A1A).copy(alpha = 0.9f),
          Color(0xFF0A0A1A).copy(alpha = 0.7f)
        ),
        center = Offset(center.x + shadowOffset, center.y),
        radius = moonRadius
      ),
      radius = moonRadius,
      center = center
    )
  }
}
