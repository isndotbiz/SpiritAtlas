package com.spiritatlas.core.ui.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * Spiritual particle effect system with multiple particle types
 * Optimized for performance with configurable particle count and behavior
 */

/**
 * Floating sparkle particles with ethereal glow effect
 * Perfect for spiritual/mystical backgrounds
 */
@Composable
fun SpiritualParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 50,
    colors: List<Color> = listOf(
        Color(0xFFB388FF),
        Color(0xFF8C9EFF),
        Color(0xFF82B1FF),
        Color(0xFFA7FFEB)
    )
) {
    val particles = remember {
        List(particleCount) {
            FloatingParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 3f + 1f,
                speed = Random.nextFloat() * 0.0003f + 0.0001f,
                color = colors.random(),
                twinkleOffset = Random.nextFloat() * PI.toFloat() * 2
            )
        }
    }

    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(16) // ~60 FPS
            time += 16f
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val currentY = (particle.y + particle.speed * time) % 1f
            val opacity = (sin(time * 0.003f + particle.twinkleOffset) + 1f) / 2f * 0.8f + 0.2f

            drawCircle(
                color = particle.color.copy(alpha = opacity),
                radius = particle.size * density,
                center = Offset(
                    particle.x * size.width,
                    currentY * size.height
                )
            )

            // Glow effect
            drawCircle(
                color = particle.color.copy(alpha = opacity * 0.3f),
                radius = particle.size * density * 2f,
                center = Offset(
                    particle.x * size.width,
                    currentY * size.height
                )
            )
        }
    }
}

/**
 * Energy flow particles that move along curved paths
 * Great for chakra visualizations and energy animations
 */
@Composable
fun EnergyFlowParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 30,
    flowSpeed: Float = 1f,
    color: Color = Color(0xFFB388FF)
) {
    val particles = remember {
        List(particleCount) {
            EnergyParticle(
                angle = Random.nextFloat() * PI.toFloat() * 2,
                radius = Random.nextFloat() * 0.4f + 0.1f,
                speed = Random.nextFloat() * 0.002f * flowSpeed + 0.001f,
                size = Random.nextFloat() * 2f + 1f,
                trail = mutableListOf()
            )
        }
    }

    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(16)
            time += 16f
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        particles.forEach { particle ->
            val currentAngle = particle.angle + particle.speed * time
            val x = centerX + cos(currentAngle) * particle.radius * size.width
            val y = centerY + sin(currentAngle) * particle.radius * size.height

            // Update trail
            val position = Offset(x, y)
            particle.trail.add(position)
            if (particle.trail.size > 10) {
                particle.trail.removeAt(0)
            }

            // Draw trail
            for (i in 0 until particle.trail.size - 1) {
                val alpha = (i.toFloat() / particle.trail.size) * 0.6f
                drawLine(
                    color = color.copy(alpha = alpha),
                    start = particle.trail[i],
                    end = particle.trail[i + 1],
                    strokeWidth = particle.size * density
                )
            }

            // Draw particle
            drawCircle(
                color = color,
                radius = particle.size * density,
                center = position
            )
        }
    }
}

/**
 * Sacred geometry particles that form geometric patterns
 * Ideal for meditation and sacred space visualizations
 */
@Composable
fun GeometricParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 20,
    color: Color = Color(0xFFFFD700),
    shape: ParticleShape = ParticleShape.CIRCLE
) {
    val particles = remember {
        List(particleCount) {
            GeometricParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                size = Random.nextFloat() * 4f + 2f,
                rotation = Random.nextFloat() * 360f,
                rotationSpeed = (Random.nextFloat() - 0.5f) * 0.1f
            )
        }
    }

    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(16)
            time += 16f
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            val centerX = particle.x * size.width
            val centerY = particle.y * size.height
            val currentRotation = particle.rotation + particle.rotationSpeed * time

            when (shape) {
                ParticleShape.CIRCLE -> {
                    drawCircle(
                        color = color,
                        radius = particle.size * density,
                        center = Offset(centerX, centerY),
                        style = Stroke(width = 1.dp.toPx())
                    )
                }
                ParticleShape.TRIANGLE -> {
                    drawGeometricShape(
                        color = color,
                        center = Offset(centerX, centerY),
                        size = particle.size * density,
                        sides = 3,
                        rotation = currentRotation
                    )
                }
                ParticleShape.SQUARE -> {
                    drawGeometricShape(
                        color = color,
                        center = Offset(centerX, centerY),
                        size = particle.size * density,
                        sides = 4,
                        rotation = currentRotation
                    )
                }
                ParticleShape.HEXAGON -> {
                    drawGeometricShape(
                        color = color,
                        center = Offset(centerX, centerY),
                        size = particle.size * density,
                        sides = 6,
                        rotation = currentRotation
                    )
                }
            }
        }
    }
}

/**
 * Constellation effect - connects nearby particles with lines
 * Creates a network/web effect perfect for connection visualizations
 */
@Composable
fun ConstellationParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 25,
    connectionDistance: Float = 0.15f,
    color: Color = Color(0xFF82B1FF)
) {
    val particles = remember {
        List(particleCount) {
            MovingParticle(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                vx = (Random.nextFloat() - 0.5f) * 0.0001f,
                vy = (Random.nextFloat() - 0.5f) * 0.0001f,
                size = Random.nextFloat() * 2f + 1f
            )
        }
    }

    var time by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(16)
            time += 16f

            // Update particle positions
            particles.forEach { particle ->
                particle.x = (particle.x + particle.vx * 16f).coerceIn(0f, 1f)
                particle.y = (particle.y + particle.vy * 16f).coerceIn(0f, 1f)

                // Bounce off edges
                if (particle.x <= 0f || particle.x >= 1f) particle.vx *= -1
                if (particle.y <= 0f || particle.y >= 1f) particle.vy *= -1
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        // Draw connections
        for (i in particles.indices) {
            for (j in i + 1 until particles.size) {
                val p1 = particles[i]
                val p2 = particles[j]

                val dx = p2.x - p1.x
                val dy = p2.y - p1.y
                val distance = kotlin.math.sqrt(dx * dx + dy * dy)

                if (distance < connectionDistance) {
                    val alpha = (1f - distance / connectionDistance) * 0.5f
                    drawLine(
                        color = color.copy(alpha = alpha),
                        start = Offset(p1.x * size.width, p1.y * size.height),
                        end = Offset(p2.x * size.width, p2.y * size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
        }

        // Draw particles
        particles.forEach { particle ->
            drawCircle(
                color = color,
                radius = particle.size * density,
                center = Offset(particle.x * size.width, particle.y * size.height)
            )
        }
    }
}

// === Helper Functions ===

private fun DrawScope.drawGeometricShape(
    color: Color,
    center: Offset,
    size: Float,
    sides: Int,
    rotation: Float
) {
    val path = Path()
    val angleStep = (2 * PI / sides).toFloat()
    val rotationRad = rotation * PI.toFloat() / 180f

    for (i in 0 until sides) {
        val angle = angleStep * i + rotationRad
        val x = center.x + cos(angle) * size
        val y = center.y + sin(angle) * size

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()

    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 1.dp.toPx())
    )
}

// === Data Classes ===

private data class FloatingParticle(
    val x: Float,
    val y: Float,
    val size: Float,
    val speed: Float,
    val color: Color,
    val twinkleOffset: Float
)

private data class EnergyParticle(
    val angle: Float,
    val radius: Float,
    val speed: Float,
    val size: Float,
    val trail: MutableList<Offset>
)

private data class GeometricParticle(
    val x: Float,
    val y: Float,
    val size: Float,
    var rotation: Float,
    val rotationSpeed: Float
)

private data class MovingParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val size: Float
)

enum class ParticleShape {
    CIRCLE,
    TRIANGLE,
    SQUARE,
    HEXAGON
}
